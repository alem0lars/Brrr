package org.nextreamlabs.bradme.exceptions;

public class TemplateNotFoundException extends RuntimeException implements ITemplateNotFoundException {

  protected final String templatePath;

  // { Construction

  private TemplateNotFoundException(String templatePath) {
    this.templatePath = templatePath;
  }

  public static TemplateNotFoundException create(String templatePath) {
    return new TemplateNotFoundException(templatePath);
  }

  // }

  // { RuntimeException implementation

  @Override
  public String toString() {
    return String.format("Template not found: %s", this.templatePath);
  }

  @Override
  public String getMessage() {
    return this.toString();
  }

  // }

  // { ITemplateNotFoundException implementation

  public String getTemplatePath() {
    return this.templatePath;
  }

  // }

}
