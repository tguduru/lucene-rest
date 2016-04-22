package org.tguduru.lucene.rest.index;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MMapDirectory;
import org.tguduru.lucene.rest.config.CommandLineConfig;
import org.tguduru.lucene.rest.config.ConfigParameters;
import org.tguduru.lucene.rest.config.LuceneIndexConfig;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * @author Guduru, Thirupathi Reddy
 * @modified 2/3/16
 */
public class SingletonIndexSearcher {

    private final IndexSearcher indexSearcher;
    private static SingletonIndexSearcher ourInstance = null;

    public static synchronized SingletonIndexSearcher getInstance(final LuceneIndexConfig luceneIndexConfig) {
        if (ourInstance == null)
            ourInstance = new SingletonIndexSearcher();
        return ourInstance;
    }

    private SingletonIndexSearcher() {
        final Path path = FileSystems.getDefault().getPath(CommandLineConfig.getConfig(ConfigParameters.indexLocation));
        try {
            final Directory directory = new MMapDirectory(path);
            final IndexReader indexReader = DirectoryReader.open(directory);
            indexSearcher = new IndexSearcher(indexReader);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public IndexSearcher getIndexSearcher() {
        return indexSearcher;
    }

    //this will reopen the IndexReader which will reload the index so updates will be available for search.
    public static synchronized void refresh() {
        ourInstance = new SingletonIndexSearcher();
    }
}
