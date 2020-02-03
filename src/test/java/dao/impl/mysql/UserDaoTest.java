package dao.impl.mysql;

import dao.impl.orm.jpa.HibernateDataSource;
import dao.impl.orm.jpa.JpaDaoFactory;
import dao.interfaces.UserDao;
import entity.user.User;
import entity.user.UserStatus;
import entity.user.UserStatusType;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.concurrent.atomic.AtomicInteger;

public class UserDaoTest {
    private static final AtomicInteger i = new AtomicInteger(1);

    private UserDao userDao;
    private User user;

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUpMethod() throws SQLException {
        userDao = new JpaDaoFactory(HibernateDataSource.getInstance()).getUserDao();
    }
    @After
    public void tearDownMethod() throws SQLException {
        if( user != null ) {
            userDao.deleteUser( user.getId() );
        }
    }

    @Test
    public void addUser() throws SQLException {
        user = generateAndAddToDbUniqueUser(UserStatusType.PASSENGER, userDao);

        User duplicateUser = User.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .phone(user.getPhone())
                .password(user.getPassword())
                .status(user.getStatus())
                .build();

        try {
            userDao.addUser(duplicateUser);
        } catch (Exception ex) {
            Assert.assertEquals(SQLIntegrityConstraintViolationException.class, ex.getClass());
        }

        removeUser();
    }
    @Test
    public void getUser() throws SQLException {
        user = generateAndAddToDbUniqueUser(UserStatusType.PASSENGER, userDao);
        User dbUser;

        dbUser = userDao.getById ( user.getId() ).orElseThrow(NullPointerException::new);
        Assert.assertEquals(user, dbUser);

        dbUser = userDao.getByPhone( user.getPhone() ).orElseThrow(NullPointerException::new);
        Assert.assertEquals(user, dbUser);

        removeUser();
    }
    @Test
    public void updateUser() throws SQLException {
        user = generateAndAddToDbUniqueUser(UserStatusType.PASSENGER, userDao);

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

        removeUser();
    }

    @Test
    public void removeUserTest() throws SQLException {
        user = generateAndAddToDbUniqueUser(UserStatusType.PASSENGER, userDao);

        int res = userDao.deleteUser( user.getId() ).orElseThrow(NullPointerException::new);
        Assert.assertEquals(1, res);

    }

    static User generateAndAddToDbUniqueUser(UserStatusType statusType, UserDao userDao) throws SQLException {
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

    private void removeUser() throws SQLException {
        userDao.deleteUser( user.getId() );
        user = null;
    }

}
