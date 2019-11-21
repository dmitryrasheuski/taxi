package dao.impl.mysql;

import dao.interfaces.DaoFactory;
import dao.interfaces.CarDao;
import dao.interfaces.OrderDao;
import dao.interfaces.UserDao;
import org.apache.log4j.Logger;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class MysqlDaoFactory implements DaoFactory {
    private static final Logger logger = Logger.getLogger(MysqlDaoFactory.class);
    private ConnectionPool connectionPool;

    public MysqlDaoFactory() throws RuntimeException {
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
    void closeConnection(Connection con){
        if(con != null){
            try {
                con.close();
            } catch (SQLException ex){
                logger.error("close connection exception", ex);
            }
        }
    }

    @Override
    public UserDao getUserDao() {
        return new UserDaoImpl(this);
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
    public void closeDatasource(){
        connectionPool.closeDatasource();
    }

}

