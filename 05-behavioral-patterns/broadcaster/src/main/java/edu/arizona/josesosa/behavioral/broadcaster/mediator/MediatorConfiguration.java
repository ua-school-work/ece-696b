package edu.arizona.josesosa.behavioral.broadcaster.mediator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for the Mediator pattern.
 */
@Configuration
public class MediatorConfiguration {

    private static final int NUMBER_OF_COMPONENTS = 10;
    
    /**
     * Creates and configures the mediator with its components
     * 
     * @return The configured mediator
     */
    @Bean
    public ConcreteMediator mediator() {
        ConcreteMediator mediator = createMediator();
        createAndRegisterComponents(mediator);
        return mediator;
    }
    
    /**
     * Creates a new mediator instance
     * 
     * @return The created mediator
     */
    private ConcreteMediator createMediator() {
        return new ConcreteMediator();
    }
    
    /**
     * Creates components and registers them with the mediator
     * 
     * @param mediator The mediator to register components with
     */
    private void createAndRegisterComponents(ConcreteMediator mediator) {
        for (int i = 1; i <= NUMBER_OF_COMPONENTS; i++) {
            MediatorComponent component = new MediatorComponent("mediator-" + i);
            component.setMediator(mediator);
        }
    }
}