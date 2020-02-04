package dao.impl.orm.jpa;

import entity.car.Car;
import entity.car.CarModel;
import entity.car.Color;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

class CarDao extends AbstractDao<Car> implements dao.interfaces.CarDao {
    private static final String getByDriverId = "SELECT c FROM Car c WHERE c.driver.id = :driverId";

    CarDao(JpaDaoFactory daoFactory) {
        super(daoFactory);
    }

    @Override
    public Optional<Long> addCar(Car car) throws SQLException {
        Color color = car.getColor();
        color = daoFactory.getColorDao().getOrElseAddAndGet( color.getTitle() );
        car.setColor(color);

        CarModel model = car.getModel();
        model = daoFactory.getCarModelDao().getOrElseAddAndGet(model.getTitle());
        car.setModel(model);

        return addEntity(car)
                .map(Car::getId);
    }
    @Override
    public Optional<Integer> deleteCar(long idCar) throws SQLException {

        boolean success = removeEntity(Car.class, idCar);

        return success ? Optional.of(1) : Optional.empty();
    }
    @Override
    public Optional<Car> getCarById(long idCar) throws SQLException {
        return getEntity(Car.class, idCar);
    }
    @Override
    public Optional<Car> getCarByDriver(long driverId) throws SQLException {
        Map<String, Object> parameters = new LinkedHashMap<>(1);
        parameters.put("driverId", driverId);

        List<Car> list = getEntities(getByDriverId, parameters);

        return list.isEmpty() ? Optional.empty() : Optional.of( list.get(0) );
    }
}
