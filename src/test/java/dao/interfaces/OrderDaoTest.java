package dao.interfaces;

import dao.impl.mysql.MysqlDaoFactory;
import entity.car.Car;
import entity.order.Address;
import entity.order.Order;
import entity.user.User;
import entity.user.UserStatusType;
import org.junit.*;

import java.sql.SQLException;
import java.util.List;

public class OrderDaoTest {
    private DaoFactory daoFactory;
    private OrderDao orderDao;
    private UserDao userDao;
    private CarDao carDao;
    private AddressDao addressDao;

    private User passenger;
    private User driver;
    private Car car;
    private Address from;
    private Address where;
    private Order order;

    @Before
    public void setUpMethod() throws SQLException {
        daoFactory = new MysqlDaoFactory();
        orderDao = daoFactory.getOrderDao();
        userDao = daoFactory.getUserDao();
        carDao = daoFactory.getCarDao();
        addressDao = daoFactory.getAddressDao();

        passenger = UserDaoTest.generateAndAddToDbUniqueUser(UserStatusType.PASSENGER, userDao);
        driver = UserDaoTest.generateAndAddToDbUniqueUser(UserStatusType.DRIVER, userDao);
        car = CarDaoTest.produceCar(driver, carDao);
        from = addressDao.getAddress("from").orElseThrow(NullPointerException::new);
        where = addressDao.getAddress("where").orElseThrow(NullPointerException::new);
        order = createNewOrder(passenger, car, from, where, "COMMENT", orderDao);

    }
    @After
    public void tearDownMethod() throws SQLException {
        orderDao.deleteOrder(order.getId()).orElseThrow(NullPointerException::new);
        carDao.deleteCar(car.getId());
        userDao.deleteUser(driver.getId());
        userDao.deleteUser(passenger.getId());
    }

    @Test
    public void getListOrderByUser() throws SQLException {
        Order order_2 = createNewOrder(passenger, car, where, from, "comment", orderDao);

        List<Order> list = orderDao.getListByPassengerId(passenger.getId()).orElseThrow(NullPointerException::new);

        orderDao.deleteOrder( order_2.getId() );

        Assert.assertTrue(list.contains(order));
        Assert.assertTrue(list.contains(order_2));

    }

    static Order createNewOrder(User passenger, Car car, Address from, Address where, String comment, OrderDao orderDao) throws SQLException {
        Order order = Order.builder()
                .passenger(passenger)
                .car(car)
                .from(from)
                .where(where)
                .comment(comment)
                .build();

        long id2 = orderDao.addOrder(order).orElseThrow(NullPointerException::new);
        order.setId(id2);

        return order;

    }
}
