package dao.impl.mysql;

import appException.dao.AppSqlException;
import dao.interfaces.DaoFactory;
import dao.interfaces.UserDao;
import entity.user.User;
import entity.user.UserBuilder;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.sql.SQLException;

public class UserDaoTest {

    private static DaoFactory daoFactory;
    private static UserDao userDao;
    private static User user;

    private static long id ;
    private static int phone = 666666666;
    private static int phoneUpdate = 777777777;
    private static String name = "name";
    private static String surname = "surname";
    private static String password = "password";
    private static String passwordUpdate = "passUpdate";
    private static String statusDefault = "passenger";
    private static String statusUpdate = "driver";



    @BeforeClass
    public static void setUp() throws Exception {

        daoFactory = DaoFactory.getFactory(DaoFactory.TypesDatabases.MY_SQL);
        userDao = daoFactory.getUserDao();

        user = UserBuilder.createUser().setPhone(phone).setName(name).setSurname(surname).setPassword(password).getUser();
    }

    @AfterClass
    public static void tearDown() throws Exception {}

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void addUser() throws SQLException, AppSqlException{

        id = userDao.addUser(user);

        try {
            expectedException.expect(AppSqlException.class);
            userDao.addUser(user);
        }catch (AppSqlException ex){

        }catch (SQLException ex){
            throw ex;
        }


        user.setId(id);
        user.setStatus(statusDefault);

        User userById = userDao.getById(id);
        Assert.assertEquals(user, userById);

        User userByPhone = userDao.getByPhone(phone);
        Assert.assertEquals(user, userByPhone);

        userDao.updatePassword(id, passwordUpdate);
        userDao.updateName(id, surname);
        userDao.updateSurname(id, name);
        userDao.updatePhone(id, phoneUpdate);
        userDao.updateStatus(id, statusUpdate);

        user.setPassword(passwordUpdate);
        user.setName(surname);
        user.setSurname(name);
        user.setPhone(phoneUpdate);
        user.setStatus(statusUpdate);

        User userUpdate = userDao.getById(id);
        Assert.assertEquals(user, userUpdate);

        userDao.deleteUser(id);

        expectedException.expect(AppSqlException.class);
        userDao.getById(id);

    }

}
