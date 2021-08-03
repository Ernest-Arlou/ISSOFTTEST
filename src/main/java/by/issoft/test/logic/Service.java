package by.issoft.test.logic;

import by.issoft.test.Util;
import by.issoft.test.bean.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;

public class Service {
    private static final int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();
    private static final Logger logger = LoggerFactory.getLogger(Service.class);
    private static final Order POISON = new Order("POISON", new Date());
    private static final List<OrderItemDatePrice> POISON_QUEUE = new LinkedList<>();

    static {
        POISON_QUEUE.add(new OrderItemDatePrice());
    }


    private Set<LocalDate> getUniqueDates(List<Order> orders) {
        Set<LocalDate> uniqueDates = new HashSet<>();
        orders.forEach(order -> uniqueDates.add(Util.getLocalDate(order.getDate())));
        return uniqueDates;
    }

    public BlockingQueue<List<OrderItemDatePrice>> getItemsWPricePerDay(List<Order> orders, List<OrderItem> orderItems, List<Product> products) {

        Set<LocalDate> uniqueDates = getUniqueDates(orders);

        ConcurrentMap<LocalDate, BlockingQueue<OrderItemDatePrice>> itemsWPricePerDayMap = new ConcurrentHashMap<>();
        uniqueDates.forEach(localDate -> itemsWPricePerDayMap.put(localDate, new LinkedBlockingQueue<>()));


        BlockingQueue<Order> ordersBlockingQueue = new LinkedBlockingQueue<>(orders);

        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            ordersBlockingQueue.add(POISON);
        }

        CountDownLatch itemsWPricePerDayCountDownLatch = new CountDownLatch(NUMBER_OF_THREADS);
        ExecutorService itemsWPricePerDayExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            itemsWPricePerDayExecutor.execute(new ItemsWPricePerDayConsumer(ordersBlockingQueue, orderItems, products
                    , itemsWPricePerDayMap, itemsWPricePerDayCountDownLatch, POISON));
        }

        try {
            itemsWPricePerDayCountDownLatch.await();
        } catch (InterruptedException e) {
            logger.error(e.toString());
        }
        itemsWPricePerDayExecutor.shutdown();

        BlockingQueue<List<OrderItemDatePrice>> dayQueues = new LinkedBlockingQueue<>();
        itemsWPricePerDayMap.forEach((k, v) -> dayQueues.add(new ArrayList<>(v)));
        return dayQueues;
    }

    public BlockingQueue<DayTotalProductPrice> getDayTotalProductPrices(BlockingQueue<List<OrderItemDatePrice>> itemsWPricePerDay) {
        BlockingQueue<DayTotalProductPrice> dayTotalProductPrices = new LinkedBlockingQueue<>();

        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            itemsWPricePerDay.add(POISON_QUEUE);
        }


        CountDownLatch dayTotalProductPricesCountDownLatch = new CountDownLatch(NUMBER_OF_THREADS);
        ExecutorService dayTotalProductPricesExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            dayTotalProductPricesExecutor.execute(new DayTotalProductPriceConsumer(itemsWPricePerDay, dayTotalProductPrices
                    , POISON_QUEUE, dayTotalProductPricesCountDownLatch));
        }

        try {
            dayTotalProductPricesCountDownLatch.await();
        } catch (InterruptedException e) {
            logger.error(e.toString());
        }
        dayTotalProductPricesExecutor.shutdown();

        return dayTotalProductPrices;
    }

    public BlockingQueue<DayTotalProductPrice> getDayTotalProductPrices(List<Order> orders, List<OrderItem> orderItems, List<Product> products) {
        return getDayTotalProductPrices(getItemsWPricePerDay(orders, orderItems, products));
    }


}
