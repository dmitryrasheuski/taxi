package dao.impl.mysql;

import dao.interfaces.DaoFactory;
import dao.interfaces.CarDao;
import dao.interfaces.UserDao;
import entity.car.Car;
import entity.car.CarModel;
import entity.car.Color;
import entity.user.User;
import entity.user.UserStatusType;
import org.junit.*;

import java.sql.SQLException;;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.concurrent.atomic.AtomicInteger;

public class CarDaoTest {
    private static AtomicInteger i = new AtomicInteger(1);

    private UserDao userDao;
    private CarDao carDao;
    private User driver;
    private Car car;

    @Before
    public void setUpMethod() throws SQLException{
        DaoFactory daoFactory = DaoFactory.getFactory(DaoFactory.TypesDatabases.MY_SQL);

        carDao = daoFactory.getCarDao();
        userDao = daoFactory.getUserDao();
        driver = UserDaoTest.generateUniqueUser(UserStatusType.DRIVER, userDao);
        car = produceCar(driver, carDao);

    }
    @After
    public void tearDownMethod() throws SQLException {

        carDao.deleteCar( car.getId() ).orElseThrow(NullPointerException::new);
        userDao.deleteUser( driver.getId() );

    }

    @Test(expected = SQLIntegrityConstraintViolationException.class)
    public void addCar() throws SQLException {
        //The car's number most be unique, else database throw the SQLIntegrityConstraintViolationException
        carDao.addCar(car);
    }
    @Test
    public void getCar() throws SQLException {
        Car dbCar;

        dbCar = carDao.getCarById( car.getId() ).orElseThrow(NullPointerException::new);
        Assert.assertEquals(dbCar, car);
        dbCar = carDao.getCarByDriver( driver.getId() ).orElseThrow(NullPointerException::new);
        Assert.assertEquals(dbCar, car);

    }

    static Car produceCar(User driver, CarDao carDao) throws SQLException {
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

        Car car = Car.builder()
                .driver(driver)
                .number(number)
                .model(new CarModel("testModel"))
                .color(new Color("testColor"))
                .build();
        long carId = carDao.addCar(car).orElseThrow(NullPointerException::new);
        car.setId(carId);

        return car;

    }
}
