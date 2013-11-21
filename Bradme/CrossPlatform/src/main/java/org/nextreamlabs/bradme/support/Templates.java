package org.nextreamlabs.bradme.support;

import java.net.URL;

public class Templates {

  private static final String TEMPLATE_PATH;

  // { Construction

  static {
    TEMPLATE_PATH = "/org/nextreamlabs/bradme/templates/%s.fxml";
  }

  private Templates() {}

  // }

  public static URL getTemplateURL(String templateName) {
    return Templates.class.getResource(String.format(TEMPLATE_PATH, templateName));
  }

}
