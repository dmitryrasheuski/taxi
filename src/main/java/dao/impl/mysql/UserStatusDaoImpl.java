package dao.impl.mysql;

import entity.user.UserStatus;
import lombok.extern.log4j.Log4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j
public class UserStatusDaoImpl extends AbstractDao implements dao.interfaces.UserStatusDao {
    private static final String addUserStatus = "INSERT INTO user_status(title) VALUE (?)";
    private static final String getUserStatusId = "SELECT id FROM user_status WHERE title = ?";

    UserStatusDaoImpl(MysqlDaoFactory factory){
        super(factory);
    }

    @Override
    public Optional<Integer> mergeUserStatus(UserStatus status) throws SQLException {
        Optional<Integer> id;
        Object[] parameters = new Object[] { status.getTitle() };

        id = getEntity(parameters, getUserStatusId).map( (list) -> ((List<Integer>)list).get(0) );
        if( ! id.isPresent() ) id = addEntity(status, addUserStatus).map( (idLong) -> ((Long)idLong).intValue() );

        return id;

    }

    @Override
    PreparedStatement getPreparedStatementForAddEntity(String sqlInsert, Object entity) throws SQLException {
        PreparedStatement ps;
        UserStatus status = (UserStatus) entity;

        ps = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, status.getTitle());

        return ps;

    }
    @Override
    Optional<List<Integer>> handleResultSet(ResultSet rs) throws SQLException {
        if(!rs.next()) return Optional.empty();

        List<Integer> list = new ArrayList<>(1);
        list.add( rs.getInt("id" ));

        return Optional.of(list);
    }
}
