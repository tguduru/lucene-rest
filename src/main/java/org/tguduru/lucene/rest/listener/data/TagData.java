package org.tguduru.lucene.rest.listener.data;

import com.cerner.message.center.referral.directory.model.Tag;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * @author Guduru, Thirupathi Reddy
 * @modified 2/2/16
 */
public class TagData {

    public static List<Tag> getTags() {
        List<Tag> tags = Lists.newArrayList();

        for (int i = 0; i < 20; i++) {
            Tag.Builder builder = Tag.newBuilder();
            builder.withTagType("Tag Type" + i);
            builder.withValue("Tag Value" + i);
            tags.add(builder.build());
        }

        return tags;
    }
}
