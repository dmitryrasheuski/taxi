package dao.impl.mysql;

import dao.interfaces.UserDao;
import entity.user.User;
import entity.user.UserStatus;
import entity.user.UserStatusType;
import lombok.extern.log4j.Log4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j
class UserDaoImpl extends AbstractDao<User> implements UserDao {
    private static final String saveUser = "INSERT INTO users (phone, name, surname, password, status_id) VALUES (?, ?, ?, ?, ?)";
    private static final String deleteUser = "DELETE FROM users WHERE id = ?";
    private static final String updatePhone = "UPDATE users SET phone = ? WHERE id = ?";
    private static final String updateName = "UPDATE users SET name = ? WHERE id = ?";
    private static final String updateSurname = "UPDATE users SET surname = ? WHERE id = ?";
    private static final String updatePassword = "UPDATE users SET password = ? WHERE id = ?";
    private static final String updateStatus = "UPDATE users SET status_id = ? WHERE id = ?";
    private static final String getUserById = "SELECT id, phone, name, surname, password, status_id FROM users WHERE id = ?";
    private static final String getUserByPhone = "SELECT id, phone, name, surname, password, status_id FROM users WHERE phone = ?";

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
    public Optional<Integer> updateStatus(long id, UserStatus status) throws SQLException {
        return updateOneColumnById(id, status.getId(), updateStatus);
    }
    @Override
    public Optional<User> getById(long id) throws SQLException {
        return getEntityByOneValue(id, getUserById)
                .map((list) -> list.get(0));
    }
    @Override
    public Optional<User> getByPhone(int phone) throws SQLException{
        return getEntityByOneValue(phone, getUserByPhone)
                .map((list) -> list.get(0));
    }


    @Override
    PreparedStatement getPreparedStatementForAddEntity(Connection con, PreparedStatement ps, String sqlInsert, User entity)  throws SQLException{
        ps = con.prepareStatement(saveUser, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, entity.getPhone());
        ps.setString(2, entity.getName());
        ps.setString(3, entity.getSurname());
        ps.setString(4, entity.getPassword());
        ps.setInt(5, entity.getStatus().getId());
        return ps;
    }
    @Override
    Optional<List<User>> handleResultSet(ResultSet rs) throws SQLException {
        if (!rs.next()) {
            return Optional.empty();
        }

        List<User> list = new ArrayList<>();
        User user;
        long id;
        int phone;
        String name;
        String surname;
        String password;
        int statusId;
        UserStatus status;
        do{
            id = rs.getLong("id");
            phone = rs.getInt("phone");
            name = rs.getString("name");
            surname = rs.getString("surname");
            password = rs.getString("password");
            statusId = rs.getInt("status_id");
            status = UserStatusType.getStatus(statusId)
                    .orElseThrow(() -> new IllegalStateException("The value from column 'status_id' in table 'users' doesn't equals the value from 'status.getId()' in 'UserStatusType' class'"));
            user = User.builder()
                    .id(id)
                    .phone(phone)
                    .name(name)
                    .surname(surname)
                    .password(password)
                    .status(status)
                    .build();
            list.add(user);
        } while (rs.next());

        return Optional.of(list);
    }
}
