package org.tguduru.lucene.rest.index;

import com.google.common.collect.Lists;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.tguduru.lucene.rest.config.DataSourceProperties;
import org.tguduru.lucene.rest.config.LuceneIndexConfig;
import org.tguduru.lucene.rest.model.Product;
import org.tguduru.lucene.rest.util.Config;

import java.sql.*;
import java.util.List;
import java.util.Properties;

/**
 * @author Guduru, Thirupathi Reddy
 * @modified 12/22/15
 */
public class ProductDAO {

    private final DataSourceProperties dataSourceProperties;
    private final LuceneIndexConfig luceneIndexConfig;

    public ProductDAO(final DataSourceProperties dataSourceProperties, final LuceneIndexConfig luceneIndexConfig) {
        this.dataSourceProperties = dataSourceProperties;
        this.luceneIndexConfig = luceneIndexConfig;
    }

    public List<Product> getProducts() {

        final DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(dataSourceProperties.getDriverClass());
        driverManagerDataSource.setUsername(dataSourceProperties.getUserName());
        driverManagerDataSource.setPassword(dataSourceProperties.getPassword());
        driverManagerDataSource.setUrl(dataSourceProperties.getUrl());
        final Properties properties = new Properties();
        properties.setProperty("dialect", dataSourceProperties.getDialect());
        driverManagerDataSource.setConnectionProperties(properties);

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = driverManagerDataSource.getConnection();
            statement = connection.createStatement();
            final String lastIndexTime = Config.getLastIndexDateTime(luceneIndexConfig);
            if (lastIndexTime == null) {
                resultSet = statement.executeQuery("SELECT * FROM product");
            } else {
                resultSet = statement.executeQuery("SELECT * FROM product WHERE lastUpdateDateTime > '" + lastIndexTime
                        + "'");
            }
            final List<Product> products = Lists.newArrayList();
            while (resultSet.next()) {
                final String name = resultSet.getString("name");
                final String description = resultSet.getString("description");
                final long price = resultSet.getLong("price");
                final Timestamp lastUpdateDateTime = resultSet.getTimestamp("lastUpdateDatetime");
                final Product product = new Product(name, description, price, lastUpdateDateTime);
                System.out.println(product);
                products.add(product);
            }
            return products;
        } catch (final SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (final SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
