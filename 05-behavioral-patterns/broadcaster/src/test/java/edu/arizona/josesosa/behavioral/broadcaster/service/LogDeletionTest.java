package edu.arizona.josesosa.behavioral.broadcaster.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * A simple test program to verify the log deletion functionality.
 * This program creates a LogManagementService instance and calls deleteAllLogs().
 */
public class LogDeletionTest {

    public static void main(String[] args) throws IOException {
        // Print the current log files
        Path logsDir = Paths.get("logs");
        System.out.println("Before deletion:");
        listLogFiles(logsDir);
        
        // Create the LogManagementService
        LogManagementService service = new LogManagementService();
        
        // Delete the logs
        String result = service.deleteAllLogs();
        System.out.println("\nResult: " + result);
        
        // Print the log files after deletion
        System.out.println("\nAfter deletion:");
        listLogFiles(logsDir);
    }
    
    private static void listLogFiles(Path logsDir) {
        if (!Files.exists(logsDir)) {
            System.out.println("Logs directory not found.");
            return;
        }
        
        File[] logFiles = logsDir.toFile().listFiles((dir, name) -> name.endsWith(".log"));
        if (logFiles == null || logFiles.length == 0) {
            System.out.println("No log files found.");
            return;
        }
        
        System.out.println("Found " + logFiles.length + " log files:");
        Arrays.stream(logFiles)
              .map(File::getName)
              .sorted()
              .forEach(name -> System.out.println("- " + name));
    }
}