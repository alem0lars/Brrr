package org.nextreamlabs.bradme.implementation.exceptions;

public class CannotCreateModelException
    extends RuntimeException
    implements IException{

  protected final String modelName;

  // { Construction

  private CannotCreateModelException(String modelName) {
    this.modelName = modelName;
  }

  public static CannotCreateModelException create(String modelName) {
    return new CannotCreateModelException(modelName);
  }

  // }

  // { RuntimeException implementation

  @Override
  public String toString() {
    return String.format("CannotCreateModelException:<reason=%s>", this.modelName);
  }

  @Override
  public String getMessage() {
    return String.format("Cannot create the model: %s", this.modelName);
  }

  // }

}
