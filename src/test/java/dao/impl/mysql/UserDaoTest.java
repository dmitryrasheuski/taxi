package dao.impl.mysql;

import dao.interfaces.DaoFactory;
import dao.interfaces.UserDao;
import entity.user.User;
import entity.user.UserStatus;
import entity.user.UserStatusType;
import org.junit.*;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.concurrent.atomic.AtomicInteger;

public class UserDaoTest {

    private static DaoFactory daoFactory;
    private static UserDao userDao;
    private static AtomicInteger i = new AtomicInteger(1);

    private static String name = "name";
    private static String surname = "surname";
    private static String password = "password";
    private static UserStatus statusDefault = UserStatus.getInstance(UserStatusType.PASSENGER);
    private static String passwordUpdate = "passUpdate";
    private static UserStatus statusUpdate = UserStatus.getInstance(UserStatusType.DRIVER);

    private User user;
    private User dbUser;


    @BeforeClass
    public static void setUp(){
        daoFactory = DaoFactory.getFactory(DaoFactory.TypesDatabases.MY_SQL);
        userDao = daoFactory.getUserDao();
    }
    @AfterClass
    public static void tearDown(){
        daoFactory.closeDatasource();
    }

    @Before
    public void setUpMethod() throws SQLException {
        int phone = getUniquePhone();
        user = User.builder()
                .phone(phone)
                .name(name)
                .surname(surname)
                .password(password)
                .status(statusDefault)
                .build();
        long id = userDao.addUser(user).orElseThrow(NullPointerException::new);
        user.setId(id);
    }
    @After
    public void tearDownMethod() throws SQLException {
        userDao.deleteUser(user.getId()).orElseThrow(NullPointerException::new);
    }

    @Test(expected = SQLIntegrityConstraintViolationException.class)
    public void addUser() throws SQLException {
        //User's phone most be unique, else database throw the SQLIntegrityConstraintViolationException
        userDao.addUser(user);
    }
    @Test
    public void getUser() throws SQLException {
        dbUser = userDao.getById(user.getId()).orElseThrow(NullPointerException::new);
        Assert.assertEquals(user, dbUser);
        dbUser = userDao.getByPhone(user.getPhone()).orElseThrow(NullPointerException::new);
        Assert.assertEquals(user, dbUser);
    }
    @Test
    public void updateUser() throws SQLException {
        int phoneUpdate = getUniquePhone();

        user.setPassword(passwordUpdate);
        user.setName(surname);
        user.setSurname(name);
        user.setPhone(phoneUpdate);
        user.setStatus(statusUpdate);

        userDao.updatePassword(user.getId(), passwordUpdate);
        userDao.updateName(user.getId(), surname);
        userDao.updateSurname(user.getId(), name);
        userDao.updatePhone(user.getId(), phoneUpdate);
        userDao.updateStatus(user.getId(), statusUpdate);

        dbUser = userDao.getById(user.getId()).orElseThrow(NullPointerException::new);
        Assert.assertEquals(user, dbUser);
    }

    private int getUniquePhone(){
        return i.getAndIncrement();
    }

}
