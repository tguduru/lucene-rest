package org.tguduru.lucene.rest.index;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
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

/**
 * An indexer listener which starts the indexing process after starting the REST container.
 * {@link ApplicationReadyEvent} fired once the Spring REST Application started, so at this time will do the indexing
 * process.
 * @author Guduru, Thirupathi Reddy
 * @modified 12/21/15
 */
@Component
public class IndexListener{// implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private DBPollingInterval dbPollingInterval;
    @Autowired
    private LuceneIndexConfig luceneIndexConfig;

    @Autowired
    private DataSourceProperties dataSourceProperties;

    //@Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        System.out.println("Lucene index starting ...");

        // read data from DB
        final ProductDAO productDAO = new ProductDAO(dataSourceProperties, luceneIndexConfig);
        final List<Product> products = productDAO.getProducts();

        // write index
        final Path path = FileSystems.getDefault().getPath(luceneIndexConfig.getDirectory(), luceneIndexConfig.getName());
        Directory directory = null;
        try {
            directory = new NIOFSDirectory(path);
            final WriteIndex writeIndex = new WriteIndex(luceneIndexConfig);
            writeIndex.writeIndex(products);
        } catch (final IOException e) {
            e.printStackTrace();
        }

        // update lastUpdateDateTime to a file
        if (!products.isEmpty()) {
            try {
                Config.writeLastIndexTime(products.get(products.size() - 1).getLastUpdateDateTime().toString(),
                        luceneIndexConfig);
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }

        // start change detection thread, which will monitor DB for changes and index them
        final ChangeDetectionThread changeDetectionThread = new ChangeDetectionThread(dbPollingInterval,
                dataSourceProperties, luceneIndexConfig);
        changeDetectionThread.run();
    }
}
