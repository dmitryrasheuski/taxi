package dao.impl.mysql;

import dao.interfaces.DaoFactory;
import dao.interfaces.CarDao;
import dao.interfaces.OrderDao;
import dao.interfaces.UserDao;
import entity.car.Car;
import entity.car.CarBuilder;
import entity.order.Order;
import entity.order.OrderBuilder;
import entity.user.User;
import entity.user.UserBuilder;
import org.junit.*;

import java.sql.SQLException;
import java.util.List;

public class OrderDaoTest {

    private static DaoFactory daoFactory;
    private static OrderDao orderDao;
    private static UserDao userDao;
    private static CarDao carDao;

    private static String from = "from";
    private static String where = "where";
    private static String comments = "comments";

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

        passenger = createTestPassenger();
        driver = createTestDriver();
        car = createTestCar(driver.getId());
    }
    @AfterClass
    public static void tearDown() throws Exception {
        //car deleted with driver because in cars table "on delete cascade"
        userDao.deleteUser(driver.getId());
        userDao.deleteUser(passenger.getId());
        daoFactory.closeDatasource();
    }

    @Before
    public void setUpMethod() throws SQLException {
        order = OrderBuilder.createOrder().setIdUser(passenger.getId()).setIdCar(car.getId()).setFrom(from).setWhere(where).setComments(comments).getOrder();
        long id = orderDao.addOrder(order).orElseThrow(NullPointerException::new);
        order.setId(id);
    }
    @After
    public void tearDownMethod() throws SQLException {
        orderDao.deleteOrder(order.getId()).orElseThrow(NullPointerException::new);
    }

    @Test
    public void getListOrderByUser() throws SQLException {
        Order order_2 = OrderBuilder.createOrder().setIdUser(passenger.getId()).setIdCar(car.getId()).setFrom(from).setWhere(where).setComments(comments).getOrder();
        long id2 = orderDao.addOrder(order_2).orElseThrow(NullPointerException::new);
        order_2.setId(id2);

        List<Order> list = orderDao.getListByIdUser(passenger.getId()).orElseThrow(NullPointerException::new);
        Assert.assertTrue(list.contains(order));
        Assert.assertTrue(list.contains(order_2));

        orderDao.deleteOrder(id2);
    }

    private static User createTestDriver() throws SQLException {
        User d = UserBuilder.createUser().setPhone(101).setName("driver").setSurname("order").setPassword("test").setStatus("driver").getUser();
        long id = userDao.addUser(d).orElseThrow(NullPointerException::new);
        d.setId(id);
        return d;
    }
    private static User createTestPassenger() throws SQLException {
        User p = UserBuilder.createUser().setPhone(102).setName("passenger").setSurname("order").setPassword("test").getUser();
        long id = userDao.addUser(p).orElseThrow(NullPointerException::new);
        p.setId(id);
        return p;
    }
    private static Car createTestCar(long idDriver) throws SQLException {
        Car c = CarBuilder.createCar().setNumber("1395КН5").setIdDriver(idDriver).setColor("testColor").setModel("testModel").setStatus(true).getCar();
        long id = carDao.addCar(c).orElseThrow(NullPointerException::new);
        c.setId(id);
        return c;
    }
}
