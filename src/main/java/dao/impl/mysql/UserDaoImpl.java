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
    public Optional<Integer> deleteUser(long id) throws SQLException, AppSqlException {
        deleteById(id, deleteUser,  "user isn't found");
    }
    @Override
    public Optional<Integer> updatePhone(long id, int phone) throws SQLException, AppSqlException {
        updateOneColumnById(id, phone, updatePhone, "phone isn't updated");
    }
    @Override
    public Optional<Integer> updateName(long id, String name) throws SQLException, AppSqlException{
        updateOneColumnById(id, name, updateName, "name isn't updated");
    }
    @Override
    public Optional<Integer> updateSurname(long id, String surname) throws SQLException, AppSqlException{
        updateOneColumnById(id, surname, updateSurname, "surname isn't updated");
    }
    @Override
    public Optional<Integer> updatePassword(long id, String password) throws SQLException, AppSqlException {
        updateOneColumnById(id, password, updatePassword, "password isn't updated");
    }
    @Override
    public Optional<Integer> updateStatus(long id, String status) throws SQLException, AppSqlException {
       int idStatus = getStatusId(status);
       updateOneColumnById(id, idStatus, updateStatus, "status isn't updated");
    }
    @Override
    public Optional<User> getById(long id) throws SQLException, AppSqlException{
        List list = getEntityByOneValue(id, getUserById, "didn't find the user by id");
        return (User)list.get(0);
    }
    @Override
    public Optional<User> getByPhone(int phone) throws SQLException, AppSqlException{
        List list = getEntityByOneValue(phone, getUserByPhone, "didn't find the user by phone");
        return (User)list.get(0);
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
    List handleResultSet(ResultSet rs) throws SQLException {
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

        return list;
    }
}
