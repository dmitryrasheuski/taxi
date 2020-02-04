package dao.interfaces;

import dao.impl.mysql.MysqlDaoFactory;
import dao.impl.orm.jpa.HibernateDataSource;
import dao.impl.orm.jpa.JpaDaoFactory;
import entity.car.Car;
import entity.car.CarModel;
import entity.car.Color;
import entity.user.User;
import entity.user.UserStatusType;
import org.junit.*;

import java.sql.SQLException;;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class CarDaoTest {
    private static AtomicInteger i = new AtomicInteger(1);

    private UserDao userDao;
    private CarDao carDao;
    private User driver;
    private Car car;

    @Before
    public void setUpMethod() throws SQLException{
        DaoFactory daoFactory = new JpaDaoFactory(HibernateDataSource.getInstance());
        carDao = daoFactory.getCarDao();
        userDao = daoFactory.getUserDao();

        driver = UserDaoTest.generateAndAddToDbUniqueUser(UserStatusType.DRIVER, userDao);
        car = generateAndAddToDbUniqueCar(driver, carDao);
    }
    @After
    public void tearDownMethod() throws SQLException {
        if ( car != null ) carDao.deleteCar( car.getId() );
        if ( driver != null ) userDao.deleteUser( driver.getId() );
    }

    @Test
    public void addCarTest() throws SQLException {

        User newDriver = UserDaoTest.generateAndAddToDbUniqueUser(UserStatusType.DRIVER, userDao);
        Car duplicateNumberCar = Car.builder()
                .number( car.getNumber() )
                .model( car.getModel() )
                .color( car.getColor() )
                .driver(newDriver)
                .build();
        try {
            carDao.addCar(duplicateNumberCar);
        } catch (Exception ex) {
            Assert.assertEquals(SQLIntegrityConstraintViolationException.class, ex.getClass());
        }

        Car duplicateDriverCar;
        try {
            duplicateDriverCar = generateAndAddToDbUniqueCar(driver, carDao);
        } catch (Exception ex) {
            Assert.assertEquals(SQLIntegrityConstraintViolationException.class, ex.getClass());
        }

        userDao.deleteUser( newDriver.getId() );
    }

    @Test
    public void getCarByIdTest() throws SQLException {

        Car receivedCar = carDao.getCarById( car.getId() ).orElseThrow(NullPointerException::new);
        Assert.assertEquals(receivedCar, car);
    }

    @Test
    public void getCarByDriverTest() throws SQLException {

        Car receivedCar = carDao.getCarByDriver( car.getDriver().getId() ).orElseThrow(NullPointerException::new);
        Assert.assertEquals(receivedCar, car);
    }

    @Test
    public void deleteCarTest() throws SQLException {

        int quantity = carDao.deleteCar( car.getId() ).orElseThrow(NullPointerException::new);
        Assert.assertEquals(quantity, 1);

       car = null;
    }

    static Car generateAndAddToDbUniqueCar(User driver, CarDao carDao) throws SQLException {
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
        long carId = carDao.addCar(car)
                .orElseThrow(NullPointerException::new);
        car.setId(carId);

        return car;

    }
}
