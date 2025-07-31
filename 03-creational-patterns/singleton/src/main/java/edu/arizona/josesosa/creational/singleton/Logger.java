package edu.arizona.josesosa.creational.singleton;

/**
 * Basic logger simulation
 */
public interface Logger {

    /** I change the signature to accept a Class<?> parameter to
    make it a true singleton. I could have avoided this by
    using a factory pattern like SL4J does, but it seemed like
    it was outside the scope of this homework given the next module is
    about factory patterns. **/
    void log(Class<?> c, String s);

}
