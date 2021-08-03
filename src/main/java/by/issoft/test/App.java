package by.issoft.test;


import by.issoft.test.bean.DayTotalProductPrice;
import by.issoft.test.bean.Order;
import by.issoft.test.bean.OrderItem;
import by.issoft.test.bean.Product;
import by.issoft.test.dao.DAO;
import by.issoft.test.logic.Service;

import java.util.List;
import java.util.concurrent.BlockingQueue;


public class App {
    private static final String ORDERS_PATH = "X:\\orders.csv";
    private static final String ORDER_ITEMS_PATH = "X:\\order_items.csv";
    private static final String PRODUCTS_PATH = "X:\\products.csv";

    public static void main(String[] args) {
        DAO dao = new DAO();

        List<Order> orders = dao.getOrders(ORDERS_PATH);
        List<OrderItem> orderItems = dao.getOrderItems(ORDER_ITEMS_PATH);
        List<Product> products = dao.getProducts(PRODUCTS_PATH);

        if (orders == null || orderItems == null || products == null)
            return;

        Service service = new Service();

        BlockingQueue<DayTotalProductPrice> dayTotalProductPrices = service.getDayTotalProductPrices(orders, orderItems, products);
        dayTotalProductPrices.forEach(x -> System.out.println(x.getDate() + " " + x.getMaxProfit().getName() + " " + x.getMaxProfit().getPrice()));

    }
}
