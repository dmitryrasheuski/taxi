package dao.impl.orm.jpa;

import entity.user.User;
import entity.user.UserStatus;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.Optional;

public class UserDao implements dao.interfaces.UserDao {
    private JpaDaoFactory daoFactory;
    private EntityManager entityManager;

    public UserDao(JpaDaoFactory daoFactory) {
        this.daoFactory = daoFactory;
        this.entityManager = daoFactory.getEntityManager();
    }

    @Override
    public Optional<Long> addUser(User user) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Optional<Integer> deleteUser(long id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Optional<Integer> updatePhone(long id, int phone) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Optional<Integer> updateName(long id, String name) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Optional<Integer> updateSurname(long id, String surname) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Optional<Integer> updatePassword(long id, String password) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Optional<Integer> updateStatus(long id, UserStatus status) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Optional<User> getById(long id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Optional<User> getByPhone(int phone) throws SQLException {
        return Optional.empty();
    }
}
