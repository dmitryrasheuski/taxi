package dao.interfaces;

import dao.impl.mysql.MysqlDaoFactory;

public interface DaoFactory {

    UserDao getUserDao();
    OrderDao getOrderDao();
    CarDao getCarDao();
    void closeDatasource();


    enum TypesDatabases {

        MY_SQL(new MysqlDaoFactory());

        private DaoFactory factory;

        TypesDatabases(DaoFactory factory) {
            this.factory = factory;
        }

    }
    // при данной реализации  конкретная фабрика создается в единственном экземпляре
    static DaoFactory getFactory(TypesDatabases type) {
        return type.factory;
    }
}
