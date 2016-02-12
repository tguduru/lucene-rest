package org.tguduru.lucene.rest.listener.data;

import com.cerner.message.center.referral.directory.model.Address;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * @author Guduru, Thirupathi Reddy
 * @modified 2/2/16
 */
public class AddressData {
    public static List<Address> getAddresses() {
        final List<Address> addresses = Lists.newArrayList();
        for (int i = 0; i < 20; i++) {
            Address.Builder builder = Address.newBuilder();
            if (i % 6 == 0) {
                builder.withCity("Overland Park");
                builder.withState("Kansas");
                builder.withZip("11111");
            } else if (i % 5 == 0) {
                builder.withCity("Kansas City");
                builder.withState("Missouri");
                builder.withZip("22222");
            } else if (i % 4 == 0) {
                builder.withCity("Leawood");
                builder.withState("Kansas");
                builder.withZip("33333");
            } else if (i % 3 == 0) {
                builder.withCity("Shawnee Mission");
                builder.withState("Kansas");
                builder.withZip("44444");
            } else {
                builder.withCity("Roeland Park");
                builder.withState("Kansas");
                builder.withZip("55555");
            }

            builder.withStreetAddress1("Street Address" + i);
            builder.withStreetAddress2("Street Address" + i);
            builder.withStreetAddress3("Street Address" + i);
            builder.withStreetAddress4("Street Address" + i);

            addresses.add(builder.build());
        }
        return addresses;
    }
}
