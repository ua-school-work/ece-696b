package edu.arizona.josesosa.enterprise.application;

import java.lang.reflect.Constructor;
import java.util.function.Function;

/**
 * Internal helper that constructs objects reflectively for SimpleDiContainer.
 * Selects the greediest (max-args) constructor (public or non-public) and resolves each parameter type
 * using the provided dependencyResolver function.
 */
class ReflectiveInstantiator<T> {

    private final Class<T> type;
    private final Function<Class<?>, Object> dependencyResolver;

    ReflectiveInstantiator(Class<T> type, Function<Class<?>, Object> dependencyResolver) {
        this.type = type;
        this.dependencyResolver = dependencyResolver;
    }

    /**
     * Create a new instance of the target type using the greediest constructor.
     * Dependencies are resolved via the injected dependencyResolver.
     * Wraps checked reflection errors into RuntimeException for simplicity.
     */
    @SuppressWarnings("unchecked")
    T createInstance() {
        try {
            Constructor<?> ctor = selectGreediestConstructor();
            Object[] args = resolveConstructorArguments(ctor);
            return (T) newInstance(ctor, args);
        } catch (RuntimeException re) {
            throw re;
        } catch (Exception e) {
            throw new RuntimeException("Failed to construct instance of " + type.getName() + ": " + e.getMessage(), e);
        }
    }

    private Constructor<?> selectGreediestConstructor() {
        Constructor<?>[] ctors = type.getDeclaredConstructors();
        if (ctors.length == 0) {
            throw new IllegalStateException("No public constructor for type: " + type.getName());
        }
        Constructor<?> best = ctors[0];
        for (Constructor<?> c : ctors) {
            if (c.getParameterCount() > best.getParameterCount()) best = c;
        }
        return best;
    }

    private Object[] resolveConstructorArguments(Constructor<?> ctor) {
        Class<?>[] paramTypes = ctor.getParameterTypes();
        Object[] args = new Object[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
            args[i] = dependencyResolver.apply(paramTypes[i]);
        }
        return args;
    }

    private Object newInstance(Constructor<?> ctor, Object[] args) throws Exception {
        try {
            ctor.setAccessible(true);
        } catch (Exception ignored) {
            // Best-effort: continue; may still work if accessible
        }
        return ctor.newInstance(args);
    }
}
