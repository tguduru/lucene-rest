package org.tguduru.lucene.rest.util;

import org.tguduru.lucene.rest.config.LuceneIndexConfig;

import java.io.*;

/**
 * @author Guduru, Thirupathi Reddy
 * @modified 12/22/15
 */
public class Config {

    public static String getLastIndexDateTime(final LuceneIndexConfig luceneIndexConfig) {
        final File file = new File(luceneIndexConfig.getDirectory() + File.separator + "lastIndexTime.index");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return reader.readLine();
        } catch (final FileNotFoundException e) {
            System.err.println(e);
            return null;
        } catch (final IOException e) {
            System.err.println(e);
            return null;
        }
    }

    public static void writeLastIndexTime(final String lastIndexTime, final LuceneIndexConfig luceneIndexConfig)
            throws IOException {
        final File file = new File(luceneIndexConfig.getDirectory() + File.separator + "lastIndexTime.index");
        if (!file.exists())
            file.createNewFile();
        final FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(lastIndexTime.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
