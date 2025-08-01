package edu.arizona.josesosa.structural.adapter.container;

import edu.arizona.josesosa.structural.adapter.business.IPersonService;
import edu.arizona.josesosa.structural.adapter.business.RemotePersonService;
import edu.arizona.josesosa.structural.adapter.view.ReportController;
import edu.arizona.josesosa.structural.adapter.view.page.Page;
import edu.arizona.josesosa.structural.adapter.view.page.PersonListPage;
import edu.arizona.josesosa.structural.adapter.view.page.ReportPage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Container {

    public static Map<String, Object> context = new HashMap<String, Object>();
    public static List<Page> pages = Arrays.asList(new Page[]{new PersonListPage(), new ReportPage()});


    private static void bootstrap() {
        // bootstrap normally in XML
        // Using RemotePersonService instead of PersonService
        IPersonService personService = new RemotePersonService();
        context.put("personService", personService);

        ReportController controller = new ReportController();
        controller.setService(personService);
        context.put("reportController", controller);
    }

    public static void main(String[] args) {
        bootstrap();

        for (Page page : pages) {
            page.render();
        }
    }
}
