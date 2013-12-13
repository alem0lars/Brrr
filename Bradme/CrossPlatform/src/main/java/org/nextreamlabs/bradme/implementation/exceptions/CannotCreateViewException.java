package org.nextreamlabs.bradme.implementation.exceptions;

public class CannotCreateViewException
    extends RuntimeException
    implements IException {

  protected final String viewName;

  // { Construction

  private CannotCreateViewException(String viewName) {
    this.viewName = viewName;
  }

  public static CannotCreateViewException create(String viewName) {
    return new CannotCreateViewException(viewName);
  }

  // }

  // { RuntimeException implementation

  @Override
  public String toString() {
    return String.format("CannotCreateViewException:<reason=%s>", this.viewName);
  }

  @Override
  public String getMessage() {
    return String.format("Cannot create the view: %s", this.viewName);
  }

  // }

}
