package dao.impl.mysql;

import dao.interfaces.CarModelDao;
import entity.car.CarModel;
import lombok.extern.log4j.Log4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j
public class CarModelDaoImpl extends AbstractDao<CarModel> implements CarModelDao {
    private static final String addCarModel = "INSERT INTO car_model(title) VALUE (?);";
    private static final String getCarModelByTitle = "SELECT id, title FROM car_model WHERE title = ?;";
    private static final String getCarModelById = "SELECT id, title FROM car_model WHERE id = ?;";

    CarModelDaoImpl (MysqlDaoFactory factory) {
        super(factory);
    }

    @Override
    public Optional<Integer> addCarModel(CarModel model) throws SQLException {
        return addEntity(model, addCarModel).map(Long::intValue);
    }
    @Override
    public Optional<CarModel> getCarModel(String title) throws SQLException {
        return getEntityByOneValue(title, getCarModelByTitle).map((list) -> list.get(0));
    }
    @Override
    public Optional<CarModel> getOrElseAddAndGetId(String title) throws SQLException {
        Optional<CarModel> model = getCarModel(title);
        if(!model.isPresent()){
            model = addCarModel(new CarModel(title))
                    .map((id) -> new CarModel(id, title));
        }
        return model;
    }

    @Override
    PreparedStatement getPreparedStatementForAddEntity(Connection con, PreparedStatement ps, String sqlInsert, CarModel entity) throws SQLException {
       ps = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
       ps.setString(1, entity.getTitle());
       return ps;
    }
    @Override
    Optional<List<CarModel>> handleResultSet(ResultSet rs) throws SQLException {
        if (!rs.next()){
            return Optional.empty();
        }

        List<CarModel> list = new ArrayList<>();
        CarModel model;
        int id;
        String title;
        do{
            id = rs.getInt("id");
            title = rs.getString("title");
            model = new CarModel( id,title);
            list.add(model);
        } while (rs.next());

        return Optional.of(list);
    }
}
