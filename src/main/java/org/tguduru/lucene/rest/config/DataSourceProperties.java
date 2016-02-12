package org.tguduru.lucene.rest.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Provides data source details to connect to MySQL.
 * @author Guduru, Thirupathi Reddy
 * @modified 12/21/15
 */
@Component
@ConfigurationProperties(prefix = "db")
public class DataSourceProperties {
    private String driverClass;
    private String url;
    private String userName;
    private String password;
    private String dialect;

    public String getDriverClass() {
        return driverClass;
    }

    public String getUrl() {
        return url;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getDialect() {
        return dialect;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

    @Override
    public String toString() {
        return "DataSourceProperties{" + "driverClass='" + driverClass + '\'' + ", url='" + url + '\'' + ", userName='"
                + userName + '\'' + ", password='" + password + '\'' + ", dialect='" + dialect + '\'' + '}';
    }

    public DataSourceProperties() {
    }

    public DataSourceProperties(String driverClass, String url, String userName, String password, String dialect) {
        this.driverClass = driverClass;
        this.url = url;
        this.userName = userName;
        this.password = password;
        this.dialect = dialect;
    }
}
