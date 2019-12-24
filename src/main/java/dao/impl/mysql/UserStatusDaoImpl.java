package dao.impl.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserStatusDaoImpl extends AbstractDao implements dao.interfaces.UserStatusDao {
    private static final String getStatusId = "SELECT id FROM userStatus WHERE status = ?";

    UserStatusDaoImpl(MysqlDaoFactory factory){
        super(factory);
    }

    @Override
    public Optional<Integer> getUserStatusId(String title) {
        try {
            return getEntityByOneValue(title, getStatusId)
                    .flatMap(list -> Optional.of((Integer) list.get(0)));
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    PreparedStatement getPreparedStatementForAddEntity(Connection con, PreparedStatement ps, String sqlInsert, Object entity) throws SQLException {
        return null;
    }
    @Override
    Optional<List<Integer>> handleResultSet(ResultSet rs) throws SQLException {
        if(!rs.next()){
            return Optional.empty();
        }

        List<Integer> list = new ArrayList<>(1);
        list.add(rs.getInt("id"));
        return Optional.of(list);
    }
}
