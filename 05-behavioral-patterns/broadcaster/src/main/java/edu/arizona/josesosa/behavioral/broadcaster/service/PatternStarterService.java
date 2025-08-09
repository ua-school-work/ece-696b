package edu.arizona.josesosa.behavioral.broadcaster.service;

import edu.arizona.josesosa.behavioral.broadcaster.chain.ChainConfiguration.ChainStarter;
import edu.arizona.josesosa.behavioral.broadcaster.mediator.ConcreteMediator;
import edu.arizona.josesosa.behavioral.broadcaster.observer.ObserverConfiguration.ObserverStarter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * Service responsible for starting pattern implementations.
 * This service handles the starting of different behavioral patterns.
 */
@Service
@Slf4j
public class PatternStarterService {

    private static final int BROADCAST_DURATION_SECONDS = 60;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Autowired
    private ChainStarter chainStarter;
    
    @Autowired
    private ConcreteMediator mediator;
    
    @Autowired
    private ObserverStarter observerStarter;
    
    /**
     * Start all pattern implementations.
     * 
     * @return A message indicating all patterns have started
     */
    public String startAllPatterns() {
        chainStarter.startAll();
        mediator.startAll();
        observerStarter.startAll();
        
        schedulePatternShutdown();
        
        return "Broadcasting started. Components will send messages for 1 minute before returning to shell.";
    }
    
    /**
     * Schedules the shutdown of all patterns after the broadcast duration
     */
    private void schedulePatternShutdown() {
        scheduler.schedule(this::stopAllPatterns, BROADCAST_DURATION_SECONDS, TimeUnit.SECONDS);
    }
    
    /**
     * Stops all pattern implementations
     */
    private void stopAllPatterns() {
        log.info("Broadcast duration complete. Stopping all patterns.");
        chainStarter.stopAll();
        mediator.stopAll();
        observerStarter.stopAll();
        scheduler.shutdown();
        
        // Refresh the shell prompt
        System.out.print("\r");
    }
    
    /**
     * Start a specific pattern implementation.
     * 
     * @param pattern The pattern to start (chain, mediator, or observer)
     * @return A message indicating the pattern has started or an error message
     */
    public String startSpecificPattern(String pattern) {
        if (pattern.equalsIgnoreCase("chain")) {
            chainStarter.startAll();
            scheduleSpecificPatternShutdown("chain");
            return "Started Chain of Responsibility pattern. Components will send messages for 1 minute before returning to shell.";
        } else if (pattern.equalsIgnoreCase("mediator")) {
            mediator.startAll();
            scheduleSpecificPatternShutdown("mediator");
            return "Started Mediator pattern. Components will send messages for 1 minute before returning to shell.";
        } else if (pattern.equalsIgnoreCase("observer")) {
            observerStarter.startAll();
            scheduleSpecificPatternShutdown("observer");
            return "Started Observer pattern. Components will send messages for 1 minute before returning to shell.";
        } else {
            return "Invalid pattern. Please specify 'chain', 'mediator', or 'observer'.";
        }
    }
    
    /**
     * Schedules the shutdown of a specific pattern after the broadcast duration
     * 
     * @param pattern The pattern to stop (chain, mediator, or observer)
     */
    private void scheduleSpecificPatternShutdown(String pattern) {
        scheduler.schedule(() -> {
            log.info("Broadcast duration complete. Stopping {} pattern.", pattern);
            if (pattern.equalsIgnoreCase("chain")) {
                chainStarter.stopAll();
            } else if (pattern.equalsIgnoreCase("mediator")) {
                mediator.stopAll();
            } else if (pattern.equalsIgnoreCase("observer")) {
                observerStarter.stopAll();
            }
            scheduler.shutdown();
            
            // Refresh the shell prompt
            System.out.print("\r");
        }, BROADCAST_DURATION_SECONDS, TimeUnit.SECONDS);
    }
}