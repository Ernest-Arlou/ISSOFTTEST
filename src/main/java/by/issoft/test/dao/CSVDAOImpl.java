package by.issoft.test.dao;

import by.issoft.test.bean.Order;
import by.issoft.test.bean.OrderItem;
import by.issoft.test.bean.Product;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CSVDAOImpl implements DAO {
    private static final Logger logger = LoggerFactory.getLogger(CSVDAOImpl.class);

    @Override
    public List<Order> getOrders(String path) {
        List<Order> orders = null;
        FileReader ordersFileReader = null;
        try {
            ordersFileReader = new FileReader(path);
            orders = new CsvToBeanBuilder(ordersFileReader)
                    .withType(Order.class)
                    .build()
                    .parse();
        } catch (FileNotFoundException e) {
            logger.error(e.toString());
        } finally {
            try {
                if (ordersFileReader != null)
                    ordersFileReader.close();
            } catch (IOException e) {
                logger.error(e.toString());
            }
        }
        return orders;
    }

    @Override
    public List<OrderItem> getOrderItems(String path) {
        List<OrderItem> orderItems = null;
        FileReader orderItemsFileReader = null;
        try {
            orderItemsFileReader = new FileReader(path);
            orderItems = new CsvToBeanBuilder(orderItemsFileReader)
                    .withType(OrderItem.class)
                    .build()
                    .parse();
        } catch (FileNotFoundException e) {
            logger.error(e.toString());
        } finally {
            try {
                if (orderItemsFileReader != null)
                    orderItemsFileReader.close();
            } catch (IOException e) {
                logger.error(e.toString());
            }
        }
        return orderItems;
    }

    @Override
    public List<Product> getProducts(String path) {
        List<Product> products = null;
        FileReader productsFileReader = null;
        try {
            productsFileReader = new FileReader(path);
            products = new CsvToBeanBuilder(productsFileReader)
                    .withType(Product.class)
                    .build()
                    .parse();
        } catch (FileNotFoundException e) {
            logger.error(e.toString());
        } finally {
            try {
                if (productsFileReader != null)
                    productsFileReader.close();
            } catch (IOException e) {
                logger.error(e.toString());
            }
        }
        return products;
    }
}
