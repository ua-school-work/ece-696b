package edu.arizona.josesosa.enterprise.application;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleDiContainerTest {

    static class Foo {}
    static class Bar {}

    static class WithDep {
        final Bar bar;
        public WithDep(Bar bar) { this.bar = bar; }
    }

    static class C1 {
        final C2 c2;
        public C1(C2 c2) { this.c2 = c2; }
    }

    static class C2 {
        final C1 c1;
        public C2(C1 c1) { this.c1 = c1; }
    }

    @Test
    void bindProvidesNewInstances() {
        SimpleDiContainer c = new SimpleDiContainer();
        c.bind(Foo.class, Foo::new);
        Foo a = c.get(Foo.class);
        Foo b = c.get(Foo.class);
        assertNotSame(a, b);
    }

    @Test
    void singletonCachesSameInstance() {
        SimpleDiContainer c = new SimpleDiContainer();
        c.singleton(Foo.class, Foo::new);
        Foo a = c.get(Foo.class);
        Foo b = c.get(Foo.class);
        assertSame(a, b);
    }

    @Test
    void instanceReturnsProvidedInstance() {
        SimpleDiContainer c = new SimpleDiContainer();
        Foo provided = new Foo();
        c.instance(Foo.class, provided);
        assertSame(provided, c.get(Foo.class));
    }

    @Test
    void autoInstantiatesAndInjectsDependencies() {
        SimpleDiContainer c = new SimpleDiContainer();
        WithDep wd = c.get(WithDep.class);
        assertNotNull(wd);
        assertNotNull(wd.bar);
    }

    @Test
    void autowiringRespectsSingletonBindings() {
        SimpleDiContainer c = new SimpleDiContainer();
        c.singleton(Bar.class, Bar::new);
        Bar singleton = c.get(Bar.class);
        WithDep wd = c.get(WithDep.class);
        assertSame(singleton, wd.bar);
    }

    @Test
    void detectsCircularDependencies() {
        SimpleDiContainer c = new SimpleDiContainer();
        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> c.get(C1.class));
        assertTrue(ex.getMessage().contains("Circular dependency detected"));
    }
}
