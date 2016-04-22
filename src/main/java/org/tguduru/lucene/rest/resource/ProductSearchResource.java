package org.tguduru.lucene.rest.resource;

import org.apache.lucene.index.CheckIndex;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MMapDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tguduru.lucene.rest.config.LuceneIndexConfig;
import org.tguduru.lucene.rest.index.ReadIndex;
import org.tguduru.lucene.rest.index.SingletonIndexWriter;
import org.tguduru.lucene.rest.model.Product;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;

/**
 * @author Guduru, Thirupathi Reddy
 * @modified 12/21/15
 */
@RestController
public class ProductSearchResource {
    @Autowired
    private LuceneIndexConfig luceneIndexConfig;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public List<Product> search(@RequestParam(value = "query") String searchQuery) throws IOException, ParseException {
        searchQuery = searchQuery.toLowerCase();

        final ReadIndex readIndex = new ReadIndex(luceneIndexConfig);
        return readIndex.searchProviders(searchQuery);
    }

    @RequestMapping("/")
    public String started() {
        return "Started REST Container";
    }

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public String getIndexStatus() throws IOException {
        final IndexWriter indexWriter = SingletonIndexWriter.getInstance(luceneIndexConfig).getIndexWriter();
        final Path path = FileSystems.getDefault().getPath(luceneIndexConfig.getDirectory(),
                luceneIndexConfig.getName());
        final Directory directory = new MMapDirectory(path);
        final CheckIndex checkIndex = new CheckIndex(directory);
        final CheckIndex.Status status = checkIndex.checkIndex();
        return status.segmentsFileName;
    }

}
