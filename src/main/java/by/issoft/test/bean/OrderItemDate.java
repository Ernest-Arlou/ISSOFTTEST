package by.issoft.test.bean;

import java.time.LocalDate;

public class OrderItemDate {
    LocalDate date;
    String productId;
    int quantity;

    public OrderItemDate() {
    }

    public OrderItemDate(LocalDate date, String productId, int quantity) {
        this.date = date;
        this.productId = productId;
        this.quantity = quantity;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderItemDate{" +
                "date=" + date +
                ", productId='" + productId + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
