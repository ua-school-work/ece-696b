package edu.arizona.josesosa.behavioral.broadcaster.shell;

import edu.arizona.josesosa.behavioral.broadcaster.service.ApplicationInfoService;
import edu.arizona.josesosa.behavioral.broadcaster.service.LogManagementService;
import edu.arizona.josesosa.behavioral.broadcaster.service.PatternStarterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

/**
 * Spring Shell commands for the Broadcaster application.
 * This class defines the commands that can be executed in the shell.
 * It delegates the actual implementation to specialized service classes.
 */
@ShellComponent
public class BroadcasterCommands {

    @Autowired
    private PatternStarterService patternStarterService;
    
    @Autowired
    private LogManagementService logManagementService;
    
    @Autowired
    private ApplicationInfoService applicationInfoService;
    
    /**
     * Start the broadcasting process for all components.
     * This command will trigger all components to start sending messages.
     */
    @ShellMethod(key = "start-all", value = "Start the broadcasting process for all components")
    public String startBroadcasting() {
        return patternStarterService.startAllPatterns();
    }

    /**
     * Start a specific pattern implementation.
     * This command allows starting only one of the pattern implementations.
     * 
     * @param pattern The pattern to start (chain, mediator, or observer)
     * @return A message indicating the pattern has started
     */
    @ShellMethod(key = "start-pattern", value = "Start a specific pattern implementation (chain, mediator, or observer)")
    public String startPattern(
            @ShellOption(help = "The pattern to start (chain, mediator, or observer)") String pattern) {
        return patternStarterService.startSpecificPattern(pattern);
    }
    
    /**
     * Display help information about the application.
     * 
     * @return Help information
     */
    @ShellMethod(key = "app-info", value = "Display information about the application")
    public String appInfo() {
        return applicationInfoService.getApplicationInfo();
    }
    
    /**
     * Delete all log files in the logs directory.
     * 
     * @return A message indicating the result of the operation
     */
    @ShellMethod(key = "delete-logs", value = "Delete all log files")
    public String deleteLogs() {
        return logManagementService.deleteAllLogs();
    }
}