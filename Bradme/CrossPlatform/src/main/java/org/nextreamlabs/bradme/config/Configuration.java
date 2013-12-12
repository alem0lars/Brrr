package org.nextreamlabs.bradme.config;

public class Configuration implements IConfiguration {

  // { Construction

  protected Configuration() { }

  public static IConfiguration create() {
    return new Configuration();
  }

  // }

}
