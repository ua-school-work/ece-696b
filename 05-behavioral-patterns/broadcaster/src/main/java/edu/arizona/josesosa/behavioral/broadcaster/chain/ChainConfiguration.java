package edu.arizona.josesosa.behavioral.broadcaster.chain;

import edu.arizona.josesosa.behavioral.broadcaster.common.Component;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Configuration class for the Chain of Responsibility pattern.
 */
@Configuration
public class ChainConfiguration {

    private static final int NUMBER_OF_COMPONENTS = 10;
    
    /**
     * Creates and configures all chain components
     * 
     * @return List of all chain components
     */
    @Bean
    public List<Component> chainComponents() {
        List<ChainComponent> components = createChainComponents();
        connectComponentsInChain(components);
        registerComponentsWithEachOther(components);
        
        return new ArrayList<>(components);
    }
    
    /**
     * Creates the individual chain components
     * 
     * @return List of created chain components
     */
    private List<ChainComponent> createChainComponents() {
        List<ChainComponent> components = new ArrayList<>();
        for (int i = 1; i <= NUMBER_OF_COMPONENTS; i++) {
            components.add(new ChainComponent("chain-" + i));
        }
        return components;
    }
    
    /**
     * Connects components in a linear chain
     * 
     * @param components List of components to connect
     */
    private void connectComponentsInChain(List<ChainComponent> components) {
        for (int i = 0; i < components.size() - 1; i++) {
            components.get(i).setNextInChain(components.get(i + 1));
        }
    }
    
    /**
     * Registers all components with each other for random recipient selection
     * 
     * @param components List of components to register
     */
    private void registerComponentsWithEachOther(List<ChainComponent> components) {
        for (ChainComponent component : components) {
            component.setAllComponents(components);
        }
    }
    
    /**
     * Provides the entry point to the chain of responsibility
     * 
     * @return The first component in the chain
     */
    @Bean
    public Component chainOfResponsibility(List<Component> chainComponents) {
        return chainComponents.get(0);
    }
    
    /**
     * Creates a controller for managing chain components lifecycle
     * 
     * @param chainComponents List of all components in the chain
     * @return A ChainStarter that can start and stop all components
     */
    @Bean
    public ChainStarter chainStarter(List<Component> chainComponents) {
        return new ChainStarter(chainComponents);
    }
    
    /**
     * Controller class for managing the lifecycle of chain components
     */
    public static class ChainStarter {
        private final List<Component> allComponents;
        
        public ChainStarter(List<Component> allComponents) {
            this.allComponents = allComponents;
        }
        
        /**
         * Activates all components in the chain
         */
        public void startAll() {
            for (Component component : allComponents) {
                component.start();
            }
        }
        
        /**
         * Deactivates all components in the chain
         */
        public void stopAll() {
            for (Component component : allComponents) {
                component.stop();
            }
        }
    }
}