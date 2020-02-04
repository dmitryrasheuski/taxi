package dao.impl.mysql;

import dao.interfaces.CarDao;
import dao.interfaces.ColorDao;
import entity.car.Car;
import entity.car.CarModel;
import entity.car.Color;
import entity.user.User;
import lombok.extern.log4j.Log4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j
class CarDaoImpl extends AbstractDao<Car> implements CarDao{
    private static final String addCar = "INSERT INTO cars(number, color_id, model_id) values(?, ?, ?)";
    private static final String buildCarDriverRelationship = "INSERT INTO car_driver(driver_id, car_id) VALUE(?, ?)";
    private static final String deleteCar = "DELETE FROM cars WHERE id = ?";
    private static final String getCarById = "SELECT id, number, driver_id, color_id, model_id FROM cars c LEFT JOIN car_driver cd ON c.id = cd.car_id WHERE id = ?";
    private static final String getCarByDriver = "SELECT id, number, driver_id, color_id, model_id FROM cars c LEFT JOIN car_driver cd ON c.id = cd.car_id  WHERE driver_id = ?";

    CarDaoImpl(MysqlDaoFactory daoFactory){
        super(daoFactory);
    }

    @Override
    public Optional<Long> addCar(Car car) throws SQLException {

        setColorCar(car);
        setModelCar(car);

        Long carId = addEntity(car, addCar).orElseThrow(() -> new IllegalStateException("The new car wasn't added to database"));

        try {
            addCarDriverRelationship(carId, car.getDriver().getId());
        } catch (Exception ex) {
            log.error("Exception when adding a relationship", ex);
            deleteCar(carId);
            carId = null;
            throw ex;
        }

        return Optional.ofNullable(carId);

    }
    @Override
    public Optional<Integer> deleteCar(long idCar) throws SQLException {
        Object[] parameters = new Object[] {idCar};

        return deleteOrUpdateEntity(parameters, deleteCar);

    }
    @Override
    public Optional<Car> getCarById(long idCar) throws SQLException {
        Object[] parameters = new Object[] {idCar};

        return getEntity(parameters, getCarById).map((list) -> list.get(0));

    }
    @Override
    public Optional<Car> getCarByDriver(long idDriver) throws SQLException {
        Object[] parameters = new Object[] {idDriver};

        return getEntity(parameters, getCarByDriver).map((list) -> list.get(0));

    }

    @Override
    PreparedStatement getPreparedStatementForAddEntity(String sqlInsert, Car entity) throws SQLException {
        PreparedStatement ps;

        ps = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, entity.getNumber());
        ps.setLong(2, entity.getColor().getId());
        ps.setLong(3, entity.getModel().getId());

        return ps;
    }
    @Override
    Optional<List<Car>> handleResultSet(ResultSet rs) throws SQLException {
        if (!rs.next()){
            return Optional.empty();
        }

        List<Car> carList = new ArrayList<>();
        Car car;
        long id;
        long driverId;
        User driver;
        String number;
        int colorId;
        Color color;
        int carModelId;
        CarModel model;
        do{
            id = rs.getLong("id");
            number = rs.getString("number");
            driverId = rs.getLong("driver_id");
            driver = daoFactory.getUserDao()
                    .getById(driverId)
                    .orElseThrow(() -> new IllegalStateException("Driver wasn't fount in the database"));
            colorId = rs.getInt("color_id");
            color = daoFactory.getColorDao()
                    .getById(colorId)
                    .orElseThrow(() -> new IllegalStateException("Color wasn't fount in the database"));
            carModelId = rs.getInt("model_id");
            model = daoFactory.getCarModelDao()
                    .getById(carModelId)
                    .orElseThrow(() -> new IllegalStateException("CarModel wasn't fount in the database"));
            car = Car.builder()
                    .id(id)
                    .driver(driver)
                    .number(number)
                    .color(color)
                    .model(model)
                    .build();
            carList.add(car);
        } while (rs.next());

        return Optional.of(carList);
    }

    private void addCarDriverRelationship(long carId, long driverId) throws SQLException {
        Object[] parameters = new Object[] {driverId, carId};
        PreparedStatement ps = null;
        Exception exception = null;

        try {
            ps = con.prepareStatement(buildCarDriverRelationship);
            preparedStatementParameterSetter(parameters, ps);
            ps.executeUpdate();

        } catch (Exception ex){
            exception = ex;
            throw ex;

        } finally {
            close(ps, exception);
        }

    }
    private void setColorCar(Car car) throws SQLException {

        Color color = daoFactory.getColorDao()
                .getOrElseAddAndGet( car.getColor().getTitle() );

        car.setColor(color);
    }
    private void setModelCar(Car car) throws SQLException {

        CarModel model = daoFactory.getCarModelDao()
                .getOrElseAddAndGet( car.getModel().getTitle() );

        car.setModel(model);

    }

}
