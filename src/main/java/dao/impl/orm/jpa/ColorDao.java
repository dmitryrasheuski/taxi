package dao.impl.orm.jpa;

import entity.car.Color;
import entity.order.Order;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

class ColorDao extends AbstractDao<Color> implements dao.interfaces.ColorDao {
    private static final String getByTitle = "SELECT c FROM Color c WHERE c.title = :title";

    ColorDao(JpaDaoFactory daoFactory) {
        super(daoFactory);
    }

    @Override
    public Optional<Long> addColor(String title) throws SQLException {

        Optional<Color> entity = addEntity(new Color(title));

        return entity.map( Color::getId );
    }
    @Override
    public Optional<Color> getByTitle(String title) throws SQLException {
        Map<String, Object> parameters = new LinkedHashMap<>(1);
        parameters.put("title", title);

        List<Color> list = getEntities(getByTitle, parameters);

        return list.isEmpty() ? Optional.empty() : Optional.of( list.get(0) );
    }
    @Override
    public Color getOrElseAddAndGet(String title) throws SQLException {

        Optional<Color> res = getByTitle(title);

        if ( ! res.isPresent() ) {
            res = addColor(title)
                    .map( (id) -> new Color(id, title) );
        }

        return res.get();
    }
    @Override
    public Optional<Color> getById(long id) throws SQLException {
        return getEntity(Color.class, (long) id);
    }
    @Override
    public boolean remove(Color color) throws SQLException {
        if (color == null) return false;

        return removeEntity(Color.class, (long)color.getId());
    }
}
