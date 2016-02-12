package org.tguduru.lucene.rest.index;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MMapDirectory;
import org.apache.lucene.store.NIOFSDirectory;
import org.tguduru.lucene.rest.config.DBPollingInterval;
import org.tguduru.lucene.rest.config.DataSourceProperties;
import org.tguduru.lucene.rest.config.LuceneIndexConfig;
import org.tguduru.lucene.rest.model.Product;
import org.tguduru.lucene.rest.util.Config;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * A thread which polls the database for any changes for every
 * {@link org.tguduru.lucene.rest.config.DBPollingInterval#polling} milliseconds. And then call the {@link WriteIndex}
 * to add/update the indexes.
 * @author Guduru, Thirupathi Reddy
 * @modified 12/23/15
 */
public class ChangeDetectionThread implements Runnable {
    private final DBPollingInterval dbPollingInterval;
    private final DataSourceProperties dataSourceProperties;
    private final LuceneIndexConfig luceneIndexConfig;

    public ChangeDetectionThread(final DBPollingInterval dbPollingInterval, final DataSourceProperties dataSourceProperties,
                                 final LuceneIndexConfig luceneIndexConfig) {
        this.dbPollingInterval = dbPollingInterval;
        this.dataSourceProperties = dataSourceProperties;
        this.luceneIndexConfig = luceneIndexConfig;
    }

    @Override
    public void run() {
        System.out.println("Change Detection Thread Started ****");
        while (true) {
            try {
                System.out.println("*** Detecting Changes ***");
                TimeUnit.MILLISECONDS.sleep(dbPollingInterval.getPolling());
                // read data from DB
                final ProductDAO productDAO = new ProductDAO(dataSourceProperties, luceneIndexConfig);
                final List<Product> products = productDAO.getProducts();
                System.out.println(" Detected changes - " + products.size());
                // update lastUpdateDateTime to a file
                if (!products.isEmpty()) {
                    // write index
                    final Path path = FileSystems.getDefault().getPath(luceneIndexConfig.getDirectory(), luceneIndexConfig.getName());
                    Directory directory = null;
                    try {
                        directory = new MMapDirectory(path);
                        final WriteIndex writeIndex = new WriteIndex(luceneIndexConfig);
                        writeIndex.writeIndex(products);
                    } catch (final IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        Config.writeLastIndexTime(products.get(products.size() - 1).getLastUpdateDateTime().toString(),
                                luceneIndexConfig);
                    } catch (final IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
