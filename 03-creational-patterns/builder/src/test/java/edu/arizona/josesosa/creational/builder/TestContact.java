package edu.arizona.josesosa.creational.builder;

/*
 * Based on https://github.com/progund/design-patterns-bi-directional-builder
 *
 */
public class TestContact {
    public static void main(String[] args) {
        Contact c = null;
        if (args.length != 0) {
            if (args[0].equalsIgnoreCase("text")) {
                c = new Contact(new ContactTextImporter());
            } else if (args[0].equalsIgnoreCase("gui")) {
                c = new Contact(new ContactSwingImporter());
            }
        } else {
            System.out.println("No args!");

            c = new Contact(new ContactSwingImporter());
//			c = new Contact(new ContactTextImporter());
        }
        System.out.println("Contact: " + c);
        ContactHTMLExporter html = new ContactHTMLExporter();
        c.export(html);
        System.out.println("HTML:");
        System.out.println(html);
        ContactCSVExporter csv = new ContactCSVExporter();
        c.export(csv);
        System.out.println("CSV:\n" + csv);
        System.out.println("Starting gui display...");
        GUIExporter gui = new GUIExporter();
        c.export(gui);
        new SwingDisplay(gui.getJPanel());
    }

    // Input CSV file with multiple lines
    // consider CSV file parser (Apache Commons CSV) - everyone

    // * All from the below
    // consider ExcelBuilder (Apache POI)
    // consider PDFBuilder (Apache PDF Box)
    // consider RTFBuilder (Apache FOP)
    // consider XMLBuilder (JAXB)

    // * Pick on of these four
    // consider HTMLBuilder
    // consider MDBuilder (MarkDown http://markdown.tautua.org/)
    // consider PPTBuilder (https://www.baeldung.com/apache-poi-slideshow/)
    // consider DOCBuilder (https://www.baeldung.com/docx4j)

}
