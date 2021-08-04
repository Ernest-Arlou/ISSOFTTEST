package by.issoft.test.logic;

public class ServiceHolder {
    private final Service service = new ServiceImpl();

    private static class InstanceHolder {
        public static final ServiceHolder instance = new ServiceHolder();
    }

    private ServiceHolder() {
    }

    public static ServiceHolder getInstance() {
        return ServiceHolder.InstanceHolder.instance;
    }

    public Service getService() {
        return service;
    }

}
