package service.impl;

import appException.dao.AppSqlException;
import appException.service.AppServiceException;
import dao.interfaces.DaoFactory;
import dao.interfaces.CarDao;
import dao.interfaces.OrderDao;
import dao.interfaces.UserDao;
import entity.car.Car;
import entity.order.Order;
import entity.order.OrderBuilder;
import entity.user.User;
import entity.user.UserBuilder;
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
    public Order createOrder(int phone, String from, String where, String comments) throws AppServiceException {
        logger.debug("start createOrder(int, Str, Str, Str)");

        Order order = null;
        try {
            User user = authenticationUser(phone);
            Car car = searchCarForOrder(from, where, comments);
            order = OrderBuilder.createOrder().setIdUser(user.getId()).setIdCar(car.getId()).setFrom(from).setWhere(where).setComments(comments).getOrder();
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
            orderList = orderDao.getListByIdUser(idUser).orElseThrow(NullPointerException::new);
        } catch (SQLException | NullPointerException ex) {
            logger.info("getTripList(long) catch (SQLException | NullPointerException ex) : orderDao.getListByIdUser(long) throw exception");
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
            user = UserBuilder.createUser().setPhone(phone).getUser();
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
    private Car searchCarForOrder(String from, String where, String comments) throws SQLException {
        logger.debug("start searchCarForOrder(Str, Str, Str)");

        CarDao carDao = daoFactory.getCarDao();
        List<Car> carList = null;
        try {
            carList = carDao.getListCarsByStatus(true).orElseThrow(NullPointerException::new);
        } catch (SQLException | NullPointerException ex){
            logger.info("searchCarForOrder(Sts, Str, Str) catch (SQLException | NullPointerException ex) {} : carDao.getListCarsByStatus(boolean) throw exception");
            throw ex;
        }
        int listSize = carList.size();
        int rand = new Random(System.nanoTime()).nextInt(listSize);

        logger.debug("finish searchCarForOrder(Str, Str, Str)");
        return carList.get(rand);
    }
}
