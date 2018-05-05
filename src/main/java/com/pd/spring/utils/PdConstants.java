package com.pd.spring.utils;

/**
 * @author peramdy
 */
public abstract class PdConstants {

    public static final String PROPERTY_NAME_SEPARATOR = ".";

    public static final String APPLICATION_PREFIX = "application";

    public static final String PD_PREFIX = "pd";

    private static final String REDIS_PREFIX = "redis";

    private static final String URL_PREFIX = "url";

    private static final String PORT_PREFIX = "port";

    private static final String IP_PREFIX = "ip";

    private static final String ADDRESSES_PREFIX = "addresses";

    private static final String PD_APPLICATION_PREFIX = PD_PREFIX + PROPERTY_NAME_SEPARATOR + APPLICATION_PREFIX;

    public static final String PD_PACKAGE_PREFIX = "com" + PROPERTY_NAME_SEPARATOR + PD_PREFIX;

    public static final String PD_REDIS_PREFIX = PD_APPLICATION_PREFIX + PROPERTY_NAME_SEPARATOR + REDIS_PREFIX;

    public static final String PD_REDIS_IP = PD_REDIS_PREFIX + PROPERTY_NAME_SEPARATOR + IP_PREFIX;

    public static final String PD_REDIS_PORT = PD_REDIS_PREFIX + PROPERTY_NAME_SEPARATOR + PORT_PREFIX;

    public static final String PD_REDIS_ADDREDDES = PD_REDIS_PREFIX + PROPERTY_NAME_SEPARATOR + ADDRESSES_PREFIX;

    public static final String PD_REDIS_TIMEOUT = PD_REDIS_PREFIX + PROPERTY_NAME_SEPARATOR + "timeout";

    public static final String PD_REDIS_MAX_TOTAL = PD_REDIS_PREFIX + PROPERTY_NAME_SEPARATOR + "maxTotal";

    public static final String PD_REDIS_MAX_IDLE = PD_REDIS_PREFIX + PROPERTY_NAME_SEPARATOR + "maxIdle";

    public static final String PD_REDIS_MIN_IDLE = PD_REDIS_PREFIX + PROPERTY_NAME_SEPARATOR + "minIdle";

}
