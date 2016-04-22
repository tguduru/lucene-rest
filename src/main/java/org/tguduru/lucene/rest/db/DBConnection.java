package org.tguduru.lucene.rest.db;

import org.apache.commons.dbcp2.BasicDataSource;
import org.tguduru.lucene.rest.config.CommandLineConfig;
import org.tguduru.lucene.rest.config.ConfigParameters;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

/**
 * @author Guduru, Thirupathi Reddy
 * @modified 2/12/16
 */
public class DBConnection {
    public Connection getConnection() throws SQLException {
        System.out.println("Starting to connect to DB... - " + new Date());
        final BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        dataSource.setUrl(CommandLineConfig.getConfig(ConfigParameters.dbUrl));
        dataSource.setUsername(CommandLineConfig.getConfig(ConfigParameters.dbUserName));
        dataSource.setPassword(CommandLineConfig.getConfig(ConfigParameters.dbPassword));
        final Connection connection = dataSource.getConnection();
        System.out.println("Connected ... ");
        return connection;
    }
}
