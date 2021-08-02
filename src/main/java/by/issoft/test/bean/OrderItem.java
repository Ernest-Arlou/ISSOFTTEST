package by.issoft.test.bean;

import com.opencsv.bean.CsvBindByName;

public class OrderItem {
    @CsvBindByName(column = "ORDER_ID")
    private String orderId;
    @CsvBindByName(column = "PRODUCT_ID")
    private String productId;
    @CsvBindByName(column = "QUANTITY")
    private int quantity;

    public OrderItem() {
    }

    public OrderItem(String orderId, String productId, int quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "orderId='" + orderId + '\'' +
                ", productId='" + productId + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
