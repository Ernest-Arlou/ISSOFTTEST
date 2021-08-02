package by.issoft.test;


import by.issoft.test.bean.*;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;


public class App {

    private static final Order POISON = new Order("POISON", new Date());
    private static final List<OrderItemDatePrice> POISON_QUEUE = new LinkedList<>();
    private static final int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();

    static {
        POISON_QUEUE.add(new OrderItemDatePrice());
    }

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


        BlockingQueue<Order> orderBlockingQueue = new LinkedBlockingQueue<>(orders);

        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            orderBlockingQueue.add(POISON);
        }

        CountDownLatch orderItemDatePriceCountDownLatch = new CountDownLatch(NUMBER_OF_THREADS);
        ExecutorService orderItemDatePriceExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            orderItemDatePriceExecutor.execute(new OrderItemDatePriceConsumer(orderBlockingQueue, orderItems, products
                    , dayProductItemsMap, orderItemDatePriceCountDownLatch, POISON));
        }


        try {
            orderItemDatePriceCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        orderItemDatePriceExecutor.shutdown();


        BlockingQueue<List<OrderItemDatePrice>> dayQueues = new LinkedBlockingQueue<>();
        dayProductItemsMap.forEach((k, v) -> dayQueues.add(new ArrayList<>(v)));

        BlockingQueue<DayTotalProductPrice> dayTotalProductPrices = new LinkedBlockingQueue<>();


        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            dayQueues.add(POISON_QUEUE);
        }


        CountDownLatch dayTotalProductPricesCountDownLatch = new CountDownLatch(NUMBER_OF_THREADS);
        ExecutorService dayTotalProductPricesExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            dayTotalProductPricesExecutor.execute(new DayTotalProductPriceConsumer(dayQueues, dayTotalProductPrices
                    , POISON_QUEUE, dayTotalProductPricesCountDownLatch));
        }

        try {
            dayTotalProductPricesCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dayTotalProductPricesExecutor.shutdown();


        dayTotalProductPrices.forEach(dayTotalProductPrice -> System.out.println(dayTotalProductPrice));

//        LocalDate date = LocalDate.of(2021, Month.JANUARY, 21);
//        dayTotalProductPrices.stream().filter(item -> item.getDate().equals(date)).forEach( item -> System.out.println(item));


    }
}
