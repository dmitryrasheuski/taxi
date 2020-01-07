package service.impl;

import appException.service.AppServiceException;
import dao.interfaces.DaoFactory;
import dao.interfaces.CarDao;
import dao.interfaces.OrderDao;
import dao.interfaces.UserDao;
import entity.car.Car;
import entity.order.Address;
import entity.order.Order;
import entity.user.User;
import org.apache.log4j.Logger;
import service.interfaces.ICreateOrder;
import service.interfaces.IGetTripList;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class OrderService implements IGetTripList, ICreateOrder {
    private static final Logger logger = Logger.getLogger(OrderService.class);
    private DaoFactory daoFactory = DaoFactory.getFactory(DaoFactory.TypesDatabases.MY_SQL);
    private OrderDao orderDao = daoFactory.getOrderDao();

    @Override
    public Order createOrder(int phone, String fromStr, String whereStr, String comment) throws AppServiceException {
        logger.debug("start createOrder(int, Str, Str, Str)");

        Order order = null;
        try {
            User passenger = authenticationUser(phone);
            Car car = searchCarForOrder(fromStr, whereStr, comment);
            Address from = new Address(fromStr);
            Address where = new Address(whereStr);
            order = Order.builder()
                    .passenger(passenger)
                    .car(car)
                    .from(from)
                    .where(where)
                    .comment(comment)
                    .build();
            long idOrder = orderDao.addOrder(order).orElseThrow(NullPointerException::new);
            order.setId(idOrder);
        } catch (SQLException | NullPointerException ex) {
            logger.info("createOrder(int, Str, Str, Str) catch (SQLException | NullPointerException ex) : (orderDao.addOrder(Order) || authenticationUser(int) || searchCarForOrder(St, Str, Str)) throw exception");
            throw new AppServiceException(ex);
        }

        logger.debug("finish createOrder(int, Str, Str, Str)");
        return order;
    }
    @Override
    public List<Order> getTripList(long idUser) throws AppServiceException {
        logger.debug("start getTripList(long)");

        List<Order> orderList = null;
        try {
            orderList = orderDao.getListByPassengerId(idUser).orElseThrow(NullPointerException::new);
        } catch (SQLException | NullPointerException ex) {
            logger.info("getTripList(long) catch (SQLException | NullPointerException ex) : orderDao.getListByPassengerId(long) throw exception");
            throw new AppServiceException(ex);
        }

        logger.debug("finish getTripList(long)");
        return orderList;
    }

    private User authenticationUser(int phone) throws SQLException {
        logger.debug("start authentication(int)");

        UserDao userDao = daoFactory.getUserDao();
        User user = null;
        try {
            user = userDao.getByPhone(phone).orElseThrow(NullPointerException::new);
        } catch (SQLException | NullPointerException ex) {
            logger.info("authentication(int) catch (SQLException | NullPointerException ex) {} : userDao.getByPhone(int) throw exception");
            //database did'n have the user, then the phone add to database
            user = User.builder().phone(phone).build();
            long idUser = 0;
            try {
                idUser = userDao.addUser(user).orElseThrow(NullPointerException::new);
            } catch (SQLException | NullPointerException e){
                logger.info("authentication(int) catch (SQLException | NullPointerException ex) {} : userDao.addUser(User) throw exception");
                e.addSuppressed(ex);
                throw e;
            }

            user.setId(idUser);
        }

        logger.debug("finish authentication(int)");
        return user;
    }
    /**заглушка*/ private Car searchCarForOrder(String from, String where, String comments) throws SQLException {
        return null;
    }
}
