package org.nextreamlabs.bradme.config;

public interface IConfigBuilder {

    public IConfiguration parse_args(String[] args) throws ConfigurationException;

}
