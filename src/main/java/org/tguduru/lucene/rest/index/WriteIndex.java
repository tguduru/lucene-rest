package org.tguduru.lucene.rest.index;

import com.cerner.message.center.referral.directory.model.*;

import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.tguduru.lucene.rest.config.LuceneIndexConfig;
import org.tguduru.lucene.rest.model.Product;

import java.io.IOException;
import java.util.List;
import java.util.Set;

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

    public void writeBigIndex(final List<Provider> providers) throws IOException {
        final IndexWriter indexWriter = SingletonIndexWriter.getInstance(luceneIndexConfig).getIndexWriter();
        final List<List<Provider>> batchProviders = Lists.partition(providers, 1000);
        final int completedCount = 1000;
        int cycle = 0;
        for (final List<Provider> providerList : batchProviders) {
            final List<Document> documents = Lists.newArrayList();
            for (final Provider provider : providerList) {
                final Document document = new Document();
                final TextField firstNameField = new TextField("firstName", provider.getFirstName(), Field.Store.YES);
                final TextField fullNameField = new TextField("fullName", provider.getFullName(), Field.Store.YES);
                final TextField lastNameField = new TextField("lastName", provider.getLastName(), Field.Store.YES);
                final TextField specialityField = new TextField("speciality", provider.getSpeciality(), Field.Store.YES);
                final LongField idField = new LongField("id", provider.getIdentifier(), Field.Store.YES);
                document.add(firstNameField);
                document.add(fullNameField);
                document.add(lastNameField);
                document.add(specialityField);
                document.add(idField);

                final Set<Tag> tags = provider.getTags();
                for (final Tag tag : tags) {
                    final TextField tagTypeField = new TextField("tagType", tag.getTagType(), Field.Store.YES);
                    final TextField tagValueField = new TextField("tagValue", tag.getValue(), Field.Store.YES);
                    document.add(tagTypeField);
                    document.add(tagValueField);
                }

                final Set<Location> locations = provider.getLocation();
                for (final Location location : locations) {
                    final TextField locationField = new TextField("location", location.getName(), Field.Store.YES);
                    document.add(locationField);
                    final Address address = location.getAddress();
                    final TextField streetAddress1Field = new TextField("streetAddress1", address.getStreetAddress1(),
                            Field.Store.YES);
                    final TextField streetAddress2Field = new TextField("streetAddress2", address.getStreetAddress2(),
                            Field.Store.YES);
                    final TextField streetAddress3Field = new TextField("streetAddress3", address.getStreetAddress3(),
                            Field.Store.YES);
                    final TextField streetAddress4Field = new TextField("streetAddress4", address.getStreetAddress4(),
                            Field.Store.YES);
                    final TextField cityField = new TextField("city", address.getCity(), Field.Store.YES);
                    final TextField stateField = new TextField("state", address.getState(), Field.Store.YES);
                    final TextField zipField = new TextField("zip", address.getZip(), Field.Store.YES);
                    StoredField stringField = new StoredField("name",provider.getFirstName());
                    document.add(streetAddress1Field);
                    document.add(streetAddress2Field);
                    document.add(streetAddress3Field);
                    document.add(streetAddress4Field);
                    document.add(cityField);
                    document.add(stateField);
                    document.add(zipField);
                    final Phone phone = location.getPhone();
                    final StringField phoneNumber = new StringField("contact", phone.getPhoneNumber(), Field.Store.YES);
                    final StringField type = new StringField("contactType", phone.getType(), Field.Store.YES);
                    document.add(phoneNumber);
                    document.add(type);
                }
                documents.add(document);
            }

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
