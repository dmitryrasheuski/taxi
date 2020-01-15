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
        Object[] parameters = new Object[] {id};

        return deleteOrUpdateEntity(parameters, deleteUser);

    }
    @Override
    public Optional<Integer> updatePhone(long id, int phone) throws SQLException {
        Object[] parameters = new Object[] {phone, id};

        return deleteOrUpdateEntity(parameters, updatePhone);

    }
    @Override
    public Optional<Integer> updateName(long id, String name) throws SQLException {
        Object[] parameters = new Object[] {name, id};

        return deleteOrUpdateEntity(parameters, updateName);

    }
    @Override
    public Optional<Integer> updateSurname(long id, String surname) throws SQLException {
        Object[] parameters = new Object[] {surname, id};

        return deleteOrUpdateEntity(parameters, updateSurname);
    }
    @Override
    public Optional<Integer> updatePassword(long id, String password) throws SQLException {
        Object[] parameters = new Object[] {password, id};

        return deleteOrUpdateEntity(parameters, updatePassword);

    }
    @Override
    public Optional<Integer> updateStatus(long id, UserStatus status) throws SQLException {
        Object[] parameters = new Object[] {status.getId(), id};

        return deleteOrUpdateEntity(parameters, updateStatus);

    }
    @Override
    public Optional<User> getById(long id) throws SQLException {
        Object[] parameters = new Object[] {id};

        return getEntity(parameters, getUserById).map((list) -> list.get(0));

    }
    @Override
    public Optional<User> getByPhone(int phone) throws SQLException{
        Object[] parameters = new Object[] {phone};

        return getEntity(parameters, getUserByPhone).map((list) -> list.get(0));

    }


    @Override
    PreparedStatement getPreparedStatementForAddEntity(String sqlInsert, User entity)  throws SQLException{
        PreparedStatement ps = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);

        ps.setInt(1, entity.getPhone());
        ps.setString(2, entity.getName());
        ps.setString(3, entity.getSurname());
        ps.setString(4, entity.getPassword());
        ps.setInt(5, entity.getStatus().getId());

        return ps;

    }
    @Override
    Optional<List<User>> handleResultSet(ResultSet rs) throws SQLException {
        if ( ! rs.next() ) return Optional.empty();

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
