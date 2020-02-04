package dao.interfaces;

import dao.impl.mysql.MysqlDaoFactory;
import dao.impl.orm.jpa.HibernateDataSource;
import dao.impl.orm.jpa.JpaDaoFactory;
import entity.car.Car;
import entity.order.Address;
import entity.order.Order;
import entity.user.User;
import entity.user.UserStatusType;
import org.junit.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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
        daoFactory = new JpaDaoFactory(HibernateDataSource.getInstance());
        orderDao = daoFactory.getOrderDao();
        userDao = daoFactory.getUserDao();
        carDao = daoFactory.getCarDao();
        addressDao = daoFactory.getAddressDao();

        passenger = UserDaoTest.generateAndAddToDbUniqueUser( UserStatusType.PASSENGER, userDao );
        driver = UserDaoTest.generateAndAddToDbUniqueUser( UserStatusType.DRIVER, userDao );
        car = CarDaoTest.generateAndAddToDbUniqueCar(driver, carDao);

        from = addressDao.getAddress( "From" ).get();
        where = addressDao.getAddress( "Where" ).get();

        order = createNewOrder();

    }
    @After
    public void tearDownMethod() throws SQLException {
        if (  order != null ) orderDao.deleteOrder( order.getId() );
        carDao.deleteCar (car.getId() );
        userDao.deleteUser( driver.getId() );
        userDao.deleteUser( passenger.getId() );
    }

    @Test
    public void addOrderDaoTest() throws SQLException {

        Assert.assertNotNull( order.getId() );
        Assert.assertEquals( order.getPassenger() , passenger );
        Assert.assertEquals( order.getCar() ,  car );
        Assert.assertEquals( order.getFrom() , from );
        Assert.assertEquals( order.getWhere() , where );
    }

    @Test
    public void deleteOrderDaoTest() throws SQLException {

        int quantity = orderDao.deleteOrder( order.getId() ).get();

        Assert.assertEquals(quantity, 1);
        Optional list = orderDao.getListByPassengerId( passenger.getId() );
        Assert.assertFalse( list.isPresent() );
    }

    @Test
    public void getListOrderByUserTest() throws SQLException {
        Order newOrder = createNewOrder();

        List<Order> list = orderDao.getListByPassengerId(passenger.getId()).get();
        Assert.assertTrue( list.contains(order) );
        Assert.assertTrue( list.contains(newOrder) );

        orderDao.deleteOrder( newOrder.getId() );
    }

    private Order createNewOrder() throws SQLException {


        Order order = Order.builder()
                .passenger(passenger)
                .car(car)
                .from(from)
                .where(where)
                .comment("Comment")
                .build();

        long id2 = orderDao.addOrder(order).orElseThrow(NullPointerException::new);
        order.setId(id2);

        return order;
    }
}
