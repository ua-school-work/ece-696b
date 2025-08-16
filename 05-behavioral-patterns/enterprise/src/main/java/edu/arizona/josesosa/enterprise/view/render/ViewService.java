package edu.arizona.josesosa.enterprise.view.render;

import java.io.IOException;

/** Port interface for rendering HTML views. */
public interface ViewService {
    String render() throws IOException;
}
