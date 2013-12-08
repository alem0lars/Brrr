package org.nextreamlabs.bradme.config;

public final class GlobalConfig {

    private static IConfiguration INSTANCE = null;

    public static IConfiguration globalConfig() {
        return INSTANCE;
    }

    public static void set(IConfiguration config) {
        if (INSTANCE != null) {
            throw new RuntimeException("The overwrite of a global configuration is forbidden.");
        }
        INSTANCE = config;
    }

}
