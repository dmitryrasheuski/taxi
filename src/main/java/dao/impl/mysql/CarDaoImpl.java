package dao.impl.mysql;

import dao.interfaces.CarDao;
import entity.car.Car;
import appException.dao.AppSqlException;
import entity.car.CarBuilder;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class CarDaoImpl extends AbstractDao implements CarDao{
    private static final Logger logger = Logger.getLogger(CarDaoImpl.class);
    private static final String addCar = "INSERT INTO cars(number, idDriver, color, model, status) values(?, ?, ?, ?, ?)";
    private static final String deleteCar = "DELETE FROM cars WHERE id = ?";
    private static final String updateStatus = "UPDATE cars SET status = ? WHERE id = ?";
    private static final String getCarById = "SELECT c.id, c.number, c.idDriver, cc.color, mc.model, c.status FROM cars AS c LEFT JOIN colorCar cc on c.color = cc.id LEFT JOIN modelCar mc ON c.model = mc.id WHERE c.id = ?";
    private static final String getCarByDriver = "SELECT c.id, c.number, c.idDriver, cc.color, mc.model, c.status FROM cars AS c LEFT JOIN colorCar cc on c.color = cc.id LEFT JOIN modelCar mc ON c.model = mc.id WHERE c.idDriver = ?";
    private static final String  getListCarByStatus= "SELECT c.id, c.number, c.idDriver, cc.color, mc.model, c.status FROM cars AS c LEFT JOIN colorCar cc on c.color = cc.id LEFT JOIN modelCar mc ON c.model = mc.id WHERE c.status = ?";
    private static final String getColorId = "SELECT id FROM colorCar WHERE color = ?";
    private static final String addColor = "INSERT INTO colorCar(color) VALUE (?)";
    private static final String getModelId = "SELECT id FROM modelCar WHERE model = ?";
    private static final String addModel = "INSERT INTO modelCar(model) VALUE (?)";

    CarDaoImpl(MysqlDaoFactory daoFactory){
        super(daoFactory);
    }

    @Override
    public long addCar(Car car) throws SQLException, AppSqlException{
        return addEntity(car, addCar, "new car didn't create ");
    }
    @Override
    public void deleteCar(long idCar) throws SQLException, AppSqlException{
        deleteById(idCar, deleteCar, "car didn't delete");
    }
    @Override
    public void updateStatus(long idCar, boolean status) throws SQLException, AppSqlException{
        updateOneColumnById(idCar, status, updateStatus, "car status didn't update");
    }
    @Override
    public Car getCarById(long idCar) throws SQLException, AppSqlException{
        List list = getEntityByOneValue(idCar, getCarById, "car by id didn't found ");
        return (Car)list.get(0);
    }
    @Override
    public Car getCarByDriver(long idDriver) throws SQLException, AppSqlException{
        List list = getEntityByOneValue(idDriver, getCarByDriver, "car by driver didn't found ");
        return (Car)list.get(0);
    }
    @Override
    public List<Car> getListCarsByStatus(boolean status) throws SQLException, AppSqlException{
       return getEntityByOneValue(status, getListCarByStatus, "car by status did'n't found ");
    }

    private int getIdColorOrModel(String sqlSelect, String sqlInsert, String value, Connection con) throws SQLException, AppSqlException {
        int id = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(sqlSelect);
            ps.setString(1, value);

            rs = ps.executeQuery();

            if (rs.next()){
                id = rs.getInt(1);
            } else {
                id = addColorOrModel(sqlInsert, value, con);
            }

            throwAppSqlException(id == 0, "not found id color or model");

            return id;

        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
        }
    }
    private int addColorOrModel(String sqlInsert, String value, Connection con) throws SQLException, AppSqlException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, value);
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            throwAppSqlException( !rs.next() , "not added new color or model");

            return rs.getInt(1);

        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
        }
    }


    @Override
    PreparedStatement getPreparedStatementForAddEntity(Connection con, PreparedStatement ps, String sqlInsert, Object entity) throws SQLException, AppSqlException {
        Car car = (Car)entity;
        ps = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, car.getNumber());
        ps.setLong(2, car.getIdDriver());
        int color = getIdColorOrModel(getColorId, addColor, car.getColor(), con);
        ps.setInt(3, color);
        int model = getIdColorOrModel(getModelId, addModel, car.getModel(), con);
        ps.setInt(4, model);
        ps.setBoolean(5, car.getStatus());
        return ps;
    }
    @Override
    List handleResultSet(ResultSet rs) throws SQLException{
        List<Car> carList = new ArrayList<>();
        Car car = null;
        long id = 0;
        long idDriver = 0;
        String number = null;
        String color = null;
        String model = null;
        boolean status = false;
        do{
            id = rs.getLong("id");
            idDriver = rs.getLong("idDriver");
            number = rs.getString("number");
            color = rs.getString("color");
            model = rs.getString("model");
            status = rs.getBoolean("status");
            car = CarBuilder.createCar().setId(id).setIdDriver(idDriver).setNumber(number).setColor(color).setModel(model).setStatus(status).getCar();
            carList.add(car);
        } while (rs.next());
        return carList;
    }
}
