package dao.interfaces;

import dao.impl.orm.jpa.HibernateDataSource;
import dao.impl.orm.jpa.JpaDaoFactory;
import entity.car.CarModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class CarModelDaoTest {
    private CarModelDao carModelDao;
    private CarModel carModel;

    @Before
    public void setUp() throws Exception {
        carModelDao = new JpaDaoFactory(HibernateDataSource.getInstance()).getCarModelDao();
    }
    @After
    public void tearDown() throws Exception {
        if (carModel != null) carModelDao.removeCarModel(carModel);
    }

    @Test
    public void addCarModel() throws SQLException {
        String title = "addCarModel";
        carModel = addCarModel(title, carModelDao);

        CarModel duplicateCarModel = new CarModel(title);
        try {
            carModelDao.addCarModel(duplicateCarModel);
        } catch (Exception ex) {
            Assert.assertEquals(SQLIntegrityConstraintViolationException.class, ex.getClass());
        }

        carModelDao.removeCarModel(carModel);
    }

    @Test
    public void getCarModel() throws SQLException {
        String title = "getCarModel";
        carModel = addCarModel(title, carModelDao);
        CarModel receivedModel;

        receivedModel = carModelDao.getById( carModel.getId() ).orElseThrow(NullPointerException::new);
        Assert.assertEquals(receivedModel, carModel);

        receivedModel = carModelDao.getCarModel( carModel.getTitle() ).orElseThrow(NullPointerException::new);
        Assert.assertEquals(receivedModel, carModel);

        carModelDao.removeCarModel(carModel);
    }

    @Test
    public void getOrElseAddAndGet() throws SQLException {
        String title = "getOrElseAddAndGet";
        CarModel receivedCarModel;

        carModel = carModelDao.getOrElseAddAndGet(title);
        Assert.assertNotNull(carModel);
        receivedCarModel = carModelDao.getOrElseAddAndGet(title);
        Assert.assertEquals(carModel, receivedCarModel);

        carModelDao.removeCarModel(carModel);
    }

    @Test
    public void getById() throws SQLException {
        carModel = addCarModel("getById", carModelDao);
        CarModel receivedCarModel;

        receivedCarModel = carModelDao.getById( carModel.getId() ).orElseThrow(NullPointerException::new);
        Assert.assertEquals(carModel, receivedCarModel);

        carModelDao.removeCarModel(carModel);
    }

    @Test
    public void removeCarModel() throws SQLException {
        carModel = addCarModel("removeCarModel", carModelDao);

        boolean res = carModelDao.removeCarModel(carModel);
        Assert.assertTrue(res);
    }

    static CarModel addCarModel(String title, CarModelDao carModelDao) throws SQLException {
        CarModel model = new CarModel(title);
        long id = carModelDao.addCarModel(model).orElseThrow(NullPointerException::new);
        model.setId(id);

        return model;
    }

}