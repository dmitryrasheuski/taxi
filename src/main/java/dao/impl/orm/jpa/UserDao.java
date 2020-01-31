package dao.impl.orm.jpa;

import entity.user.User;
import entity.user.UserStatus;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

class UserDao extends AbstractDao<User> implements dao.interfaces.UserDao {
    private static final String getUserByPhone = "SELECT u FROM User u WHERE phone = :phone";

    UserDao(JpaDaoFactory daoFactory) {
        super(daoFactory);
    }

    @Override
    public Optional<Long> addUser(User user) throws SQLException {

        Optional<User> res = addEntity(user);

        return res.map(User::getId);
    }
    @Override
    public Optional<Integer> deleteUser(long id) throws SQLException {

        boolean success = removeEntity(User.class, id);

        return success ? Optional.of(1) : Optional.empty();
    }
    @Override
    public Optional<Integer> updatePhone(long id, int phone) throws SQLException {

        boolean success = updateEntity(
                User.class,
                id,
                (user) -> user.setPhone(phone)
        );

        return success ? Optional.of(1) : Optional.empty();
    }
    @Override
    public Optional<Integer> updateName(long id, String name) throws SQLException {

        boolean success = updateEntity(
                User.class,
                id,
                (user) -> user.setName(name)
        );

        return success ? Optional.of(1) : Optional.empty();
    }
    @Override
    public Optional<Integer> updateSurname(long id, String surname) throws SQLException {

        boolean success = updateEntity(
                User.class,
                id,
                (user) -> user.setSurname(surname)
        );

        return success ? Optional.of(1) : Optional.empty();
    }
    @Override
    public Optional<Integer> updatePassword(long id, String password) throws SQLException {

        boolean success = updateEntity(
                User.class,
                id,
                (user) -> user.setPassword(password)
        );

        return success ? Optional.of(1) : Optional.empty();
    }
    @Override
    public Optional<Integer> updateStatus(long id, UserStatus status) throws SQLException {

        boolean success = updateEntity(
                User.class,
                id,
                (user) -> user.setStatus(status)
        );

        return success ? Optional.of(1) : Optional.empty();
    }
    @Override
    public Optional<User> getById(long id) throws SQLException {
        return getEntity(User.class, id);
    }
    @Override
    public Optional<User> getByPhone(int phone) throws SQLException {
        Map<String, Object> properties = new LinkedHashMap<>(1);
        properties.put("phone", phone);

        List<User> list = getEntities(getUserByPhone, properties);

        return list.isEmpty() ? Optional.empty() : Optional.of( list.get(0) );
    }

}
