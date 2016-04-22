package org.tguduru.lucene.rest.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.tguduru.lucene.rest.config.*;
import org.tguduru.lucene.rest.db.DBConnection;
import org.tguduru.lucene.rest.index.WriteIndex;

import java.io.IOException;
import java.sql.*;
import java.util.Date;
import java.util.List;
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

        final List<Objects> objects = Lists.newArrayList();
        // build 300K objects
        try {
            final Connection connection = new DBConnection().getConnection();
            final Statement statement = connection.createStatement();
            final ResultSet countResult = statement.executeQuery(some sql);
            long rows = 0;
            while (countResult.next()) {
                rows = countResult.getLong(1);
            }
            System.out.println("Total Rows : " + rows);
            countResult.close();

            final String sql = "some another sql";
            // read data for each 10000 bucket
            final PreparedStatement preparedStatement = connection.prepareStatement(sql);
            final ResultSet resultSet = preparedStatement.executeQuery();
            long readRows = 1;
            long requiredRowCount = Long.parseLong(CommandLineConfig.getConfig(ConfigParameters.rows));
            while (resultSet.next()) {
                // create result objects and them to objects collection
                readRows++;

                if(readRows > requiredRowCount)
                    break;
            }
            resultSet.close();
            System.out.println("Read Rows : " + readRows);
        } catch (final SQLException e) {
            System.out.println(e);
            System.exit(-1);
        }
        System.out.println(new Date() + "--- Done Building Data");
        System.out.println(new Date() + "--- Analyzing Index Data");
        try {
            final WriteIndex writeIndex = new WriteIndex(luceneIndexConfig);
            writeIndex.writeBigIndex(objects);
        } catch (final IOException e) {
            e.printStackTrace();
        }

        System.out.println(new Date() + " - Index Completed");
        System.out.println("--------------------");
    }
}
