package org.tguduru.lucene.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tguduru.lucene.rest.config.CommandLineConfig;
import org.tguduru.lucene.rest.config.ConfigParameters;

/**
 * @author Guduru, Thirupathi Reddy
 * @modified 12/21/15
 */
@SpringBootApplication
public class LuceneRestApplication {

    public static void main(final String[] args) {
        if (args.length == 0) {
            System.out.println("Command Usage ...");
            System.out
                    .println("java -jar lucene-rest-0.0.1-SNAPSHOT.jar <index-location> <db-url> <db-username> <db-password>");
            System.exit(-1);
        }
        CommandLineConfig.addConfig(ConfigParameters.indexLocation, args[0]);
        CommandLineConfig.addConfig(ConfigParameters.dbUrl, args[1]);
        CommandLineConfig.addConfig(ConfigParameters.dbUserName, args[2]);
        CommandLineConfig.addConfig(ConfigParameters.dbPassword, args[3]);
        CommandLineConfig.addConfig(ConfigParameters.rows,args[4]);
        SpringApplication.run(LuceneRestApplication.class, args);
    }
}
