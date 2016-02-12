package org.tguduru.lucene.rest.listener.data;

import com.cerner.message.center.referral.directory.model.Location;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * @author Guduru, Thirupathi Reddy
 * @modified 2/2/16
 */
public class LocationData {

    public static List<Location.Builder> getLocations() {
        final List<Location.Builder> builders = Lists.newArrayList();
        for (int i = 0; i < 20; i++) {
            final Location.Builder builder = Location.newBuilder();
            if (i % 2 == 0)
                builder.withName("Cerner Clinic");
            else if (i % 3 == 0)
                builder.withName("Continuous Clinic");
            else
                builder.withName("WHQ Clinic");
            builders.add(builder);
        }
        return builders;
    }
}
