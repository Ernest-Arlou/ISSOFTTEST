package by.issoft.test.logic;

import by.issoft.test.bean.DayTotalProductPrice;

import java.util.concurrent.BlockingQueue;

public interface Service {
    BlockingQueue<DayTotalProductPrice> getDayTotalProductPrices(String ordersPath, String orderItemsPath, String productsPath);
}
