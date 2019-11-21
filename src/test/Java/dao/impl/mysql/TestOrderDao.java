package dao.impl.mysql;

import appException.dao.AppSqlException;
import dao.interfaces.DaoFactory;
import dao.interfaces.CarDao;
import dao.interfaces.OrderDao;
import dao.interfaces.UserDao;
import entity.car.Car;
import entity.car.CarBuilder;
import entity.order.Order;
import entity.user.User;
import entity.user.UserBuilder;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.sql.SQLException;

public class TestOrderDao {

    private static DaoFactory factory;
    private static OrderDao orderDao;
    private static UserDao userDao;
    private static CarDao carDao;

    private static Order order;
    private static String from = "from";
    private static String where = "where";
    private static String comments = "comments";

    private static User passenger;
    private static User driver;
    private static Car car;



    @BeforeClass
    public static void setUp() throws Exception {
        factory = DaoFactory.getFactory(DaoFactory.TypesDatabases.MY_SQL);
        orderDao = factory.getOrderDao();
        userDao = factory.getUserDao();
        carDao = factory.getCarDao();

        passenger = createTestPassenger();
        driver = createTestDriver();
        car = createTestCar(driver.getId());
        order = new Order();
    }
    @AfterClass
    public static void tearDown() throws Exception {

        delUser(passenger.getId());
        delUser(driver.getId());
        //car deleted with driver because in cars table "on delete cascade"
    }

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void test() throws SQLException, AppSqlException {

        order.setIdUser(passenger.getId());
        order.setIdCar(car.getId());
        order.setFrom(from);
        order.setWhere(where);
        order.setComments(comments);

        long id = orderDao.addOrder(order);
        order.setId(id);

        Order orderDb = orderDao.getListByIdUser(passenger.getId()).get(0);
        Assert.assertEquals(orderDb, order);

        orderDao.deleteOrder(id);
        expectedException.expect(AppSqlException.class);
        orderDao.getListByIdUser(passenger.getId());
    }

    private static User createTestDriver() throws SQLException, AppSqlException {

        User d = UserBuilder.createUser().setPhone(101).setName("driver").setSurname("order").setPassword("test").setStatus("driver").getUser();
        long id = userDao.addUser(d);
        d.setId(id);

        return d;
    }
    private static User createTestPassenger() throws SQLException, AppSqlException {

        User p = UserBuilder.createUser().setPhone(102).setName("passenger").setSurname("order").setPassword("test").getUser();
        long id = userDao.addUser(p);
        p.setId(id);

        return p;

    }
    private static Car createTestCar(long idDriver) throws SQLException, AppSqlException {

        Car c = CarBuilder.createCar().setNumber("1395КН5").setIdDriver(idDriver).setColor("testColor").setModel("testModel").setStatus(true).getCar();
        long id = carDao.addCar(c);
        c.setId(id);

        return c;

    }
    private static void delUser(long id) throws SQLException, AppSqlException {

       userDao.deleteUser(id);

    }
    private static void delCar(long id) throws SQLException, AppSqlException {
        carDao.deleteCar(id);
    }
}
