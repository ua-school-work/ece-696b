package edu.arizona.josesosa.behavioral.broadcaster.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the LogManagementService class.
 */
public class LogManagementServiceTest {

    @TempDir
    Path tempDir;

    @Test
    public void testDeleteAllLogs() throws IOException {
        // Create a test implementation of LogManagementService
        LogManagementService service = new LogManagementService() {
            @Override
            protected Path getLogsDirectory() {
                return tempDir;
            }

            @Override
            protected Path getSpringShellLogPath() {
                return tempDir.resolve("spring-shell.log");
            }
        };

        // Create some test log files
        Path logFile1 = tempDir.resolve("test1.log");
        Path logFile2 = tempDir.resolve("test2.log");
        Path springShellLog = tempDir.resolve("spring-shell.log");
        
        Files.createFile(logFile1);
        Files.createFile(logFile2);
        Files.createFile(springShellLog);
        
        // Verify files were created
        assertThat(Files.exists(logFile1)).isTrue();
        assertThat(Files.exists(logFile2)).isTrue();
        assertThat(Files.exists(springShellLog)).isTrue();
        
        // Execute the delete operation
        String result = service.deleteAllLogs();
        
        // Verify files were deleted
        assertThat(Files.exists(logFile1)).isFalse();
        assertThat(Files.exists(logFile2)).isFalse();
        assertThat(Files.exists(springShellLog)).isFalse();
        
        // Verify the result message
        assertThat(result).contains("Successfully deleted 3 log files");
        assertThat(result).contains("Failed to delete 0 files");
    }
    
    @Test
    public void testDeleteFileWithRetry() throws IOException {
        LogManagementService service = new LogManagementService();
        
        // Create a test file
        Path testFile = tempDir.resolve("retry-test.log");
        Files.createFile(testFile);
        
        // Verify file exists
        assertThat(Files.exists(testFile)).isTrue();
        
        // Delete the file with retry
        boolean result = service.deleteFileWithRetry(testFile, 3);
        
        // Verify file was deleted
        assertThat(result).isTrue();
        assertThat(Files.exists(testFile)).isFalse();
    }
}