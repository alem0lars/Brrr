package org.nextreamlabs.bradme.implementation.config;

import org.nextreamlabs.bradme.implementation.exceptions.InvalidConfigurationException;
import org.nextreamlabs.bradme.interfaces.config.IConfigBuilder;
import org.nextreamlabs.bradme.interfaces.config.IConfiguration;

public class ConfigBuilder
    implements IConfigBuilder {

  // { Construction

  protected ConfigBuilder() { }

  public static IConfigBuilder create() {
    return new ConfigBuilder();
  }

  // }

  // { IConfigBuilder implementation

  public IConfiguration parseArgs(String[] args) throws InvalidConfigurationException {
    return Configuration.create();
  }

  // }

}
