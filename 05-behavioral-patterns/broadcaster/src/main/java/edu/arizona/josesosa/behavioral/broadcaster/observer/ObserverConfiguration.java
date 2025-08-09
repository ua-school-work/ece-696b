package edu.arizona.josesosa.behavioral.broadcaster.observer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Configuration class for the Observer pattern.
 */
@Configuration
public class ObserverConfiguration {

    private static final int NUMBER_OF_COMPONENTS = 10;
    
    /**
     * Creates and configures the observer components with their relationships
     * 
     * @return A lifecycle controller for all observer components
     */
    @Bean
    public ObserverStarter observerStarter() {
        List<ObserverComponent> components = createObserverComponents();
        registerComponentsWithEachOther(components);
        setupObserverRelationships(components);
        
        return new ObserverStarter(components);
    }
    
    /**
     * Creates the individual observer components
     * 
     * @return List of created observer components
     */
    private List<ObserverComponent> createObserverComponents() {
        List<ObserverComponent> components = new ArrayList<>();
        for (int i = 1; i <= NUMBER_OF_COMPONENTS; i++) {
            components.add(new ObserverComponent("observer-" + i));
        }
        return components;
    }
    
    /**
     * Registers all components with each other for random recipient selection
     * 
     * @param components List of components to register
     */
    private void registerComponentsWithEachOther(List<ObserverComponent> components) {
        for (ObserverComponent component : components) {
            component.setAllComponents(components);
        }
    }
    
    /**
     * Establishes the observer relationships between components
     * 
     * @param components List of components to set up relationships for
     */
    private void setupObserverRelationships(List<ObserverComponent> components) {
        setupMainSubjectObservers(components);
        setupAdditionalObserverRelationships(components);
    }
    
    /**
     * Sets up the main subject with all other components as its observers
     */
    private void setupMainSubjectObservers(List<ObserverComponent> components) {
        ObserverComponent mainSubject = components.get(0);
        
        for (int i = 1; i < components.size(); i++) {
            mainSubject.registerObserver(components.get(i));
        }
    }
    
    /**
     * Sets up additional observer relationships between components
     */
    private void setupAdditionalObserverRelationships(List<ObserverComponent> components) {
        for (int i = 1; i < components.size() - 1; i++) {
            if (i % 2 == 1) {
                components.get(i).registerObserver(components.get(i + 1));
            }
        }
    }
    
    /**
     * Controller class for managing the lifecycle of observer components
     */
    public static class ObserverStarter {
        private final List<ObserverComponent> components;
        
        /**
         * Creates a new lifecycle controller for the specified components
         * 
         * @param components The components to manage
         */
        public ObserverStarter(List<ObserverComponent> components) {
            this.components = components;
        }
        
        /**
         * Activates all observer components
         */
        public void startAll() {
            for (ObserverComponent component : components) {
                component.start();
            }
        }
        
        /**
         * Deactivates all observer components
         */
        public void stopAll() {
            for (ObserverComponent component : components) {
                component.stop();
            }
        }
    }
}