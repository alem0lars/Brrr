package org.nextreamlabs.bradme.exceptions;

public class UnavailableL10N extends RuntimeException implements IException {

  protected final String stringKey;

  // { Construction

  private UnavailableL10N(String stringKey) {
    this.stringKey = stringKey;
  }

  public static UnavailableL10N create(String stringKey) {
    return new UnavailableL10N(stringKey);
  }

  // }

  // { RuntimeException implementation

  @Override
  public String toString() {
    return String.format("Unavailable translation for: %s", this.stringKey);
  }

  @Override
  public String getMessage() {
    return this.toString();
  }

  // }

}
