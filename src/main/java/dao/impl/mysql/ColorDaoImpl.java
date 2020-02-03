package dao.impl.mysql;

import dao.interfaces.ColorDao;
import entity.car.Color;
import lombok.extern.log4j.Log4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j
public class ColorDaoImpl extends AbstractDao<Color> implements ColorDao {
    private static final String addColor = "INSERT INTO colors(title) VALUE(?);";
    private static final String getColorByTitle = "SELECT id, title FROM colors WHERE title = ?;";
    private static final String getColorById = "SElECT id, title FROM colors WHERE id = ?;";
    private static final String removeColor = "DELETE FROM colors WHERE id = ?;";

    ColorDaoImpl(MysqlDaoFactory factory) {
        super(factory);
    }

    @Override
    public Optional<Long> addColor(String title) throws SQLException {
        return addEntity(new Color(title), addColor).map(Long::longValue);
    }
    @Override
    public Optional<Color> getByTitle(String title) throws SQLException {
        Object[] parameters = new Object[] {title};

        return getEntity(parameters, getColorByTitle).map((list) -> list.get(0));
    }
    @Override
    public Color getOrElseAddAndGet(String title) throws  SQLException {
        Optional<Color> color;

        color = getByTitle(title);
        if( ! color.isPresent() ) {
            color = addColor(title).map( (id) -> new Color(id,title) );
        }

        return color.get();
    }
    @Override
    public Optional<Color> getById(long id) throws SQLException {
        Object[] parameters = new Object[] {id};

        return getEntity(parameters, getColorById).map( (list) -> list.get(0) );
    }
    @Override
    public boolean remove(Color color) throws SQLException {
        if (color == null) return false;

        Object[] parameters = new Object[] {color.getId()};

        return deleteOrUpdateEntity(parameters, removeColor).isPresent();
    }

    @Override
    PreparedStatement getPreparedStatementForAddEntity(String sqlInsert, Color entity) throws SQLException {
        PreparedStatement ps;

        ps = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, entity.getTitle());

        return ps;

    }
    @Override
    Optional<List<Color>> handleResultSet(ResultSet rs) throws SQLException {
        if (!rs.next()) return Optional.empty();

        List<Color> list = new ArrayList<>();
        long id;
        String title;
        do{
            id = rs.getLong("id");
            title = rs.getString("title");
            list.add( new Color(id, title) );
        } while (rs.next());

        return Optional.of(list);
    }

}
