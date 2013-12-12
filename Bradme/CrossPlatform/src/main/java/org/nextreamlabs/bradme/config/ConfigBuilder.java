package org.nextreamlabs.bradme.config;

import org.nextreamlabs.bradme.exceptions.InvalidConfigurationException;

public class ConfigBuilder implements IConfigBuilder {

    protected ConfigBuilder() { }

    public static IConfigBuilder create() {
        return new ConfigBuilder();
    }

    @Override
    public IConfiguration parseArgs(String[] args) throws InvalidConfigurationException {
        return Configuration.create();
    }

}
