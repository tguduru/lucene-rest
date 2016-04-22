package org.tguduru.lucene.rest.config;

import java.util.Map;


/**
 * @author Guduru, Thirupathi Reddy
 * @modified 2/14/16
 */
public class CommandLineConfig {
    private static final Map<String, String> config = Maps.newHashMap();

    public static void addConfig(final String key, final String value) {
        config.put(key, value);
    }

    public static String getConfig(final String key) {
        return config.get(key);
    }
}
