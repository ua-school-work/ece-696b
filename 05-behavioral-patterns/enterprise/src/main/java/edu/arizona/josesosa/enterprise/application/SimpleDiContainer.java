package edu.arizona.josesosa.enterprise.application;

import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * Minimal dependency injection container supporting:
 * - Type bindings via providers,
 * - Lazy singletons,
 * - Prebound instances,
 * - Reflective auto-wiring by selecting the greediest public constructor,
 * - Circular dependency detection during resolution.
 * Thread-safe maps for providers/singletons; not intended for concurrent writes to the resolving set.
 */
public class SimpleDiContainer {
    private final Map<Class<?>, Supplier<?>> providers = new ConcurrentHashMap<>();
    private final Map<Class<?>, Object> singletons = new ConcurrentHashMap<>();
    private final Set<Class<?>> resolving = new HashSet<>();

    /**
     * Bind a concrete or interface type to a provider that creates instances on demand.
     * Later calls to get(type) will delegate to this provider.
     */
    public <T> void bind(Class<T> type, Supplier<? extends T> provider) {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(provider, "provider");
        providers.put(type, provider);
    }

    /**
     * Register a lazy singleton provider for the given type.
     * The underlying provider is called at most once; the same instance is returned thereafter.
     */
    public <T> void singleton(Class<T> type, Supplier<? extends T> provider) {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(provider, "provider");
        providers.put(type, () -> {
            Object existing = singletons.get(type);
            if (existing != null) return existing;
            Object created = provider.get();
            Object prior = singletons.putIfAbsent(type, created);
            return prior != null ? prior : created;
        });
    }

    /**
     * Pre-bind an already created instance to the given type. Both singleton map and providers
     * are updated so that later get(type) always returns this instance.
     */
    public <T> void instance(Class<T> type, T value) {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(value, "value");
        singletons.put(type, value);
        providers.put(type, () -> value);
    }

    /**
     * Resolve an instance for the given type.
     * Resolution order:
     * 1) If a provider was bound for the exact type, invoke it;
     * 2) If a prebound instance exists, return it;
     * 3) If the type is concrete, auto-construct it resolving constructor deps from the container;
     * otherwise, throw IllegalStateException for missing binding.
     */
    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> type) {
        Objects.requireNonNull(type, "type");
        Supplier<?> provider = findProvider(type);
        if (provider != null) return (T) provider.get();
        Object prebound = findPreboundSingleton(type);
        if (prebound != null) return (T) prebound;
        ensureConcrete(type);
        return createInstance(type);
    }

    private Supplier<?> findProvider(Class<?> type) {
        return providers.get(type);
    }

    private Object findPreboundSingleton(Class<?> type) {
        return singletons.get(type);
    }

    private void ensureConcrete(Class<?> type) {
        boolean isAbstract = java.lang.reflect.Modifier.isAbstract(type.getModifiers());
        if (type.isInterface() || isAbstract) {
            throw new IllegalStateException("No binding found for type: " + type.getName());
        }
    }

    private <T> T createInstance(Class<T> type) {
        enterResolving(type);
        try {
            ReflectiveInstantiator<T> instantiator = new ReflectiveInstantiator<>(type, this::get);
            return instantiator.createInstance();
        } finally {
            exitResolving(type);
        }
    }

    private void enterResolving(Class<?> type) {
        if (!resolving.add(type)) {
            throw new IllegalStateException("Circular dependency detected while resolving: " + type.getName());
        }
    }

    private void exitResolving(Class<?> type) {
        resolving.remove(type);
    }
}
