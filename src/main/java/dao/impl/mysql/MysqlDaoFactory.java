package dao.impl.mysql;

import dao.interfaces.*;
import org.apache.log4j.Logger;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class MysqlDaoFactory implements DaoFactory {
    private static final Logger logger;
    private static final ConnectionPool connectionPool;
    static {
        logger = Logger.getLogger(MysqlDaoFactory.class);
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (PropertyVetoException ex) {
            logger.error("init ConnectionPool exception", ex);
            throw new RuntimeException("init ConnectionPool exception", ex);
        }
    }

    Connection getConnection() throws SQLException {
        return connectionPool.getConnection();
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
    public void closeDatasource(){
        connectionPool.closeDatasource();
    }

}

