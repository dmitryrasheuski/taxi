package dao.impl.mysql;

import dao.interfaces.*;
import lombok.extern.log4j.Log4j;

import java.sql.Connection;
import java.sql.SQLException;

@Log4j
public class MysqlDaoFactory implements DaoFactory {
    private static final DataSource connectionPool;
    private static ThreadLocal<Connection> connectionThreadLocal;
    static {
        connectionPool = DataSource.getInstance();

        connectionThreadLocal = new ThreadLocal<Connection>() {
            @Override
            protected Connection initialValue() {

                try {
                    return connectionPool.getConnection();

                } catch (SQLException ex) {
                    log.error("The exception occurred when initializing 'MysqlDaoFactory' class at the moment of receiving connection to database", ex);
                    throw new RuntimeException("The exception occurred when initializing 'MysqlDaoFactory' class at the moment of receiving connection to database", ex);
                }
            }

        };

    }

    private Connection con;
    {
        con = connectionThreadLocal.get();
    }

    Connection getConnection() throws SQLException {
        return con;
    }
    @Override
    public UserDao getUserDao() {
        return new UserDaoImpl(this);
    }
    @Override
    public UserStatusDao getUserStatusDao() {
        return new UserStatusDaoImpl(this);
    }
    @Override
    public OrderDao getOrderDao() {
        return new OrderDaoImpl(this);
    }
    @Override
    public CarDao getCarDao() {
        return new CarDaoImpl(this);
    }
    @Override
    public AddressDao getAddressDao() {
        return new AddressDaoImpl(this);
    }
    @Override
    public ColorDao getColorDao() {
        return new ColorDaoImpl(this);
    }
    @Override
    public CarModelDao getCarModelDao() {
        return new CarModelDaoImpl(this);
    }

}

