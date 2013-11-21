package org.nextreamlabs.bradme.support;

import java.util.Locale;

public class Locales {

  private static final Locale[] SUPPORTED_LOCALES;
  private static final Locale CURRENT_LOCALE;
  private static final String DEFAULT_LOCALE_KEY;

  public class AvailableKeys {

    private static final String IT = "it";
    private static final String EN = "en";

  }

  static {

    SUPPORTED_LOCALES = new Locale[] {
        new Locale(AvailableKeys.EN),
        new Locale(AvailableKeys.IT)
    };

    DEFAULT_LOCALE_KEY = AvailableKeys.EN;

    Locale currentLocale = null;
    for (Locale supportedLocale : SUPPORTED_LOCALES) {
      if ((supportedLocale.getLanguage().equals(Locale.getDefault().getLanguage())) ||
          ((currentLocale == null) && supportedLocale.getLanguage().equals(DEFAULT_LOCALE_KEY))) {
        currentLocale = supportedLocale;
      }
    }
    if (currentLocale == null) {
      throw new RuntimeException(String.format("Unsupported locale: %s", currentLocale));
    }
    CURRENT_LOCALE = currentLocale;

  }

  public static Locale getCurrent() {
    return CURRENT_LOCALE;
  }

}
