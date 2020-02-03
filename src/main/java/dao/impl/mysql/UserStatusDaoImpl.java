package dao.impl.mysql;

import entity.user.UserStatus;
import lombok.extern.log4j.Log4j;

import java.sql.*;
import java.util.*;

@Log4j
public class UserStatusDaoImpl extends AbstractDao implements dao.interfaces.UserStatusDao {
    private static final String addUserStatus = "INSERT INTO user_status(title) VALUE (?)";
    private static final String getUserStatusId = "SELECT * FROM user_status WHERE title = ?";
    private static final String getList = "SELECT * FROM user_status;";

    UserStatusDaoImpl(MysqlDaoFactory factory){
        super(factory);
    }

    @Override
    public Optional<Long> mergeUserStatus(UserStatus status) throws SQLException {
        Long id;
        List list;
        List<Long> idList;
        Object[] parameters = new Object[] { status.getTitle() };

        list = (List<List>) getEntity(parameters, getUserStatusId).orElse(new ArrayList());

        if ( list.isEmpty() ) {
            id = (Long) addEntity(status, addUserStatus).orElse(new ArrayList());

        } else {
            idList = (List<Long>) list.get(0);
            id = idList.get(0);
        }

        return Optional.ofNullable(id);
    }

    @Override
    public Map<Long, String> getMap() throws SQLException {
        Map<Long, String> map = new LinkedHashMap<>();

        List list = (List) getEntity(new Object[0], getList).orElse(new ArrayList());
        if ( list.isEmpty() ) throw new NullPointerException();

        List<Long> idList = (List<Long>) list.get(0);
        List<String> titleList = (List<String>) list.get(1);

        for (int i = 0 ; i < idList.size() ; i++) {
            map.put( idList.get(i) , titleList.get(i) );
        }

        return map;
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
    Optional<List<List>> handleResultSet(ResultSet rs) throws SQLException {
        if(!rs.next()) return Optional.empty();

        List list = new ArrayList();
        List<String> titleList = new ArrayList<>();
        List<Long> idList = new ArrayList();

        do {
            idList.add( rs.getLong("id") );
            titleList.add( rs.getString("title") );
        } while (rs.next());

        list.add(0, idList);
        list.add(1, titleList);

        return Optional.of(list);
    }
}
