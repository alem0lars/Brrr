package org.nextreamlabs.bradme.implementation.config;

import org.nextreamlabs.bradme.interfaces.config.IConfiguration;

public final class GlobalConfig {

  /**
   * Hold the global configuration
   */
  private static IConfiguration INSTANCE = null;

  // { Construction

  private GlobalConfig() { }

  // }

  /**
   * @return The global configuration instance.
   */
  public static IConfiguration getInstance() {
    return INSTANCE;
  }

  /**
   * Initializes the global configuration with the provided configuration.
   *
   * @param config The configuration which should become the global configuration.
   */
  public static void initialize(IConfiguration config) {
    if (INSTANCE != null) {
      throw new RuntimeException("The overwrite of a global configuration is forbidden.");
    }
    INSTANCE = config;
  }

}
