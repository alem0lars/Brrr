package org.nextreamlabs.bradme.exceptions;

import java.util.Locale;

public class UnsupportedLocaleException extends RuntimeException {

  protected Locale currentLocale;

  // { Construction

  private UnsupportedLocaleException(Locale currentLocale) {
    this.currentLocale = currentLocale;
  }

  public static UnsupportedLocaleException create(Locale currentLocale) {
    return new UnsupportedLocaleException(currentLocale);
  }

  // }

  // { RuntimeException implementation

  @Override
  public String toString() {
    return (String.format("Unsupported locale: %s", this.currentLocale));
  }

  @Override
  public String getMessage() {
    return this.toString();
  }

  // }

}
