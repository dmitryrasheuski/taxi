package dao.interfaces;

import dao.impl.mysql.MysqlDaoFactory;

public interface DaoFactory {

    UserDao getUserDao();
    UserStatusDao getUserStatusDao();
    OrderDao getOrderDao();
    CarDao getCarDao();
    AddressDao getAddressDao();
    ColorDao getColorDao();
    CarModelDao getCarModelDao();
    void closeDatasource();

    static DaoFactory getFactory(TypesDatabases type) {
        return type.factory;
    }

    enum TypesDatabases {
        MY_SQL(new MysqlDaoFactory());

        private DaoFactory factory;

        TypesDatabases(DaoFactory factory) {
            this.factory = factory;
        }

    }
}
