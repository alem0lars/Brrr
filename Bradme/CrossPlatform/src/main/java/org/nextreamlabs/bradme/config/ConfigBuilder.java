package org.nextreamlabs.bradme.config;

public class ConfigBuilder implements IConfigBuilder {

    protected ConfigBuilder() {

    }

    public static IConfigBuilder create() {
        return new ConfigBuilder();
    }

    @Override
    public IConfiguration parse_args(String[] args) throws ConfigurationException {
        return Configuration.create();
    }

}