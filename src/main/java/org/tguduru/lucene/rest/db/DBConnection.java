package org.tguduru.lucene.rest.db;

import org.apache.commons.dbcp2.BasicDataSource;

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
        dataSource.setUrl("jdbc:oracle:thin:v500/v500@ipldb02.northamerica.cerner.net:1521:prov1");
        dataSource.setUsername("v500");
        dataSource.setPassword("v500");
        final Connection connection = dataSource.getConnection();
        System.out.println("Connected ... ");
        return connection;
    }
}
