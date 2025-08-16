package edu.arizona.josesosa.enterprise.controller;

import edu.arizona.josesosa.enterprise.model.Person;
import edu.arizona.josesosa.enterprise.service.PersonService;
import edu.arizona.josesosa.enterprise.view.render.TableRenderer;

import java.io.IOException;
import java.util.Map;

/**
 * Controller handling CRUD workflows for {@link Person}.
 * Bridges HTTP/request flow (via BaseController template methods) with the {@link PersonService}.
 */
public class PersonController extends BaseController {
    private final PersonService service;
    private final TableRenderer tableRenderer;

    private String statusMessage = "";
    private Person editing = null;

    /**
     * Create a controller bridging UI actions to the person service.
     * @param service application service for person operations
     * @param tableRenderer table renderer for producing HTML rows
     */
    public PersonController(PersonService service, TableRenderer tableRenderer) {
        this.service = service;
        this.tableRenderer = tableRenderer;
    }

    /**
     * Current user-facing status message to display after actions.
     * @return non-null status text
     */
    public String statusMessage() {
        return statusMessage == null ? "" : statusMessage;
    }

    /**
     * Name value for the edit form when in editing mode.
     * @return current editing name or empty string
     */
    public String name() {
        return editing == null ? "" : nullSafe(editing.getName());
    }

    /**
     * Page title displayed at the top of the page.
     */
    public String title() {
        return editing == null ? "Person Directory" : "Edit Person";
    }

    /**
     * Label for the main submit button.
     */
    public String submitLabel() {
        return editing == null ? "Add" : "Update";
    }

    /**
     * Hidden id field value when editing an existing person.
     */
    public String idHidden() {
        return editing == null ? "" : nullSafe(editing.getId());
    }

    /**
     * Email value for the edit form when in editing mode.
     */
    public String email() {
        return editing == null ? "" : nullSafe(editing.getEmail());
    }

    /**
     * Age value for the edit form when in editing mode.
     */
    public String age() {
        return (editing == null || editing.getAge() == null) ? "" : String.valueOf(editing.getAge());
    }

    private static String nullSafe(String s) {
        return s == null ? "" : s;
    }

    /**
     * Render the table rows for all people using the view-layer renderer.
     * Bound to template token {{peopleTable}}.
     */
    public String peopleTable() {
        return tableRenderer.render(service.findAll());
    }

    /**
     * The form action URL that the template binds to; ensures Enter submits to the submit route.
     */
    public String formAction() {
        return "/submit";
    }

    /**
     * Add a new person with provided fields.
     */
    public void add(String name, String email, Integer age) {
        service.add(name, email, age);
    }

    /**
     * Update an existing person by id.
     */
    public void update(String id, String name, String email, Integer age) {
        service.update(id, name, email, age);
    }

    /**
     * Delete a person by id.
     */
    public void delete(String id) {
        service.delete(id);
    }

    /**
     * Begin editing a person, loading state into the controller.
     */
    public void beginEdit(String id) {
        this.editing = service.findById(id).orElse(null);
    }

    /**
     * Clear any editing state.
     */
    public void clearEditing() {
        this.editing = null;
    }

    /**
     * Set a custom status message to show in the UI.
     */
    public void setStatus(String msg) {
        this.statusMessage = msg;
    }


    @Override
    protected void beforeRoot() throws IOException {
        this.statusMessage = "";
    }

    /**
     * Represents and validates the person form submission.
     * This class encapsulates the logic for parsing and validating the form data
     * from the HTTP request, separating these concerns from the controller.
     */
    private static class PersonForm {
        private final String id;
        private final String name;
        private final String email;
        private final String ageStr;
        private Integer age = null;

        public PersonForm(Map<String, String> form) {
            this.id = form.getOrDefault("id", "");
            this.name = form.getOrDefault("name", "").trim();
            this.email = form.getOrDefault("email", "").trim();
            this.ageStr = form.getOrDefault("age", "").trim();
        }

        /**
         * Validates the form data. For now, it only validates the age format.
         * Throws IllegalArgumentException on validation failure.
         */
        public void validate() {
            if (!ageStr.isBlank()) {
                try {
                    this.age = Integer.parseInt(ageStr);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid age");
                }
            }
        }

        public boolean isNew() {
            return id.isBlank();
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public Integer getAge() {
            return age;
        }
    }

    @Override
    protected void onSubmit(Map<String, String> form) throws IOException {
        try {
            PersonForm personForm = new PersonForm(form);
            personForm.validate();

            if (personForm.isNew()) {
                add(personForm.getName(), personForm.getEmail(), personForm.getAge());
                if (statusMessage().isEmpty()) setStatus("Added successfully");
            } else {
                update(personForm.getId(), personForm.getName(), personForm.getEmail(), personForm.getAge());
                if (statusMessage().isEmpty()) setStatus("Updated successfully");
            }
        } catch (IllegalArgumentException e) {
            setStatus(e.getMessage());
        }
        clearEditing();
    }

    @Override
    protected void onEdit(Map<String, String> query) throws IOException {
        String id = query.get("id");
        if (id != null) {
            beginEdit(id);
        }
    }

    @Override
    protected void onDelete(Map<String, String> query) throws IOException {
        String id = query.get("id");
        if (id != null) {
            delete(id);
            setStatus("Deleted successfully");
        }
        clearEditing();
    }
}
