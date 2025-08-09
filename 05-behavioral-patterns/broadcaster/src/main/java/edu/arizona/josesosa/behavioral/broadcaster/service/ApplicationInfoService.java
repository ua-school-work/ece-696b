package edu.arizona.josesosa.behavioral.broadcaster.service;

import org.springframework.stereotype.Service;

/**
 * Service responsible for providing application information.
 * This service handles the generation of help and information text about the application.
 */
@Service
public class ApplicationInfoService {

    /**
     * Generate application information and help text.
     * 
     * @return Formatted application information
     */
    public String getApplicationInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Broadcaster Application\n");
        info.append("----------------------\n");
        info.append("This application demonstrates three behavioral patterns:\n");
        info.append("1. Chain of Responsibility\n");
        info.append("2. Mediator\n");
        info.append("3. Observer\n\n");
        info.append("Each pattern has its own implementation with 10 components that broadcast messages.\n");
        info.append("The application will run for 1 minute and then shut down automatically.\n\n");
        info.append("Available commands:\n");
        info.append("- start: Start all pattern implementations\n");
        info.append("- start-pattern [pattern]: Start a specific pattern implementation\n");
        info.append("- app-info: Display this information\n");
        info.append("- delete-logs: Delete all log files\n");
        
        return info.toString();
    }
}