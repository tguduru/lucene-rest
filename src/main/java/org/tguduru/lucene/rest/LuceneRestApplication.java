package org.tguduru.lucene.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * @author Guduru, Thirupathi Reddy
 * @modified 12/21/15
 */
@SpringBootApplication
public class LuceneRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(LuceneRestApplication.class, args);
    }
}
