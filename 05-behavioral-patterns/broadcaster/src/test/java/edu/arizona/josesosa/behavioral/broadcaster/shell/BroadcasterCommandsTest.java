package edu.arizona.josesosa.behavioral.broadcaster.shell;

import edu.arizona.josesosa.behavioral.broadcaster.service.ApplicationInfoService;
import edu.arizona.josesosa.behavioral.broadcaster.service.LogManagementService;
import edu.arizona.josesosa.behavioral.broadcaster.service.PatternStarterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the BroadcasterCommands class.
 */
@ExtendWith(MockitoExtension.class)
public class BroadcasterCommandsTest {

    @Mock
    private PatternStarterService patternStarterService;
    
    @Mock
    private LogManagementService logManagementService;
    
    @Mock
    private ApplicationInfoService applicationInfoService;
    
    @InjectMocks
    private BroadcasterCommands commands;
    
    @Test
    public void testStartBroadcasting() {
        // Arrange
        when(patternStarterService.startAllPatterns()).thenReturn("Broadcasting started message");
        
        // Act
        String result = commands.startBroadcasting();
        
        // Assert
        verify(patternStarterService, times(1)).startAllPatterns();
        assertThat(result).isEqualTo("Broadcasting started message");
    }
    
    @Test
    public void testStartPattern() {
        // Arrange
        String pattern = "chain";
        when(patternStarterService.startSpecificPattern(pattern)).thenReturn("Started Chain pattern message");
        
        // Act
        String result = commands.startPattern(pattern);
        
        // Assert
        verify(patternStarterService, times(1)).startSpecificPattern(pattern);
        assertThat(result).isEqualTo("Started Chain pattern message");
    }
    
    @Test
    public void testAppInfo() {
        // Arrange
        when(applicationInfoService.getApplicationInfo()).thenReturn("Application info message");
        
        // Act
        String result = commands.appInfo();
        
        // Assert
        verify(applicationInfoService, times(1)).getApplicationInfo();
        assertThat(result).isEqualTo("Application info message");
    }
    
    @Test
    public void testDeleteLogs() {
        // Arrange
        when(logManagementService.deleteAllLogs()).thenReturn("Deleted logs message");
        
        // Act
        String result = commands.deleteLogs();
        
        // Assert
        verify(logManagementService, times(1)).deleteAllLogs();
        assertThat(result).isEqualTo("Deleted logs message");
    }
}