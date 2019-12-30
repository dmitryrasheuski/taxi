package dao.impl.mysql;

import dao.interfaces.CarDao;
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
    private static final String addCar = "INSERT INTO cars(number, driver_id, color_id, model_id, active) values(?, ?, ?, ?, ?)";
    private static final String deleteCar = "DELETE FROM cars WHERE id = ?";
    private static final String updateActive = "UPDATE cars SET active = ? WHERE id = ?";
    private static final String getCarById = "SELECT id, number, driver_id, color_id, model_id, active FROM cars WHERE id = ?";
    private static final String getCarByDriver = "SELECT id, number, driver_id, color_id, model_id, active FROM cars WHERE driver_id = ?";
    private static final String getListCarByActive = "SELECT id, number, driver_id, color_id, model_id, active FROM cars WHERE active = ?";

    CarDaoImpl(MysqlDaoFactory daoFactory){
        super(daoFactory);
    }

    @Override
    public Optional<Long> addCar(Car car) throws SQLException {
        int colorId = daoFactory.getColorDao()
                .getIdOrElseAddAndGet(car.getColor().getTitle())
                .orElseThrow(() -> new IllegalStateException("The value 'color.title'  wasn't found and wasn't added in the database"));
        car.getColor().setId(colorId);
        int modelId = daoFactory.getCarModelDao()
                .getOrElseAddAndGetId(car.getModel().getTitle())
                .map(CarModel::getId)
                .orElseThrow(() -> new IllegalStateException("The value 'carModel.title'  wasn't found and wasn't added in the database"));
        car.getModel().setId(modelId);

        return addEntity(car, addCar);
    }
    @Override
    public Optional<Integer> deleteCar(long idCar) throws SQLException {
        return deleteById(idCar, deleteCar);
    }
    @Override
    public Optional<Integer> updateActive(long idCar, boolean active) throws SQLException {
        return updateOneColumnById(idCar, active, updateActive);
    }
    @Override
    public Optional<Car> getCarById(long idCar) throws SQLException {
        return getEntityByOneValue(idCar, getCarById)
                .map((list) -> list.get(0));
    }
    @Override
    public Optional<Car> getCarByDriver(long idDriver) throws SQLException {
        return getEntityByOneValue(idDriver, getCarByDriver)
                .map((list) -> list.get(0));
    }
    @Override
    public Optional<List<Car>> getListCarsByStatus(boolean status) throws SQLException {
       return getEntityByOneValue(status, getListCarByActive);
    }

    @Override
    PreparedStatement getPreparedStatementForAddEntity(Connection con, PreparedStatement ps, String sqlInsert, Car entity) throws SQLException {
        ps = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, entity.getNumber());
        ps.setLong(2, entity.getDriver().getId());
        ps.setInt(3, entity.getColor().getId());
        ps.setInt(4, entity.getModel().getId());
        ps.setBoolean(5, entity.isActive());
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
        long idDriver;
        User driver;
        String number;
        int colorId;
        Color color;
        int carModelId;
        CarModel model;
        boolean active;
        do{
            id = rs.getLong("id");
            number = rs.getString("number");
            idDriver = rs.getLong("driver_id");
            driver = daoFactory.getUserDao()
                    .getById(idDriver)
                    .orElseThrow(() -> new IllegalStateException("Driver wasn't fount in the database"));
            colorId = rs.getInt("color_id");
            color = daoFactory.getColorDao()
                    .getById(colorId)
                    .orElseThrow(() -> new IllegalStateException("Color wasn't fount in the database"));
            carModelId = rs.getInt("model_id");
            model = daoFactory.getCarModelDao()
                    .getById(carModelId)
                    .orElseThrow(() -> new IllegalStateException("CarModel wasn't fount in the database"));
            active = rs.getBoolean("active");
            car = Car.builder()
                    .id(id)
                    .driver(driver)
                    .number(number)
                    .color(color)
                    .model(model)
                    .active(active)
                    .build();
            carList.add(car);
        } while (rs.next());

        return Optional.of(carList);
    }
}
