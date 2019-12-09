package dao.impl.mysql;

import appException.dao.AppSqlException;
import dao.interfaces.UserDao;
import entity.user.User;
import entity.user.UserBuilder;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class UserDaoImpl extends AbstractDao implements UserDao {
    private static final Logger logger = Logger.getLogger(UserDaoImpl.class);
    private static final String saveUser = "INSERT INTO users (phone, name, surname, password, status) VALUES (?, ?, ?, ?, ?)";
    private static final String deleteUser = "DELETE FROM users WHERE id = ?";
    private static final String updatePhone = "UPDATE users SET phone = ? WHERE id = ?";
    private static final String updateName = "UPDATE users SET name = ? WHERE id = ?";
    private static final String updateSurname = "UPDATE users SET surname = ? WHERE id = ?";
    private static final String updatePassword = "UPDATE users SET password = ? WHERE id = ?";
    private static final String updateStatus = "UPDATE users SET status = ? WHERE id = ?";
    private static final String getUserById = "SELECT u.id, u.phone, u.name, u.surname, u.password, s.status FROM users AS u LEFT JOIN userStatus AS s ON u.status = s.id WHERE u.id = ?";
    private static final String getUserByPhone = "SELECT u.id, u.phone, u.name, u.surname, u.password, s.status FROM users AS u LEFT JOIN userStatus AS s ON u.status = s.id WHERE u.phone = ?";
    private static final String getStatusId = "SELECT id FROM userStatus WHERE status = ?";

    UserDaoImpl(MysqlDaoFactory daoFactory) {
        super(daoFactory);
    }

    @Override
    public Optional<Long> addUser(User user) throws SQLException{
        return addEntity(user, saveUser);
    }
    @Override
    public Optional<Integer> deleteUser(long id) throws SQLException {
        return deleteById(id, deleteUser);
    }
    @Override
    public Optional<Integer> updatePhone(long id, int phone) throws SQLException {
        return updateOneColumnById(id, phone, updatePhone);
    }
    @Override
    public Optional<Integer> updateName(long id, String name) throws SQLException {
        return updateOneColumnById(id, name, updateName);
    }
    @Override
    public Optional<Integer> updateSurname(long id, String surname) throws SQLException {
        return updateOneColumnById(id, surname, updateSurname);
    }
    @Override
    public Optional<Integer> updatePassword(long id, String password) throws SQLException {
        return updateOneColumnById(id, password, updatePassword);
    }
    @Override
    public Optional<Integer> updateStatus(long id, String status) throws SQLException {
        Connection con = getConnection();
        int idStatus = 0;

        try {
            idStatus = getStatusId(status, con).orElseThrow(() -> new IllegalArgumentException("user status was didn't found"));
        } finally {
            close(con, new Exception());
        }

        return updateOneColumnById(id, idStatus, updateStatus);
    }
    @Override
    public Optional<User> getById(long id) throws SQLException {
        return getEntityByOneValue(id, getUserById)
                .flatMap((list) -> Optional.of((User)list.get(0)));
    }
    @Override
    public Optional<User> getByPhone(int phone) throws SQLException{
        return getEntityByOneValue(phone, getUserByPhone)
                .flatMap((list) -> Optional.of((User)list.get(0)));
    }

    private Optional<Integer> getStatusId(String status, Connection con) throws SQLException{
        if (status == null || status.isEmpty()) {
            status = "passenger";
        }
        PreparedStatement ps = null;
        ResultSet rs = null;
        Exception exception = null;
        try {
            ps = con.prepareStatement(getStatusId);
            ps.setString(1, status);

            rs = ps.executeQuery();

            if(rs.next()) {
                return Optional.of(rs.getInt("id"));
            } else {
                return Optional.empty();
            }

        } catch (Exception ex) {
            exception = ex;
            throw ex
                    ;
        } finally {
            close(rs, exception);
            close(ps, exception);
        }
    }

    @Override
    PreparedStatement getPreparedStatementForAddEntity(Connection con, PreparedStatement ps, String sqlInsert, Object entity)  throws SQLException{
        User user = (User) entity;
        int statusId = getStatusId(user.getStatus(), con).orElseThrow(() -> new IllegalArgumentException("user status was didn't found"));

        ps = con.prepareStatement(saveUser, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, user.getPhone());
        ps.setString(2, user.getName());
        ps.setString(3, user.getSurname());
        ps.setString(4, user.getPassword());
        ps.setInt(5, statusId);
        return ps;
    }
    @Override
    Optional<List<User>> handleResultSet(ResultSet rs) throws SQLException {
        if (!rs.next()) {
            return Optional.empty();
        }

        List<User> list = new ArrayList<>();
        User user = null;
        long id = 0;
        int phone = 0;
        String name = null;
        String surname = null;
        String password = null;
        String status = null;
        do{
            id = rs.getLong("id");
            phone = rs.getInt("phone");
            name = rs.getString("name");
            surname = rs.getString("surname");
            password = rs.getString("password");
            status = rs.getString("status");
            user = UserBuilder.createUser().setId(id).setPhone(phone).setName(name).setSurname(surname).setPassword(password).setStatus(status).getUser();
            list.add(user);
        } while (rs.next());

        return Optional.of(list);
    }
}
