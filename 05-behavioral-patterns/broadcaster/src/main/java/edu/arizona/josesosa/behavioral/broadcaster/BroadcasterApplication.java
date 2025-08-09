package edu.arizona.josesosa.behavioral.broadcaster;

import edu.arizona.josesosa.behavioral.broadcaster.chain.ChainConfiguration.ChainStarter;
import edu.arizona.josesosa.behavioral.broadcaster.mediator.ConcreteMediator;
import edu.arizona.josesosa.behavioral.broadcaster.observer.ObserverConfiguration.ObserverStarter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * Main application class for the Broadcaster application.
 * This Spring Boot application demonstrates three behavioral patterns:
 * - Chain of Responsibility
 * - Mediator
 * - Observer
 */
@SpringBootApplication
@EnableAsync(proxyTargetClass=true)
@EnableScheduling
public class BroadcasterApplication {

    @Autowired
    private ConcreteMediator mediator;
    
    @Autowired
    private ChainStarter chainStarter;
    
    @Autowired
    private ObserverStarter observerStarter;

    public static void main(String[] args) {
        SpringApplication.run(BroadcasterApplication.class, args);
    }

    /**
     * Configure the async executor for component tasks
     */
    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); // One thread per component
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("component-");
        executor.initialize();
        return executor;
    }

    /**
     * Stop all components after one minute but keep the Spring Shell running
     */
    @EventListener(ApplicationReadyEvent.class)
    public void stopComponentsAfterOneMinute() {
        Thread shutdownThread = new Thread(() -> {
            try {
                // Wait for 1 minute
                TimeUnit.MINUTES.sleep(1);
                System.out.println("Application has been running for 1 minute. Stopping components...");
                
                // Stop all components but keep the application running
                System.out.println("Stopping all mediator components...");
                if (mediator != null) {
                    mediator.stopAll();
                }
                
                System.out.println("Stopping all chain components...");
                if (chainStarter != null) {
                    chainStarter.stopAll();
                }
                
                System.out.println("Stopping all observer components...");
                if (observerStarter != null) {
                    observerStarter.stopAll();
                }
                
                System.out.println("All components stopped successfully. Spring Shell remains active.");
                
                // Refresh the shell prompt
                System.out.print("\r");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        shutdownThread.setDaemon(true);
        shutdownThread.start();
    }
}