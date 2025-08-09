package edu.arizona.josesosa.behavioral.broadcaster.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Service responsible for managing log files.
 * This service handles operations related to log files such as deletion.
 */
@Service
public class LogManagementService {

    /**
     * Delete all log files in the logs directory and spring-shell.log.
     * If files cannot be deleted because they are locked, they will be truncated to 0 bytes.
     * 
     * @return A message indicating the result of the operation
     */
    public String deleteAllLogs() {
        Path logsDir = getLogsDirectory();
        int deletedCount = 0;
        int truncatedCount = 0;
        int failedCount = 0;
        
        if (!Files.exists(logsDir)) {
            return "Logs directory not found.";
        }
        
        try {
            File[] logFiles = logsDir.toFile().listFiles((dir, name) -> name.endsWith(".log"));
            
            if (logFiles == null || logFiles.length == 0) {
                return "No log files found to delete.";
            }
            
            // Process each log file
            for (File logFile : logFiles) {
                String fileName = logFile.getName();
                
                // Try to delete the file
                if (deleteFileWithRetry(logFile.toPath(), 3)) {
                    deletedCount++;
                } else {
                    // If deletion fails, try to truncate the file to 0 bytes
                    if (truncateFile(logFile)) {
                        truncatedCount++;
                    } else {
                        failedCount++;
                    }
                }
            }
            
            // Handle spring-shell.log
            Path springShellLogPath = getSpringShellLogPath();
            File springShellLog = springShellLogPath.toFile();
            if (springShellLog.exists()) {
                if (deleteFileWithRetry(springShellLogPath, 3)) {
                    deletedCount++;
                } else {
                    if (truncateFile(springShellLog)) {
                        truncatedCount++;
                    } else {
                        failedCount++;
                    }
                }
            }
            
            if (truncatedCount > 0) {
                return String.format("Successfully deleted %d log files. Truncated %d files to 0 bytes. Failed to process %d files.", 
                        deletedCount, truncatedCount, failedCount);
            } else {
                return String.format("Successfully deleted %d log files. Failed to delete %d files.", 
                        deletedCount, failedCount);
            }
            
        } catch (Exception e) {
            return "Error processing log files: " + e.getMessage();
        }
    }
    
    /**
     * Truncates a file to 0 bytes.
     * This is used when a file cannot be deleted because it's locked by another process.
     * 
     * @param file The file to truncate
     * @return true if truncation was successful, false otherwise
     */
    protected boolean truncateFile(File file) {
        try {
            // Try to truncate the file using RandomAccessFile
            try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
                raf.setLength(0);
                return true;
            } catch (IOException e) {
                // If RandomAccessFile fails, try with FileChannel
                try (FileChannel channel = FileChannel.open(file.toPath(), 
                        StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
                    return true;
                } catch (IOException e2) {
                    // If all truncation methods fail, try to create an empty file with the same name
                    try {
                        // Create a temporary file
                        File tempFile = new File(file.getParentFile(), file.getName() + ".tmp");
                        if (tempFile.createNewFile()) {
                            // Try to replace the original file with the empty file
                            // This might fail if the original file is locked
                            return tempFile.renameTo(file);
                        }
                    } catch (IOException e3) {
                        // Ignore
                    }
                }
            }
        } catch (Exception e) {
            // Ignore any exceptions
        }
        return false;
    }
    
    /**
     * Get the path to the logs directory.
     * 
     * @return Path to the logs directory
     */
    protected Path getLogsDirectory() {
        // Use absolute path to ensure we're targeting the correct directory
        return Paths.get(System.getProperty("user.dir"), "logs").toAbsolutePath();
    }
    
    /**
     * Get the path to the spring-shell.log file.
     * 
     * @return Path to the spring-shell.log file
     */
    protected Path getSpringShellLogPath() {
        // Use absolute path to ensure we're targeting the correct file
        return Paths.get(System.getProperty("user.dir"), "spring-shell.log").toAbsolutePath();
    }
    
    /**
     * Attempts to delete a file with retry mechanism.
     * This helps handle cases where files might be temporarily locked.
     * Tries multiple deletion methods to ensure success, including handling locked files.
     * 
     * @param path The path to the file to delete
     * @param maxAttempts Maximum number of deletion attempts
     * @return true if deletion was successful, false otherwise
     */
    protected boolean deleteFileWithRetry(Path path, int maxAttempts) {
        File file = path.toFile();
        
        // First check if file exists
        if (!file.exists()) {
            return true; // File doesn't exist, so consider it "deleted"
        }
        
        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            // Try first with Files.delete
            try {
                Files.delete(path);
                return true;
            } catch (IOException e) {
                // If Files.delete fails, try with File.delete
                if (file.delete()) {
                    return true;
                }
                
                // Try to handle locked files
                try {
                    // Try to truncate the file to 0 bytes and mark for deletion
                    truncateAndDeleteFile(path);
                    return true;
                } catch (Exception ex) {
                    // Continue with other methods
                }
                
                // If this is not the last attempt, wait before retrying
                if (attempt < maxAttempts - 1) {
                    try {
                        // Wait for 500ms before retrying (increased wait time)
                        Thread.sleep(500);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        return false;
                    }
                }
            }
        }
        
        // As a last resort, try to set the file writable and delete again
        try {
            file.setWritable(true);
            return file.delete();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Attempts to truncate a file to 0 bytes and mark it for deletion.
     * This can help with locked files by removing their content.
     * 
     * @param path The path to the file
     * @throws IOException If an I/O error occurs
     */
    private void truncateAndDeleteFile(Path path) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(path.toFile(), "rw");
             FileChannel channel = raf.getChannel()) {
            
            // Try to get a lock on the file
            FileLock lock = null;
            try {
                lock = channel.tryLock();
                if (lock != null) {
                    // Truncate the file to 0 bytes
                    channel.truncate(0);
                    // Force changes to disk
                    channel.force(true);
                }
            } finally {
                if (lock != null) {
                    lock.release();
                }
            }
        }
        
        // Try to create a new file with DELETE_ON_CLOSE option
        try {
            Files.newByteChannel(path, 
                StandardOpenOption.READ, 
                StandardOpenOption.WRITE, 
                StandardOpenOption.DELETE_ON_CLOSE).close();
        } catch (Exception e) {
            // Ignore if this fails
        }
    }
}