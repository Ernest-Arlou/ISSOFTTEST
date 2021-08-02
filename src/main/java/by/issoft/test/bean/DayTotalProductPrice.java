package by.issoft.test.bean;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class DayTotalProductPrice {
    private LocalDate date;
    private List<Product> products;

    public DayTotalProductPrice() {

    }

    public DayTotalProductPrice(LocalDate date, List<Product> products) {
        this.date = date;
        this.products = products;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DayTotalProductPrice that = (DayTotalProductPrice) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(products, that.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, products);
    }

    @Override
    public String toString() {
        return "DayTotalProductPrice{" +
                "date=" + date +
                ", products=" + products +
                '}';
    }
}
