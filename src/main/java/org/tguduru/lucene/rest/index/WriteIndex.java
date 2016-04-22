package org.tguduru.lucene.rest.index;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.tguduru.lucene.rest.config.LuceneIndexConfig;
import org.tguduru.lucene.rest.model.Product;

import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * Write the data into an index using Lucene
 * @author Guduru, Thirupathi Reddy
 * @modified 12/22/15
 */
public class WriteIndex {
    private final LuceneIndexConfig luceneIndexConfig;

    public WriteIndex(final LuceneIndexConfig indexConfig) {
        this.luceneIndexConfig = indexConfig;
    }

    public void writeIndex(final List<Product> products) throws IOException {
        final IndexWriter indexWriter = SingletonIndexWriter.getInstance(luceneIndexConfig).getIndexWriter();
        final List<Document> documents = Lists.newArrayList();
        for (final Product product : products) {
            final Document document = new Document();
            // index all fields, only TextField will tokenize and index the data
            final TextField nameField = new TextField("name", product.getName(), Field.Store.YES);
            final TextField priceField = new TextField("price", Double.toString(product.getPrice()), Field.Store.YES);
            final TextField descriptionField = new TextField("description", product.getDescription(), Field.Store.YES);
            final TextField lastUpdateDateTime = new TextField("lastUpdateDateTime", product.getLastUpdateDateTime()
                    .toString(), Field.Store.YES);
            document.add(nameField);
            document.add(priceField);
            document.add(descriptionField);
            document.add(lastUpdateDateTime);
            documents.add(document);
        }
        indexWriter.addDocuments(documents);
        indexWriter.commit();
        indexWriter.close();
    }

    public void writeBigIndex(final List<Product> products) throws IOException {
        final IndexWriter indexWriter = SingletonIndexWriter.getInstance(luceneIndexConfig).getIndexWriter();
        final List<List<Product>> batchProducts = Lists.partition(products, 1000);
        final int completedCount = 1000;
        int cycle = 0;
        for (final List<Product> productList : batchProducts) {
            // build documents add it to index

            indexWriter.addDocuments(documents);
            // indexWriter.commit();
            cycle++;
            if (cycle % 10 == 0)
                System.out.println("Completed Records : " + completedCount * cycle);
        }
        // committing finally to flush any data left in the writer, don't close this writer as this can be used for
        // updates as well.
        indexWriter.commit();
    }
}
