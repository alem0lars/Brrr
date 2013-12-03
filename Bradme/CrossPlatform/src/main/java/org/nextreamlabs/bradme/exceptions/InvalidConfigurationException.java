package org.nextreamlabs.bradme.exceptions;

public class InvalidConfigurationException extends RuntimeException implements IException {

  protected final String message;

  // { Construction

  private InvalidConfigurationException(String message) {
    this.message = message;
  }

  public static InvalidConfigurationException create(String message) {
    return new InvalidConfigurationException(message);
  }

  // }

  // { RuntimeException implementation

  @Override
  public String toString() {
    return this.message;
  }

  @Override
  public String getMessage() {
    return this.toString();
  }

  // }

}
