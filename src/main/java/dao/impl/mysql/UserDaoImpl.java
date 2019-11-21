package dao.impl.mysql;

import appException.dao.AppSqlException;
import dao.interfaces.UserDao;
import entity.user.User;
import entity.user.UserBuilder;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public long addUser(User user) throws SQLException, AppSqlException {
        return addEntity(user, saveUser, "new user isn't added");
    }
    @Override
    public void deleteUser(long id) throws SQLException, AppSqlException {
        deleteById(id, deleteUser,  "user isn't found");
    }
    @Override
    public void updatePhone(long id, int phone) throws SQLException, AppSqlException {
        updateOneColumnById(id, phone, updatePhone, "phone isn't updated");
    }
    @Override
    public void updateName(long id, String name) throws SQLException, AppSqlException{
        updateOneColumnById(id, name, updateName, "name isn't updated");
    }
    @Override
    public void updateSurname(long id, String surname) throws SQLException, AppSqlException{
        updateOneColumnById(id, surname, updateSurname, "surname isn't updated");
    }
    @Override
    public void updatePassword(long id, String password) throws SQLException, AppSqlException {
        updateOneColumnById(id, password, updatePassword, "password isn't updated");
    }
    @Override
    public void updateStatus(long id, String status) throws SQLException, AppSqlException {
       int idStatus = getStatusId(status);
       updateOneColumnById(id, idStatus, updateStatus, "status isn't updated");
    }
    @Override
    public User getById(long id) throws SQLException, AppSqlException{
        List list = getEntityByOneValue(id, getUserById, "didn't find the user by id");
        return (User)list.get(0);
    }
    @Override
    public User getByPhone(int phone) throws SQLException, AppSqlException{
        List list = getEntityByOneValue(phone, getUserByPhone, "didn't find the user by phone");
        return (User)list.get(0);
    }

    private int getStatusId(String status) throws SQLException, AppSqlException {
        if (status == null || status.isEmpty()) {
            status = "passenger";
        }
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();

            ps = con.prepareStatement(getStatusId);
            ps.setString(1, status);

            rs = ps.executeQuery();
            throwAppSqlException(!rs.next(), "status didn't find");

            return rs.getInt("id");
        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
            closeConnection(con);
        }
    }

    @Override
    PreparedStatement getPreparedStatementForAddEntity(Connection con, PreparedStatement ps, String sqlInsert, Object entity)  throws SQLException, AppSqlException{
        User user = (User) entity;
        ps = con.prepareStatement(saveUser, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, user.getPhone());
        ps.setString(2, user.getName());
        ps.setString(3, user.getSurname());
        ps.setString(4, user.getPassword());
        int statusId = getStatusId(user.getStatus());
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
