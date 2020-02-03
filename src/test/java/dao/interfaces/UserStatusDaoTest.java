package dao.interfaces;

import dao.impl.mysql.MysqlDaoFactory;
import dao.impl.orm.jpa.HibernateDataSource;
import dao.impl.orm.jpa.JpaDaoFactory;
import entity.user.UserStatusType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.Access;
import java.sql.SQLException;
import java.util.Map;

import static org.junit.Assert.*;

public class UserStatusDaoTest {
    private UserStatusDao userStatusDao;

    @Before
    public void setUp() throws Exception {
        userStatusDao = new JpaDaoFactory(HibernateDataSource.getInstance()).getUserStatusDao();
    }
    @Test
    public void getMap() throws SQLException {
        UserStatusType.values();
        Map<Long, String> map = userStatusDao.getMap();

        Assert.assertEquals( map.size(), 3 );

        map.forEach(
                (id, title) -> Assert.assertEquals(
                                                    UserStatusType.getStatus(id).get(),
                                                    UserStatusType.valueOf(title).getStatus()
                )
        );
    }
}