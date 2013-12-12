package org.nextreamlabs.bradme.config;

import org.nextreamlabs.bradme.exceptions.InvalidConfigurationException;

public interface IConfigBuilder {

    public IConfiguration parseArgs(String[] args) throws InvalidConfigurationException;

}
