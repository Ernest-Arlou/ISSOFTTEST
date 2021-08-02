package by.issoft.test;

import by.issoft.test.bean.Order;

public class Producer implements Runnable {
    private final Order POISON;

    public Producer(Order poison) {
        POISON = poison;
    }


    @Override
    public void run() {

    }
}
