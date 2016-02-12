package org.tguduru.lucene.rest.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Guduru, Thirupathi Reddy
 * @modified 12/22/15
 */
@Component
@ConfigurationProperties(prefix = "database")
public class DBPollingInterval {
    private long polling;

    public DBPollingInterval() {
    }

    public DBPollingInterval(final long polling) {
        this.polling = polling;
    }

    public long getPolling() {
        return polling;
    }

    public void setPolling(final long polling) {
        this.polling = polling;
    }

    @Override
    public String toString() {
        return "DBPollingInterval{" +
                "polling=" + polling +
                '}';
    }
}
