package org.nextreamlabs.bradme.support;

import org.nextreamlabs.bradme.exceptions.UnavailableL10N;
import org.nextreamlabs.bradme.exceptions.UnsupportedLocaleException;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Utility class to handle localization.
 */
public class L10N {

  /**
   * The locale used in the application.
   */
  private static Locale currentLocale;

  /**
   * The bundle which contains all of the localization resources.
   */
  private static ResourceBundle l10nResources;

  /**
   * The available languages are values of this enum.
   */
  public enum Lang {
    IT("it"),
    EN("en");

    protected String lang;

    Lang(String lang) {
      this.lang = lang;
    }

    @Override
    public String toString() {
      return this.lang;
    }
  }

  /* { Construction */

  /**
   * Initialize the static variables, like:
   * <ul>
   *  <li>currentLocale</li>
   *  <li>l10nResources</li>
   * </ul>
   */
  static {

    Locale[] supportedLocales = new Locale[]{
        new Locale(Lang.EN.toString()),
        new Locale(Lang.IT.toString())
    };

    Lang defaultLanguage = Lang.EN;

    for (Locale supportedLocale : supportedLocales) {
      if ((supportedLocale.getLanguage().equals(Locale.getDefault().getLanguage())) ||
          ((currentLocale == null) && supportedLocale.getLanguage().equals(defaultLanguage.toString()))) {
        currentLocale = supportedLocale;
      }
    }
    if (currentLocale == null) {
      throw UnsupportedLocaleException.create(currentLocale);
    }

    l10nResources = ResourceBundle.getBundle("org.nextreamlabs.bradme.resources.strings", L10N.getCurrent());
  }

  /**
   * This constructor is private to prevent instantiation.
   */
  private L10N() { }

  /* } */

  /**
   * @return The current locale.
   */
  public static Locale getCurrent() {
    return currentLocale;
  }

  /**
   * Gets the localized string for the provided key.
   *
   * @param stringKey The key.
   * @return The localized string.
   */
  public static String t(String stringKey) {
    try {
      return l10nResources.getString(stringKey);
    } catch(Exception exc) {
      throw UnavailableL10N.create(stringKey);
    }
  }

}
