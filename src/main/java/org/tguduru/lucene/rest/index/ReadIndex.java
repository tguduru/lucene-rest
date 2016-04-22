package org.tguduru.lucene.rest.index;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.tguduru.lucene.rest.config.LuceneIndexConfig;
import org.tguduru.lucene.rest.model.Product;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * Reads data from an index using Lucene
 * @author Guduru, Thirupathi Reddy
 * @modified 12/22/15
 */
public class ReadIndex {

    final IndexSearcher indexSearcher;

    public ReadIndex(final LuceneIndexConfig luceneIndexConfig) {
        this.indexSearcher = SingletonIndexSearcher.getInstance(luceneIndexConfig).getIndexSearcher();
    }

    public List<Product> search(final String searchString) throws ParseException, IOException {
        final List<Product> products = Lists.newArrayList();
        final Analyzer standardAnalyzer = new WhitespaceAnalyzer();
        final BooleanQuery booleanQueryBuilder = new BooleanQuery();
        final Term term = new Term("name", searchString);
        final Query nameQuery = new WildcardQuery(term);
        booleanQueryBuilder.add(nameQuery, BooleanClause.Occur.SHOULD);
        final Query descriptionQuery = new QueryParser( "description", standardAnalyzer)
                .parse(searchString);
        booleanQueryBuilder.add(descriptionQuery, BooleanClause.Occur.SHOULD);
        final TopDocs topDocs = indexSearcher.search(booleanQueryBuilder, 5);
        final ScoreDoc[] resultDocs = topDocs.scoreDocs;
        for (final ScoreDoc doc : resultDocs) {
            final Document document = indexSearcher.doc(doc.doc);
            final String name = document.get("name");
            final String description = document.get("description");
            final double price = Double.parseDouble(document.get("price"));
            final Timestamp lastUpdateDateTime = Timestamp.valueOf(document.get("lastUpdateDateTime"));
            final Product product = new Product(name, description, price, lastUpdateDateTime);

            products.add(product);
        }
        return products;
    }

    public List<Product> searchProviders(final String searchString) throws ParseException, IOException {
        final List<Product> products = Lists.newArrayList();
        final Analyzer standardAnalyzer = new WhitespaceAnalyzer();
        final BooleanQuery booleanQueryBuilder = new BooleanQuery();
        final Query query = new QueryParser( "firstName", standardAnalyzer).parse(searchString);
        booleanQueryBuilder.add(query, BooleanClause.Occur.SHOULD);
        final TopDocs topDocs = indexSearcher.search(booleanQueryBuilder, 10);
        final ScoreDoc[] resultDocs = topDocs.scoreDocs;
        for (final ScoreDoc doc : resultDocs) {
            final Document document = indexSearcher.doc(doc.doc);
            final String name = document.get("firstName");
            final long id = Long.parseLong(document.get("id"));
            final String lastName = document.get("lastName");
            final String fullName = document.get("fullName");
            //add it to products
        }

        return products;
    }
}
