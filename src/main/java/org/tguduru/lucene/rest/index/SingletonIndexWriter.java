package org.tguduru.lucene.rest.index;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.ConcurrentMergeScheduler;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.TieredMergePolicy;
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
 * @modified 2/4/16
 */
public class SingletonIndexWriter {
    private static SingletonIndexWriter ourInstance = null;
    private final IndexWriter indexWriter;

    public static SingletonIndexWriter getInstance(final LuceneIndexConfig luceneIndexConfig) {
        if (ourInstance == null)
            ourInstance = new SingletonIndexWriter(luceneIndexConfig);
        return ourInstance;
    }

    private SingletonIndexWriter(final LuceneIndexConfig luceneIndexConfig) {
        final Analyzer analyzer = new StandardAnalyzer();
        final IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        indexWriterConfig.setMergePolicy(new TieredMergePolicy()); // merge policy
        indexWriterConfig.setMergeScheduler(new ConcurrentMergeScheduler()); // merge scheduler
        indexWriterConfig.setRAMBufferSizeMB(64);// which is Lucene sweet spot for RAM buffer.
        indexWriterConfig.setUseCompoundFile(false);
        indexWriterConfig.setMaxBufferedDeleteTerms(1);
        try {
            final Path path = FileSystems.getDefault().getPath(CommandLineConfig.getConfig(ConfigParameters.indexLocation));
            final Directory directory = new MMapDirectory(path);
            indexWriter = new IndexWriter(directory, indexWriterConfig);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public IndexWriter getIndexWriter() {
        return indexWriter;
    }
}
