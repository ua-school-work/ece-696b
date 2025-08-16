package edu.arizona.josesosa.enterprise.view.render;

import edu.arizona.josesosa.enterprise.model.Person;

import java.util.List;

/**
 * Presentation-layer component responsible for rendering the People table HTML.
 * Keeps HTML generation and escaping concerns out of the controller.
 * <p>
 * Implemented using the Composite pattern (Node/Element/Text),
 * with an Abstract Factory (HtmlFactory) injected (no static calls).
 */
public class PeopleTableRenderer implements TableRenderer {

    private final HtmlFactory html;

    public PeopleTableRenderer(HtmlFactory html) {
        this.html = html;
    }

    public String render(List<Person> people) {
        StringBuilder sb = new StringBuilder();
        for (Person p : people) {
            Node row = html.tr(
                    html.td(html.text(html.escape(p.getName()))),
                    html.td(html.text(html.escape(p.getEmail()))),
                    html.td(html.text(p.getAge() == null ? "" : html.escape(String.valueOf(p.getAge())))),
                    html.td(
                            html.a("/edit?id=" + p.getId(), html.text("Edit")),
                            html.text(" | "),
                            html.a("/delete?id=" + p.getId(), html.text("Delete")).attr("onclick", "return confirm('Delete?');")
                    )
            );
            sb.append(row.render()).append('\n');
        }
        return sb.toString();
    }
}
