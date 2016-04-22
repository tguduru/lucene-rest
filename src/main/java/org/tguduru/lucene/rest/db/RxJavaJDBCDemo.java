package org.tguduru.lucene.rest.db;

import org.tguduru.lucene.rest.config.CommandLineConfig;
import org.tguduru.lucene.rest.config.ConfigParameters;

import rx.functions.Action1;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.github.davidmoten.rx.jdbc.Database;

/**
 * @author Guduru, Thirupathi Reddy
 * @modified 2/16/16
 */
public class RxJavaJDBCDemo {
    public static void main(final String[] args) throws SQLException {
        CommandLineConfig.addConfig(ConfigParameters.dbUrl, args[0]);
        CommandLineConfig.addConfig(ConfigParameters.dbUserName, args[1]);
        CommandLineConfig.addConfig(ConfigParameters.dbPassword, args[2]);
        CommandLineConfig.addConfig(ConfigParameters.rows, args[3]);
        final Connection connection = new DBConnection().getConnection();
        final Database database = Database.from(connection);
        database.select("select name_first from person where person_id < ?").parameter(args[3])
                .getAs(String.class).toList().forEach(new Action1<List<String>>() {
                    @Override
                    public void call(final List<String> strings) {
                        System.out.println(strings);
                    }
                });
        database.close();
    }
}
