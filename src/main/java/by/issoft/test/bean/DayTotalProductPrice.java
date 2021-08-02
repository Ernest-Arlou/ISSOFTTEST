package by.issoft.test.bean;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

public class DayTotalProductPrice {
    private LocalDate date;
    private Map<String, Product> products;
    private Product maxProfit;

    public DayTotalProductPrice() {
    }

    public DayTotalProductPrice(LocalDate date, Map<String, Product> products) {
        this.date = date;
        this.products = products;
    }

    public DayTotalProductPrice(LocalDate date, Map<String, Product> products, Product maxProfit) {
        this.date = date;
        this.products = products;
        this.maxProfit = maxProfit;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Map<String, Product> getProducts() {
        return products;
    }

    public void setProducts(Map<String, Product> products) {
        this.products = products;
    }

    public Product getMaxProfit() {
        return maxProfit;
    }

    public void setMaxProfit(Product maxProfit) {
        this.maxProfit = maxProfit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DayTotalProductPrice that = (DayTotalProductPrice) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(products, that.products) &&
                Objects.equals(maxProfit, that.maxProfit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, products, maxProfit);
    }

    @Override
    public String toString() {
        return "DayTotalProductPrice{" +
                "date=" + date +
                ", maxProfit=" + maxProfit +
//                ", products=" + products +
                '}';
    }
}
