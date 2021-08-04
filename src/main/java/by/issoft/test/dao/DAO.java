package by.issoft.test.dao;

import by.issoft.test.bean.Order;
import by.issoft.test.bean.OrderItem;
import by.issoft.test.bean.Product;

import java.util.List;

public interface DAO {
    List<Order> getOrders(String path);

    List<OrderItem> getOrderItems(String path);

    List<Product> getProducts(String path);
}
