package org.nextreamlabs.bradme.support;

import java.util.ResourceBundle;

public class Resources {

  private static final ResourceBundle STRING_RESOURCES;

  // { Construction

  static {
    STRING_RESOURCES = ResourceBundle.getBundle(Keys.RESOURCE_FQN.STRINGS, Locales.getCurrent());
  }

  private Resources() {}

  // }

  /**
   * Gets the localized string for the provided key.
   *
   * @param stringKey The key.
   * @return The localized string.
   */
  public static String t(String stringKey) {
    return STRING_RESOURCES.getString(stringKey);
  }

  public static class Keys {

    private Keys() {}

    public interface I18N {

      public static final String BRADME_TITLE = "bradme_title";

      public static final String STARTING_NAME = "starting_name";
      public static final String STARTING_DESC = "starting_desc";
      public static final String STARTED_NAME = "started_name";
      public static final String STARTED_DESC = "started_desc";
      public static final String STOPPED_NAME = "stopped_name";
      public static final String STOPPED_DESC = "stopped_desc";
    }

    public interface RESOURCE_FQN {

      public static final String STRINGS = "org.nextreamlabs.bradme.resources.strings";

    }

  }

}
