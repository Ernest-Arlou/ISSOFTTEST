package by.issoft.test;


import by.issoft.test.bean.DayTotalProductPrice;
import by.issoft.test.logic.Service;
import by.issoft.test.logic.ServiceHolder;

import java.util.concurrent.BlockingQueue;


public class App {
    private static final String ORDERS_PATH = "X:\\orders.csv";
    private static final String ORDER_ITEMS_PATH = "X:\\order_items.csv";
    private static final String PRODUCTS_PATH = "X:\\products.csv";

    public static void main(String[] args) {

        Service service = ServiceHolder.getInstance().getService();

        BlockingQueue<DayTotalProductPrice> dayTotalProductPrices = service.getDayTotalProductPrices(ORDERS_PATH, ORDER_ITEMS_PATH, PRODUCTS_PATH);
        dayTotalProductPrices.forEach(x -> System.out.println(x.getDate() + " " + x.getMaxProfit().getName() + " " + x.getMaxProfit().getPrice()));

    }
}
