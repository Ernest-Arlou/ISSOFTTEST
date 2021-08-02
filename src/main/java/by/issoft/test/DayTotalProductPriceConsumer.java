package by.issoft.test;

import by.issoft.test.bean.DayTotalProductPrice;
import by.issoft.test.bean.OrderItemDatePrice;
import by.issoft.test.bean.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class DayTotalProductPriceConsumer implements Runnable {
    private final BlockingQueue<List<OrderItemDatePrice>> dayQueues;
    private final BlockingQueue<DayTotalProductPrice> dayTotalProductPrices;
    private final List<OrderItemDatePrice> POISON_QUEUE;
    private final CountDownLatch countDownLatch;


    private boolean dateSet = false;

    public DayTotalProductPriceConsumer(BlockingQueue<List<OrderItemDatePrice>> dayQueues, BlockingQueue<DayTotalProductPrice> dayTotalProductPrices
            , List<OrderItemDatePrice> poison_queue, CountDownLatch countDownLatch) {
        this.dayQueues = dayQueues;
        this.dayTotalProductPrices = dayTotalProductPrices;
        POISON_QUEUE = poison_queue;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        while (true) {
            List<OrderItemDatePrice> list = dayQueues.poll();


            if (list == null || list.isEmpty()) {
                dateSet = false;
                continue;
            }

            if (list.equals(POISON_QUEUE)) {
                countDownLatch.countDown();
                break;
            }

            DayTotalProductPrice dayTotalProductPrice = new DayTotalProductPrice();

            Map<String, Product> products = new HashMap<>();
            dayTotalProductPrice.setProducts(products);


            list.forEach(item -> {
                if (products.containsKey(item.getProductId())) {
                    if (!dateSet) {
                        dayTotalProductPrice.setDate(item.getDate());
                        dateSet = true;
                    }
                    Product product = products.get(item.getProductId());
                    products.remove(item.getProductId());
                    product.setPrice(product.getPrice() + item.getTotalPrice());
                    products.put(item.getProductId(), product);
                    if (dayTotalProductPrice.getMaxProfit() == null
                            || dayTotalProductPrice.getMaxProfit().getPrice() < product.getPrice()) {
                        dayTotalProductPrice.setMaxProfit(product);
                    }
                } else {
                    products.put(item.getProductId(), new Product(item.getProductId(), item.getProductName(), item.getTotalPrice()));
                }
            });
            dayTotalProductPrices.add(dayTotalProductPrice);
            dateSet = false;
        }


    }
}
