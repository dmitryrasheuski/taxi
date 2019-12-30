package dao.impl.mysql;

import dao.interfaces.CarDao;
import entity.car.Car;
import entity.car.CarModel;
import entity.car.Color;
import lombok.extern.log4j.Log4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j
class CarDaoImpl extends AbstractDao<Car> implements CarDao{
    private static final String addCar = "INSERT INTO cars(number, driver_id, color_id, model_id, active) values(?, ?, ?, ?, ?)";
    private static final String deleteCar = "DELETE FROM cars WHERE id = ?";
    private static final String updateStatus = "UPDATE cars SET status = ? WHERE id = ?";
    private static final String getCarById = "SELECT c.id, c.number, c.idDriver, cc.color, mc.model, c.status FROM cars AS c LEFT JOIN colorCar cc on c.color = cc.id LEFT JOIN modelCar mc ON c.model = mc.id WHERE c.id = ?";
    private static final String getCarByDriver = "SELECT c.id, c.number, c.idDriver, cc.color, mc.model, c.status FROM cars AS c LEFT JOIN colorCar cc on c.color = cc.id LEFT JOIN modelCar mc ON c.model = mc.id WHERE c.idDriver = ?";
    private static final String  getListCarByStatus= "SELECT c.id, c.number, c.idDriver, cc.color, mc.model, c.status FROM cars AS c LEFT JOIN colorCar cc on c.color = cc.id LEFT JOIN modelCar mc ON c.model = mc.id WHERE c.status = ?";

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
    public Optional<Integer> updateStatus(long idCar, boolean status) throws SQLException {
        return updateOneColumnById(idCar, status, updateStatus);
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
       return getEntityByOneValue(status, getListCarByStatus);
    }

    private Optional<Integer> getIdColorOrModel(String sqlSelect, String sqlInsert, String value, Connection con) throws SQLException {
        int id = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Exception exception = null;
        try {
            ps = con.prepareStatement(sqlSelect);
            ps.setString(1, value);
            rs = ps.executeQuery();

            if (rs.next()){
                return Optional.of(rs.getInt(1));
            } else {
                return addColorOrModel(sqlInsert, value, con);
            }

        } catch (Exception ex) {
            exception = ex;
            throw ex;

        } finally {
            close(rs, exception);
            close(ps, exception);
        }
    }
    private Optional<Integer> addColorOrModel(String sqlInsert, String value, Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Exception exception = null;
        try {
            ps = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, value);
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();

            if (rs.next()) {
                return Optional.of(rs.getInt(1));
            } else {
                return Optional.empty();
            }

        } catch (Exception ex) {
            exception = ex;
            throw ex;

        } finally {
            close(rs, exception);
            close(ps, exception);
        }
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
        String number;
        String color;
        String model;
        boolean status;
        do{
            id = rs.getLong("id");
            idDriver = rs.getLong("idDriver");
            number = rs.getString("number");
            color = rs.getString("color");
            model = rs.getString("model");
            status = rs.getBoolean("status");
            car = Car.builder()
                    .id(id)
                    .driver(daoFactory.getUserDao().getById(idDriver).orElseThrow(() -> new NullPointerException("Driver by id was not found")))
                    .number(number)
                    .color(new Color(color))
                    .model(new CarModel(model))
                    .active(status)
                    .build();
            carList.add(car);
        } while (rs.next());

        return Optional.of(carList);
    }
}
