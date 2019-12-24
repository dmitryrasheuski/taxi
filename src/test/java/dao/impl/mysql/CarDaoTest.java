package dao.impl.mysql;

import dao.interfaces.DaoFactory;
import dao.interfaces.CarDao;
import dao.interfaces.UserDao;
import entity.car.Car;
import entity.user.User;
import entity.user.UserStatus;
import entity.user.UserStatusType;
import org.junit.*;

import java.sql.SQLException;;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CarDaoTest {

    private static DaoFactory daoFactory;
    private static UserDao userDao;
    private static CarDao carDao;
    private static User driver;
    private static AtomicInteger i = new AtomicInteger(1);

    private static String color = "someColor";
    private static String model = "someModel";
    private static boolean statusDefault = false;
    private static boolean statusUpdate = true;

    private Car car;
    private Car dbCar;
    private long idCar;

    @BeforeClass
    public static void setUp() throws Exception {
        daoFactory = DaoFactory.getFactory(DaoFactory.TypesDatabases.MY_SQL);
        carDao = daoFactory.getCarDao();
        userDao = daoFactory.getUserDao();

        driver = createTestDriver(123456789, "test", "test", "test", "driver");
    }
    @AfterClass()
    public static void tearDown() throws Exception {
        userDao.deleteUser(driver.getId());
        daoFactory.closeDatasource();
    }

    private static User createTestDriver(int phone, String name, String surname, String password, String status) throws SQLException {
        User d = User.builder()
                .phone(phone)
                .name(name)
                .surname(surname)
                .password(password)
                .status(UserStatus.getInstance(UserStatusType.valueOf(status.toUpperCase())))
                .build();
        long id = userDao.addUser(d).orElseThrow(NullPointerException::new);
        d.setId(id);
        return d;
    }

    @Before
    public void setUpMethod() throws SQLException{
        car = produceCar();
        idCar = carDao.addCar(car).orElseThrow(NullPointerException::new);
        car.setId(idCar);
        //If the car was created without "car.status", then the database set default "car.status = false"
        car.setActive(statusDefault);
    }
    @After
    public void tearDownMethod() throws SQLException {
        carDao.deleteCar(idCar).orElseThrow(NullPointerException::new);
    }

    @Test(expected = SQLIntegrityConstraintViolationException.class)
    public void addCar() throws SQLException {
        //The car's number most be unique, else database throw the SQLIntegrityConstraintViolationException
        carDao.addCar(car);
    }
    @Test
    public void getCar() throws SQLException {
        dbCar = carDao.getCarById(idCar).orElseThrow(NullPointerException::new);
        Assert.assertEquals(dbCar, car);
        dbCar = carDao.getCarByDriver(driver.getId()).orElseThrow(NullPointerException::new);
        Assert.assertEquals(dbCar, car);
    }
    @Test
    public void updateCar() throws SQLException {
        car.setActive(statusUpdate);
        carDao.updateStatus(idCar, statusUpdate).orElseThrow(NullPointerException::new);
        dbCar = carDao.getCarById(idCar).orElseThrow(NullPointerException::new);
        Assert.assertEquals(dbCar, car);
    }
    @Test
    public void getListCar() throws SQLException {
        car.setActive(statusUpdate);
        carDao.updateStatus(idCar, statusUpdate).orElseThrow(NullPointerException::new);
        Car car_2 = produceCar();
        car_2.setActive(statusUpdate);
        long idCar_2 = carDao.addCar(car_2).orElseThrow(NullPointerException::new);
        car_2.setId(idCar_2);

        List<Car> list = carDao.getListCarsByStatus(statusUpdate).orElseThrow(NullPointerException::new);
        Assert.assertTrue(list.contains(car));
        Assert.assertTrue(list.contains(car_2));

        carDao.deleteCar(car_2.getId()).orElseThrow(NullPointerException::new);
    }

    private Car produceCar(){
        Integer count = i.getAndIncrement();
        String number = count.toString();
        switch (number.length()) {
            case (1) : {
                number = "000" + count + "ТТ0";
                break;
            }
            case (2) : {
                number = "00" + count + "ТТ0";
                break;
            }
            case (3) : {
                number = "0" + count + "ТТ0";
                break;
            }
            case (4) : {
                number = count + "ТТ0";
                break;
            }
        }
        return Car.builder().idDriver(driver.getId()).number(number).model(model).color(color).build();
    }
}
