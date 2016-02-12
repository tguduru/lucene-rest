package org.tguduru.lucene.rest.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Guduru, Thirupathi Reddy
 * @modified 12/22/15
 */
@Component
@ConfigurationProperties(prefix = "index")
public class LuceneIndexConfig {
    private String directory;
    private String name;

    public LuceneIndexConfig(String directory, String name) {
        this.directory = directory;
        this.name = name;
    }

    public LuceneIndexConfig() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(final String directory) {
        this.directory = directory;
    }

    @Override
    public String toString() {
        return "LuceneIndexConfig{" + "directory='" + directory + '\'' + ", name='" + name + '\'' + '}';
    }
}
