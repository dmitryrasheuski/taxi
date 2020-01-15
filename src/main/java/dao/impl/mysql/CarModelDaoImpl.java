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
        Object[] parameters = new Object[] {title};

        return getEntity(parameters, getCarModelByTitle).map((list) -> list.get(0));

    }
    @Override
    public Optional<CarModel> getOrElseAddAndGetId(String title) throws SQLException {
        Optional<CarModel> model;

        model = getCarModel(title);
        if( !model.isPresent() ) model = addCarModel(new CarModel(title)).map((id) -> new CarModel(id, title));

        return model;

    }
    @Override
    public Optional<CarModel> getById(int id) throws SQLException {
        Object[] parameters = new Object[] {id};

        return getEntity(parameters, getCarModelById).map((list) -> list.get(0));

    }

    @Override
    PreparedStatement getPreparedStatementForAddEntity(String sqlInsert, CarModel entity) throws SQLException {
        PreparedStatement ps;

        ps = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, entity.getTitle());

        return ps;
    }
    @Override
    Optional<List<CarModel>> handleResultSet(ResultSet rs) throws SQLException {
        if ( ! rs.next() ) return Optional.empty();

        List<CarModel> list = new ArrayList<>();
        int id;
        String title;
        do{
            id = rs.getInt("id");
            title = rs.getString("title");
            list.add( new CarModel(id, title));
        } while (rs.next());

        return Optional.of(list);

    }

}
