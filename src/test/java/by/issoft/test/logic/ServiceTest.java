package by.issoft.test.logic;

import by.issoft.test.bean.*;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.concurrent.BlockingQueue;

import static org.junit.Assert.*;

public class ServiceTest {
    private static final Service SERVICE = new Service();
    private static final List<Order> orders = new ArrayList<>();
    private static final List<OrderItem> orderItems = new ArrayList<>();
    private static final List<Product> products = new ArrayList<>();

    private static final double epsilon = 0.000001d;

    private static final Date date1 = new GregorianCalendar(2021, Calendar.JANUARY, 1).getTime();
    private static final Date date2 = new GregorianCalendar(2021, Calendar.JANUARY, 2).getTime();
    private static final Date date3 = new GregorianCalendar(2021, Calendar.JANUARY, 3).getTime();

    private static final String orderId1 = "orderId_1";
    private static final String orderId2 = "orderId_2";
    private static final String orderId3 = "orderId_3";

    private static final String productId1 = "productId_1";
    private static final String productId2 = "productId_2";

    static {
        orders.add(new Order(orderId1, date1));
        orders.add(new Order(orderId2, date2));
        orders.add(new Order(orderId3, date3));

        orderItems.add(new OrderItem(orderId1,productId2, 1 ));
        orderItems.add(new OrderItem(orderId1,productId2, 3 ));
        orderItems.add(new OrderItem(orderId2,productId1, 2 ));
        orderItems.add(new OrderItem(orderId2,productId2, 5 ));
        orderItems.add(new OrderItem(orderId3,productId1, 3 ));
        orderItems.add(new OrderItem(orderId3,productId2, 2 ));

        products.add(new Product(productId1, productId1, 5));
        products.add(new Product(productId2, productId2, 5));
    }


    @Test
    public void getItemsWPricePerDay() {
        BlockingQueue<List<OrderItemDatePrice>> dayQueues = SERVICE.getItemsWPricePerDay(orders,orderItems,products);

        List<List<OrderItemDatePrice>> lists = new ArrayList<>(dayQueues);
        lists.forEach( orderItemDatePrices -> orderItemDatePrices.forEach(item -> {
            if ( !(item.getOrderId().equals(orderId1) && item.getProductId().equals(productId2) && item.getTotalPrice() == 5.0) &&
                    !(item.getOrderId().equals(orderId1) && item.getProductId().equals(productId2) && item.getTotalPrice() == 15.0) &&
                    !(item.getOrderId().equals(orderId2) && item.getProductId().equals(productId1) && item.getTotalPrice() == 10.0) &&
                    !(item.getOrderId().equals(orderId2) && item.getProductId().equals(productId2) && item.getTotalPrice() == 25.0) &&
                    !(item.getOrderId().equals(orderId3) && item.getProductId().equals(productId1) && item.getTotalPrice() == 15.0) &&
                    !(item.getOrderId().equals(orderId3) && item.getProductId().equals(productId2) && item.getTotalPrice() == 10.0)){
               assertTrue(false);
            }
        }));
    }

    @Test
    public void testGetDayTotalProductPrices() {
        BlockingQueue<DayTotalProductPrice> dayTotalProductPrices = SERVICE.getDayTotalProductPrices(orders,orderItems,products);
        ArrayList<DayTotalProductPrice> arrayList = new ArrayList<>(dayTotalProductPrices);

        assertEquals(3,arrayList.size());

        for (DayTotalProductPrice item: arrayList) {
            if (item.getDate().equals(LocalDate.of(2021, Month.JANUARY,1))){
                assertEquals(productId2,item.getMaxProfit().getId());
                assertTrue(Math.abs(20.0 - item.getMaxProfit().getPrice()) < epsilon);
            }
            if (item.getDate().equals(LocalDate.of(2021, Month.JANUARY,2))){
                assertEquals(productId2,item.getMaxProfit().getId());
                assertTrue(Math.abs(25.0 - item.getMaxProfit().getPrice()) < epsilon);
            }
            if (item.getDate().equals(LocalDate.of(2021, Month.JANUARY,3))){
                assertEquals(productId1,item.getMaxProfit().getId());
                assertTrue(Math.abs(15.0 - item.getMaxProfit().getPrice()) < epsilon);
            }
        }
    }
}