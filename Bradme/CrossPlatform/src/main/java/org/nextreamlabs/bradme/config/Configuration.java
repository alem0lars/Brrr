package org.nextreamlabs.bradme.config;

public class Configuration implements IConfiguration {

    protected Configuration() {
    }

    public static IConfiguration create() {
        return new Configuration();
    }

}