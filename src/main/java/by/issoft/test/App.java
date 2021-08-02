package by.issoft.test;


import by.issoft.test.bean.Order;
import by.issoft.test.bean.OrderItem;
import by.issoft.test.bean.OrderItemDatePrice;
import by.issoft.test.bean.Product;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.concurrent.*;

public class App {

    private static final Order POISON = new Order("POISON", new Date());
    private static final int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) throws ParseException, FileNotFoundException {
        String ordersPath = "X:\\orders.csv";
        String orderItemsPath = "X:\\order_items.csv";
        String productsPath = "X:\\products.csv";


        List<Order> orders = new CsvToBeanBuilder(new FileReader(ordersPath))
                .withType(Order.class)
                .build()
                .parse();

        List<OrderItem> orderItems = new CsvToBeanBuilder(new FileReader(orderItemsPath))
                .withType(OrderItem.class)
                .build()
                .parse();

        List<Product> products = new CsvToBeanBuilder(new FileReader(productsPath))
                .withType(Product.class)
                .build()
                .parse();


        Set<LocalDate> uniqueDates = new HashSet<>();
        orders.forEach(order -> uniqueDates.add(Util.getLocalDate(order.getDate())));

        ConcurrentMap<LocalDate, BlockingQueue<OrderItemDatePrice>> dayProductItemsMap = new ConcurrentHashMap<>();
        uniqueDates.forEach(localDate -> dayProductItemsMap.put(localDate, new LinkedBlockingQueue<>()));


        BlockingQueue<OrderItemDatePrice> orderItemDatePrices = new LinkedBlockingQueue<>();
        BlockingQueue<Order> orderBlockingQueue = new LinkedBlockingQueue<>(orders);


        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            orderBlockingQueue.add(POISON);
        }

        CountDownLatch countDownLatch = new CountDownLatch(NUMBER_OF_THREADS);
        ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            executor.execute(new OrderItemDatePriceConsumer(orderBlockingQueue, orderItems, products
                    , dayProductItemsMap, countDownLatch, POISON));
        }


        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();



        LocalDate inputDate = LocalDate.of(2021, Month.JANUARY,21);
        ArrayList<LocalDate> localDates = new ArrayList<>(uniqueDates);
        dayProductItemsMap.get(inputDate).forEach(orderItemDatePrice -> System.out.println(orderItemDatePrice));





    }



}
