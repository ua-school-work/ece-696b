package edu.arizona.josesosa.enterprise.application;

import edu.arizona.josesosa.enterprise.controller.PersonController;
import edu.arizona.josesosa.enterprise.dao.PersonRepository;
import edu.arizona.josesosa.enterprise.dao.Repository;
import edu.arizona.josesosa.enterprise.http.*;
import edu.arizona.josesosa.enterprise.service.MyPersonService;
import edu.arizona.josesosa.enterprise.service.PersonService;
import edu.arizona.josesosa.enterprise.view.bind.HtmlMethodBindingTemplateRenderer;
import edu.arizona.josesosa.enterprise.view.bind.TemplateRenderer;
import edu.arizona.josesosa.enterprise.view.render.*;

/**
 * Builder responsible for wiring the application components and producing a ready-to-run {@link App}.
 * Uses a minimal DI container to assemble service, presentation, and HTTP layers.
 */
public class AppBuilder {
    private int port = 8088;

    /**
     * Sets the TCP port the embedded HTTP server will listen on.
     * @param port the desired port
     * @return this builder for fluent chaining
     */
    public AppBuilder withPort(int port) {
        this.port = port;
        return this;
    }

    /**
     * Builds a fully-wired {@link App} instance ready to be started.
     * @return the configured application
     */
    public App build() {
        SimpleDiContainer container = createContainer();
        bindServiceLayer(container);
        bindPresentationLayer(container);

        PersonController controller = wireController(container);
        ServerProxy serverProxy = wireServerProxy(container, controller);

        return new App(port, serverProxy);
    }

    private SimpleDiContainer createContainer() {
        return new SimpleDiContainer();
    }

    private void bindServiceLayer(SimpleDiContainer container) {
        container.singleton(PersonRepository.class, PersonRepository::new);
        container.bind(Repository.class, () -> container.get(PersonRepository.class));
        container.singleton(MyPersonService.class, () -> new MyPersonService(container.get(Repository.class)));
        container.bind(PersonService.class, () -> container.get(MyPersonService.class));
    }

    private void bindPresentationLayer(SimpleDiContainer container) {
        container.singleton(Html.class, Html::new);
        container.singleton(HtmlFactory.class, () -> container.get(Html.class));
        container.singleton(TableRenderer.class, () -> new PeopleTableRenderer(container.get(HtmlFactory.class)));
        container.singleton(PersonController.class, () -> new PersonController(container.get(PersonService.class), container.get(TableRenderer.class)));
        container.singleton(TemplateRenderer.class, () -> new HtmlMethodBindingTemplateRenderer(container.get(PersonController.class)));
        container.singleton(ViewService.class, () -> new HtmlViewService(container.get(TemplateRenderer.class)));
        container.singleton(RichHttpExchange.class, MyHttpExchange::new);
        container.singleton(HttpRequestHandler.class, () -> new MyHttpRequestHandler(container.get(RichHttpExchange.class)));
    }

    private PersonController wireController(SimpleDiContainer container) {
        PersonController controller = container.get(PersonController.class);
        ViewService views = container.get(ViewService.class);
        HttpRequestHandler http = container.get(HttpRequestHandler.class);
        controller.setViews(views);
        controller.setHttp(http);
        return controller;
    }

    private ServerProxy wireServerProxy(SimpleDiContainer container, PersonController controller) {
        RichHttpExchange rich = container.get(RichHttpExchange.class);
        return new ServerProxy(port, controller, rich);
    }
}
