package org.nextreamlabs.bradme.interfaces.config;

import org.nextreamlabs.bradme.implementation.exceptions.InvalidConfigurationException;

public interface IConfigBuilder {

    public IConfiguration parseArgs(String[] args) throws InvalidConfigurationException;

}
