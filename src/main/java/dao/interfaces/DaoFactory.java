package dao.interfaces;

public interface DaoFactory {

    UserDao getUserDao();
    UserStatusDao getUserStatusDao();
    OrderDao getOrderDao();
    CarDao getCarDao();
    AddressDao getAddressDao();
    ColorDao getColorDao();
    CarModelDao getCarModelDao();

}
