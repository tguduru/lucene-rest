package org.tguduru.lucene.rest.listener.data;

import com.cerner.message.center.referral.directory.model.Provider;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

/**
 * @author Guduru, Thirupathi Reddy
 * @modified 2/2/16
 */
public class ProviderData {
    public static List<Provider.Builder> getProviders() {
        final List<Provider.Builder> providers = Lists.newArrayList();

        for (int i = 0; i < 20; i++) {
            final Provider.Builder builder = Provider.newBuilder();
            builder.withFirstName("First Name" + i);
            builder.withLastName("Last Name" + i);
            builder.withFullName("Full Name" + i);
            builder.withIdentifier(new Random().nextLong());
            builder.withSpeciality("Speciality" + i);
            providers.add(builder);
        }

        return providers;
    }
}
