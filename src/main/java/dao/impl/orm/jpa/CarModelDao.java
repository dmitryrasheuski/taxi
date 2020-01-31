package dao.impl.orm.jpa;

import entity.car.CarModel;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

class CarModelDao extends AbstractDao<CarModel> implements dao.interfaces.CarModelDao {
    private static final String getByTitle = "SELECT cm FROM CarModel cm WHERE cm.title = :title";

    CarModelDao(JpaDaoFactory daoFactory) {
        super(daoFactory);
    }

    @Override
    public Optional<Integer> addCarModel(CarModel model) throws SQLException {
        return addEntity(model)
                .map(CarModel::getId);
    }
    @Override
    public Optional<CarModel> getCarModel(String title) throws SQLException {
        Map<String, Object> parameter = new LinkedHashMap<>(1);
        parameter.put("title", title);

        List<CarModel> list = getEntities(getByTitle, parameter);

        return list.isEmpty() ? Optional.empty() : Optional.of( list.get(0) );
    }
    @Override
    public CarModel getOrElseAddAndGet(String title) throws SQLException {
        Optional<CarModel> res = Optional.empty();

        res = getCarModel(title);
        if ( ! res.isPresent() ) {
            res = addCarModel(new CarModel(title)).map( (id) -> new CarModel(id, title) );
        }

        return res.get();
    }
    @Override
    public Optional<CarModel> getById(int id) throws SQLException {
        return getEntity(CarModel.class, (long)id);
    }
}
