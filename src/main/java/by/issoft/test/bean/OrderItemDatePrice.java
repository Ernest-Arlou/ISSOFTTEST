package by.issoft.test.bean;

import java.time.LocalDate;
import java.util.Objects;

public class OrderItemDatePrice {
    private String orderId;
    private String productId;
    private String productName;
    private double totalPrice;
    private LocalDate date;

    public OrderItemDatePrice() {
    }

    public OrderItemDatePrice(String orderId, String productId, String productName, double totalPrice, LocalDate date) {
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.totalPrice = totalPrice;
        this.date = date;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemDatePrice that = (OrderItemDatePrice) o;
        return Double.compare(that.totalPrice, totalPrice) == 0 &&
                Objects.equals(orderId, that.orderId) &&
                Objects.equals(productId, that.productId) &&
                Objects.equals(productName, that.productName) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId, productName, totalPrice, date);
    }

    @Override
    public String toString() {
        return "OrderItemDatePrice{" +
                "orderId='" + orderId + '\'' +
                ", productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", totalPrice=" + totalPrice +
                ", date=" + date +
                '}';
    }
}
