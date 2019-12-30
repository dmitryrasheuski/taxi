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

    ColorDaoImpl(MysqlDaoFactory factory) {
        super(factory);
    }

    @Override
    public Optional<Integer> addColor(String title) throws SQLException {
        return addEntity(new Color(title), addColor).map(Long::intValue);
    }
    @Override
    public Optional<Color> getColorByTitle(String title) throws SQLException {
        return getEntityByOneValue(title, getColorByTitle).map((list) -> list.get(0));
    }
    @Override
    public Optional<Integer> getIdOrElseAddAndGet(String title) throws  SQLException {
        Optional<Integer> id = getColorByTitle(title).map(Color::getId);
        if(!id.isPresent()) {
            id = addColor(title);
        }
        return id;
    }
    @Override
    public Optional<Color> getById(int id) throws SQLException {
        return getEntityByOneValue(id, getColorById).map((list -> list.get(0)));
    }

    @Override
    PreparedStatement getPreparedStatementForAddEntity(Connection con, PreparedStatement ps, String sqlInsert, Color entity) throws SQLException {
        ps = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, entity.getTitle());
        return ps;
    }

    @Override
    Optional<List<Color>> handleResultSet(ResultSet rs) throws SQLException {
        if (!rs.next()) {
            return Optional.empty();
        }

        List<Color> list = new ArrayList<>();
        Color color;
        int id;
        String title;
        do{
            id = rs.getInt("id");
            title = rs.getString("title");
            color = new Color(title);
            color.setId(id);
            list.add(color);
        } while (rs.next());

        return Optional.of(list);
    }
}
