package org.nextreamlabs.bradme.implementation.config;

import org.nextreamlabs.bradme.interfaces.config.IConfiguration;

public class Configuration
    implements IConfiguration {

  // { Construction

  protected Configuration() { }

  public static IConfiguration create() {
    return new Configuration();
  }

  // }

}
