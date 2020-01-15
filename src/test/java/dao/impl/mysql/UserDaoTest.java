package dao.impl.mysql;

import dao.interfaces.UserDao;
import entity.user.User;
import entity.user.UserStatus;
import entity.user.UserStatusType;
import org.junit.*;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.concurrent.atomic.AtomicInteger;

public class UserDaoTest {
    private static final AtomicInteger i = new AtomicInteger(1);

    private UserDao userDao;
    private User user;

    @Before
    public void setUpMethod() throws SQLException {
        userDao = new MysqlDaoFactory().getUserDao();
        user = generateUniqueUser(UserStatusType.PASSENGER, userDao);
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
        User dbUser;

        dbUser = userDao.getById(user.getId()).orElseThrow(NullPointerException::new);
        Assert.assertEquals(user, dbUser);
        dbUser = userDao.getByPhone(user.getPhone()).orElseThrow(NullPointerException::new);
        Assert.assertEquals(user, dbUser);
    }
    @Test
    public void updateUser() throws SQLException {
        User dbUser;
        String newPassword = "newPassword";
        String newName = "newName";
        String newSurname = "newSurname";
        int newPhone = getUniquePhone();
        UserStatus newStatus = UserStatus.getInstance(UserStatusType.DRIVER);

        user.setPassword(newPassword);
        user.setName(newName);
        user.setSurname(newSurname);
        user.setPhone(newPhone);
        user.setStatus(newStatus);

        userDao.updatePassword(user.getId(), newPassword);
        userDao.updateName(user.getId(), newName);
        userDao.updateSurname(user.getId(), newSurname);
        userDao.updatePhone(user.getId(), newPhone);
        userDao.updateStatus(user.getId(), newStatus);

        dbUser = userDao.getById(user.getId()).orElseThrow(NullPointerException::new);
        Assert.assertEquals(user, dbUser);
    }

    static User generateUniqueUser(UserStatusType statusType, UserDao userDao) throws SQLException {
        User user;

        user = User.builder()
                .phone( getUniquePhone() )
                .name("name")
                .surname("surname")
                .password("password")
                .status(UserStatus.getInstance(statusType))
                .build();

        long id = userDao.addUser(user).orElseThrow(NullPointerException::new);
        user.setId(id);

        return user;

    }
    private static int getUniquePhone(){
        return i.getAndIncrement();
    }

}
