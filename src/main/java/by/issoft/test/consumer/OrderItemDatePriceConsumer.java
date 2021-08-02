package by.issoft.test.consumer;

import by.issoft.test.Util;
import by.issoft.test.bean.Order;
import by.issoft.test.bean.OrderItem;
import by.issoft.test.bean.OrderItemDatePrice;
import by.issoft.test.bean.Product;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;

public class OrderItemDatePriceConsumer implements Runnable {
    private final BlockingQueue<Order> orders;
    private final List<OrderItem> orderItems;
    private final List<Product> products;
    private final ConcurrentMap<LocalDate, BlockingQueue<OrderItemDatePrice>> dayProductItemsMap;
    private final CountDownLatch countDownLatch;
    private final Order POISON;

    public OrderItemDatePriceConsumer(BlockingQueue<Order> orders, List<OrderItem> orderItems, List<Product> products
            , ConcurrentMap<LocalDate, BlockingQueue<OrderItemDatePrice>> dayProductItemsMap, CountDownLatch countDownLatch, Order poison) {

        this.countDownLatch = countDownLatch;
        this.orderItems = orderItems;
        this.orders = orders;
        this.products = products;
        this.dayProductItemsMap = dayProductItemsMap;
        POISON = poison;
    }

    @Override
    public void run() {
        while (true) {

            Order order = orders.poll();

            if (order.equals(POISON)) {
                countDownLatch.countDown();
                break;
            }

            orderItems.forEach(orderItem -> {

                if (order.getId().equals(orderItem.getOrderId())) {
                    products.forEach(product -> {

                        if (orderItem.getProductId().equals(product.getId())) {
                            dayProductItemsMap.get(Util.getLocalDate(order.getDate()))
                                    .add(new OrderItemDatePrice(
                                            order.getId(),
                                            product.getId(),
                                            product.getName(),
                                            product.getPrice() * orderItem.getQuantity(),
                                            Util.getLocalDate(order.getDate())));
                        }
                    });
                }
            });
        }
    }
}
