package dao.interfaces;

import dao.impl.orm.jpa.HibernateDataSource;
import dao.impl.orm.jpa.JpaDaoFactory;
import dao.interfaces.ColorDao;
import entity.car.Color;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class ColorDaoTest {
    private ColorDao colorDao;
    private Color color;

    @Before
    public void setUp() throws SQLException {
        colorDao = new JpaDaoFactory(HibernateDataSource.getInstance()).getColorDao();
    }

    @After
    public void tearDown() throws Exception {
        if (color != null) colorDao.remove(color);
    }


    @Test(expected = SQLIntegrityConstraintViolationException.class)
    public void addColorTest() throws SQLException {
        String title = "addColorTest";
        color = addColorToDB(title);

        colorDao.addColor(title);
    }

    @Test
    public void getColorTest() throws SQLException {
        color = addColorToDB("getColorTest");

        Color receivedColor = colorDao.getByTitle( color.getTitle() ).get();
        Assert.assertEquals(color, receivedColor);

        receivedColor = colorDao.getById( color.getId() ).get();
        Assert.assertEquals(color, receivedColor);

        removeColor(color);
    }

    @Test
    public void getOrElseAddAndGetTest() throws SQLException {

        color = colorDao.getOrElseAddAndGet( "newColorDaoTest" );
        Color receivedColor = colorDao.getOrElseAddAndGet( color.getTitle() );
        Assert.assertEquals(color, receivedColor);

        removeColor(color);
    }

    @Test
    public void removeTest() throws SQLException {
        color = addColorToDB("removeTest");
        boolean res = colorDao.remove(color);
        Assert.assertTrue(res);
    }

    private Color addColorToDB(String title) throws SQLException {
        Long id = colorDao.addColor(title)
                .orElseThrow(NullPointerException::new);

        return new Color(id, title);
    }
    private void removeColor(Color color) throws SQLException {
        colorDao.remove(color);
        color = null;
    }

}
