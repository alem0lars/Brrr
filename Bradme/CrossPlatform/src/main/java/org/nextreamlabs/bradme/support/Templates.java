package org.nextreamlabs.bradme.support;

import org.nextreamlabs.bradme.exceptions.TemplateNotFoundException;

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
    String templatePath = String.format(TEMPLATE_PATH, templateName);
    URL templateURL = Templates.class.getResource(templatePath);
    if (templateURL == null) {
      throw TemplateNotFoundException.create(templatePath);
    }
    return templateURL;
  }

}
