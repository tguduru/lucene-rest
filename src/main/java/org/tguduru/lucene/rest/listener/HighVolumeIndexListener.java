package org.tguduru.lucene.rest.listener;

import com.cerner.message.center.referral.directory.model.*;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MMapDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.tguduru.lucene.rest.config.DBPollingInterval;
import org.tguduru.lucene.rest.config.DataSourceProperties;
import org.tguduru.lucene.rest.config.LuceneIndexConfig;
import org.tguduru.lucene.rest.index.WriteIndex;
import org.tguduru.lucene.rest.listener.data.*;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * @author Guduru, Thirupathi Reddy
 * @modified 2/2/16
 */
@Component
public class HighVolumeIndexListener implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private DBPollingInterval dbPollingInterval;
    @Autowired
    private LuceneIndexConfig luceneIndexConfig;

    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        System.out.println("---------------------");
        System.out.println(new Date() + " - Starting big index ....");

        final List<Tag> tags = TagData.getTags();
        final List<Phone> phones = PhoneData.getPhones();
        final List<Location.Builder> locationBuilders = LocationData.getLocations();
        final List<Address> addresses = AddressData.getAddresses();
        final List<Provider.Builder> providersBuilders = ProviderData.getProviders();
        final List<Provider> providers = Lists.newArrayList();
        // build 300K objects
        final Random random = new Random();
        for (long i = 0; i < 1000000; i++) {
            final int randomPlace = random.nextInt(20);
            final Provider.Builder providerBuilder = providersBuilders.get(randomPlace);
            final Address address = addresses.get(randomPlace);
            final Tag tag1 = tags.get(randomPlace);
            final Tag tag2 = tags.get(randomPlace / 2);
            final Set<Tag> tagSet = Sets.newHashSet();
            tagSet.add(tag1);
            tagSet.add(tag2);
            final Phone phone = phones.get(randomPlace);
            final Location.Builder locationBuilder = locationBuilders.get(randomPlace);
            locationBuilder.withAddress(address);
            locationBuilder.withPhone(phone);
            final Set<Location> locationSet = Sets.newHashSet();
            locationSet.add(locationBuilder.build());
            providerBuilder.withLocation(locationSet);
            providerBuilder.withTags(tagSet);

            providers.add(providerBuilder.build());
        }
        System.out.println(new Date() + "--- Done Building Data");
        System.out.println(new Date() + "--- Analyzing Index Data");
        try {
            final WriteIndex writeIndex = new WriteIndex(luceneIndexConfig);
            writeIndex.writeBigIndex(providers);
        } catch (final IOException e) {
            e.printStackTrace();
        }

        System.out.println(new Date() + " - Index Completed");
        System.out.println("--------------------");
    }
}
