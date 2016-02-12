package org.tguduru.lucene.rest.model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Model object for the Product
 * @author Guduru, Thirupathi Reddy
 * @modified 12/21/15
 */
public class Product {
    private String name;
    private String description;
    private double price;
    private Timestamp lastUpdateDateTime;

    public Product(String name, String description, double price, Timestamp lastUpdateDateTime) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.lastUpdateDateTime = lastUpdateDateTime;
    }

    public Product() {
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public Timestamp getLastUpdateDateTime() {
        return lastUpdateDateTime;
    }

    @Override
    public String toString() {
        return "Product{" + "name='" + name + '\'' + ", description='" + description + '\'' + ", price=" + price
                + ", lastUpdateDateTime=" + lastUpdateDateTime + '}';
    }
}
