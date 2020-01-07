package dao.impl.mysql;

import dao.interfaces.*;
import entity.car.Car;
import entity.car.CarModel;
import entity.car.Color;
import entity.order.Address;
import entity.order.Order;
import entity.user.User;
import entity.user.UserStatus;
import entity.user.UserStatusType;
import org.junit.*;

import java.sql.SQLException;
import java.util.List;

public class OrderDaoTest {

    private static DaoFactory daoFactory;
    private static OrderDao orderDao;
    private static UserDao userDao;
    private static CarDao carDao;
    private static AddressDao addressDao;

    private static Address from;
    private static Address where;
    private static String comment = "comment";

    private static User passenger;
    private static User driver;
    private static Car car;
    private Order order;

    @BeforeClass
    public static void setUp() throws Exception {
        daoFactory = DaoFactory.getFactory(DaoFactory.TypesDatabases.MY_SQL);
        orderDao = daoFactory.getOrderDao();
        userDao = daoFactory.getUserDao();
        carDao = daoFactory.getCarDao();
        addressDao = daoFactory.getAddressDao();

        passenger = createTestPassenger();
        driver = createTestDriver();
        car = createTestCar(driver);
        from = addressDao.getAddress("from").orElseThrow(NullPointerException::new);
        where = addressDao.getAddress("where").orElseThrow(NullPointerException::new);
    }
    @AfterClass
    public static void tearDown() throws Exception {
        carDao.deleteCar(car.getId());
        userDao.deleteUser(driver.getId());
        userDao.deleteUser(passenger.getId());
        daoFactory.closeDatasource();
    }

    @Before
    public void setUpMethod() throws SQLException {
        order = Order.builder()
                .passenger(passenger)
                .car(car)
                .from(from)
                .where(where)
                .comment(comment)
                .build();
        long id = orderDao.addOrder(order).orElseThrow(NullPointerException::new);
        order.setId(id);
    }
    @After
    public void tearDownMethod() throws SQLException {
        orderDao.deleteOrder(order.getId()).orElseThrow(NullPointerException::new);
    }

    @Test
    public void getListOrderByUser() throws SQLException {
        Order order_2 = Order.builder()
                .passenger(passenger)
                .car(car)
                .from(from)
                .where(where)
                .comment(comment)
                .build();
        long id2 = orderDao.addOrder(order_2).orElseThrow(NullPointerException::new);
        order_2.setId(id2);

        List<Order> list = orderDao.getListByPassengerId(passenger.getId()).orElseThrow(NullPointerException::new);
        orderDao.deleteOrder(id2);
        Assert.assertTrue(list.contains(order));
        Assert.assertTrue(list.contains(order_2));
    }

    private static User createTestDriver() throws SQLException {
        User d = User.builder()
                .phone(101101)
                .name("driver")
                .surname("order")
                .password("test")
                .status(UserStatus.getInstance(UserStatusType.DRIVER))
                .build();
        long id = userDao.addUser(d).orElseThrow(NullPointerException::new);
        d.setId(id);
        return d;
    }
    private static User createTestPassenger() throws SQLException {
        User p = User.builder()
                .phone(102102)
                .name("passenger")
                .surname("order")
                .password("test")
                .status(UserStatus.getInstance(UserStatusType.PASSENGER))
                .build();
        long id = userDao.addUser(p).orElseThrow(NullPointerException::new);
        p.setId(id);
        return p;
    }
    private static Car createTestCar(User driver) throws SQLException {
        Car c = Car.builder()
                .number("1395КН5")
                .driver(driver)
                .color(new Color("testColor"))
                .model(new CarModel("testModel"))
                .build();
        long id = carDao.addCar(c).orElseThrow(NullPointerException::new);
        c.setId(id);
        return c;
    }
}
