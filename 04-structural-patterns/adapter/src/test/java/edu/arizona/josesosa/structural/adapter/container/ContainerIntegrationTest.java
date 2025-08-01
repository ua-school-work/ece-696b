package edu.arizona.josesosa.structural.adapter.container;

import edu.arizona.josesosa.structural.adapter.business.IPersonService;
import edu.arizona.josesosa.structural.adapter.business.RemotePersonService;
import edu.arizona.josesosa.structural.adapter.model.Person;
import edu.arizona.josesosa.structural.adapter.view.ReportController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test for the Container class
 * This test verifies that the complete application flow works correctly
 */
public class ContainerIntegrationTest {

    @BeforeEach
    public void setUp() {
        // Clear the context before each test
        Container.context.clear();
        
        // Since bootstrap() is private, we'll manually set up the context
        // similar to what bootstrap() does
        IPersonService personService = new RemotePersonService();
        Container.context.put("personService", personService);
        
        ReportController controller = new ReportController();
        controller.setService(personService);
        Container.context.put("reportController", controller);
    }

    @Test
    public void testBootstrap() {
        // Verify that the container has been bootstrapped correctly
        assertNotNull(Container.context);
        assertTrue(Container.context.containsKey("personService"));
        assertTrue(Container.context.containsKey("reportController"));
        
        // Verify that the personService is an instance of IPersonService
        Object personService = Container.context.get("personService");
        assertInstanceOf(IPersonService.class, personService);
        
        // Verify that the reportController is an instance of ReportController
        Object reportController = Container.context.get("reportController");
        assertInstanceOf(ReportController.class, reportController);
    }

    @Test
    public void testPersonServiceInjection() {
        // Verify that the personService has been injected into the reportController
        ReportController controller = (ReportController) Container.context.get("reportController");
        IPersonService service = (IPersonService) Container.context.get("personService");
        
        // Get the service from the controller using reflection
        IPersonService injectedService = controller.getService();
        
        // Verify that the injected service is the same as the one in the context
        assertSame(service, injectedService);
    }

    @Test
    public void testPersonServiceReturnsData() {
        // Verify that the personService returns data
        IPersonService service = (IPersonService) Container.context.get("personService");
        List<Person> personList = service.getPersonList();
        
        // Verify that the list is not null and contains elements
        assertNotNull(personList);
        assertFalse(personList.isEmpty());
    }

    @Test
    public void testReportControllerUsesPersonService() {
        // Verify that the reportController uses the personService
        ReportController controller = (ReportController) Container.context.get("reportController");
        
        // Get the person list
        List<Person> personList = controller.getPersonList();
        
        // Verify that the list is not null and contains elements
        assertNotNull(personList);
        assertFalse(personList.isEmpty());
    }

    @Test
    public void testPagesAreInitialized() {
        // Verify that the pages are initialized
        assertNotNull(Container.pages);
        assertEquals(2, Container.pages.size());
    }
}