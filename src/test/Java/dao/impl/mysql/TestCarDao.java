package dao.impl.mysql;

import appException.dao.AppSqlException;
import dao.interfaces.DaoFactory;
import dao.interfaces.CarDao;
import dao.interfaces.UserDao;
import entity.car.Car;
import entity.user.User;
import entity.user.UserBuilder;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.sql.Connection;
import java.sql.SQLException;;
import java.util.ArrayList;
import java.util.List;

public class TestCarDao {

    private static DaoFactory daoFactory;
    private static UserDao userDao;
    private static CarDao carDao;

    private static Car car;
    private static User driver;

    private static long id;
    private static String number = "1397KH5";
    private static String color = "someColor";
    private static String model = "someModel";
    private static boolean statusDefault = false;
    private static boolean statusUpdate = true;


    @BeforeClass
    public static void setUp() throws Exception {
        daoFactory = DaoFactory.getFactory(DaoFactory.TypesDatabases.MY_SQL);
        carDao = daoFactory.getCarDao();
        userDao = daoFactory.getUserDao();

        car = new Car();
        driver = createTestDriver(123456789, "driver", "car", "test", "driver");
    }

    @AfterClass()
    public static void tearDown() throws Exception {
        delDriver(driver.getId());
    }

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void test() throws SQLException, AppSqlException {

        car.setNumber(number);
        car.setColor(color);
        car.setModel(model);
        car.setIdDriver(driver.getId());

        id = carDao.addCar(car);

        try {
            expectedException.expect(AppSqlException.class);
            carDao.addCar(car);
        }catch (AppSqlException ex){}

        car.setId(id);
        car.setStatus(statusDefault);

        Car carById = carDao.getCarById(id);
        Assert.assertEquals(carById, car);

        Car carByDriver = carDao.getCarByDriver(driver.getId());
        Assert.assertEquals(carByDriver, car);

        carDao.updateStatus(id, statusUpdate);
        Car carUpdate = carDao.getCarById(id);
        car.setStatus(statusUpdate);
        Assert.assertEquals(carUpdate, car);

        List<Car> list = carDao.getListCarsByStatus(statusUpdate);
        Assert.assertTrue(list.contains(car));

        carDao.deleteCar(id);

        expectedException.expect(AppSqlException.class);
        carDao.getCarById(id);
    }

    private static User createTestDriver(int phone, String name, String surname, String password, String status) throws SQLException, AppSqlException {
        User d = UserBuilder.createUser().setPhone(phone).setName(name).setSurname(surname).setPassword(password).setStatus(status).getUser();
        long id = userDao.addUser(d);
        d.setId(id);
        return d;
    }

    private static void delDriver(long id) throws SQLException, AppSqlException{
        userDao.deleteUser(id);
    }
}
