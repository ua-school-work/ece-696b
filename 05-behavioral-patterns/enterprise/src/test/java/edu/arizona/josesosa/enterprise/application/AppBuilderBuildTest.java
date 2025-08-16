package edu.arizona.josesosa.enterprise.application;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class AppBuilderBuildTest {
    @Test
    void buildDoesNotThrowConcurrentModificationException() {
        assertDoesNotThrow(() -> new AppBuilder().withPort(0).build());
    }
}
