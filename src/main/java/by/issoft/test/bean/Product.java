package by.issoft.test.bean;

import com.opencsv.bean.CsvBindByName;

public class Product {
    @CsvBindByName(column = "ID")
    private String id;
    @CsvBindByName(column = "NAME")
    private String name;
    @CsvBindByName(column = "PRICE_PER_UNIT")
    private double price;

    public Product() {
    }

    public Product(String id, String name, double pricePerUnit) {
        this.id = id;
        this.name = name;
        this.price = pricePerUnit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pricePerUnit=" + price +
                '}';
    }
}
