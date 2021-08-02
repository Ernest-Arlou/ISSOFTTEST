package by.issoft.test;

import by.issoft.test.bean.OrderItemDatePrice;

import java.time.LocalDate;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;

public class DayTotalProductPriceConsumer implements Runnable{
    private final ConcurrentMap<LocalDate, BlockingQueue<OrderItemDatePrice>> dayProductItemsMap;

    public DayTotalProductPriceConsumer(ConcurrentMap<LocalDate, BlockingQueue<OrderItemDatePrice>> dayProductItemsMap) {
        this.dayProductItemsMap = dayProductItemsMap;
    }

    @Override
    public void run() {

    }
}
