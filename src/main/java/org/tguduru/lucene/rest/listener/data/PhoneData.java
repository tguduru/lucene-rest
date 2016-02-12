package org.tguduru.lucene.rest.listener.data;

import com.cerner.message.center.referral.directory.model.Phone;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * @author Guduru, Thirupathi Reddy
 * @modified 2/2/16
 */
public class PhoneData {
    public static List<Phone> getPhones() {
        final List<Phone> phones = Lists.newArrayList();
        for (int i = 0; i < 20; i++) {
            final Phone.Builder builder = Phone.newBuilder();
            builder.withPhoneNumber("423567456" + i);
            if (i % 2 == 0)
                builder.withType("Mobile Number");
            else if (i % 3 == 0)
                builder.withType("Home Number");
            else if (i % 7 == 0)
                builder.withType("Fax");
            else
                builder.withType("Other");
            phones.add(builder.build());
        }
        return phones;
    }
}
