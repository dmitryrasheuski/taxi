package service.impl;

import appException.dao.AppSqlException;
import appException.service.AppServiceException;
import dao.interfaces.DaoFactory;
import dao.interfaces.CarDao;
import entity.car.Car;
import org.apache.log4j.Logger;
import service.interfaces.IGetCar;

import java.sql.SQLException;

public class CarService implements IGetCar {
    private static final Logger logger = Logger.getLogger(CarService.class);
    private DaoFactory daoFactory = DaoFactory.getFactory(DaoFactory.TypesDatabases.MY_SQL);
    private CarDao carDao = daoFactory.getCarDao();

    @Override
    public Car getCar(long idCar) throws AppServiceException {
        logger.debug("start getCar(long)");

        Car car = null;

        try {
            car = carDao.getCarById(idCar);
        } catch (AppSqlException | SQLException ex) {
            logger.info("getCar(long) catch (SQLException | AppSqlException ex) : carDao.getCarById(long) throw exception");
            throw new AppServiceException(ex);
        }

        logger.debug("start getCar(long)");
        return car;
    }
}
