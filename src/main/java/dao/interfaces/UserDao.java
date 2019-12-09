package dao.interfaces;

import entity.user.User;

import java.sql.SQLException;
import java.util.Optional;

public interface UserDao {

    Optional<Long> addUser(User user) throws SQLException;
    Optional<Integer> deleteUser(long id) throws SQLException;
    Optional<Integer> updatePhone(long id, int phone) throws SQLException;
    Optional<Integer> updateName(long id, String name) throws SQLException;
    Optional<Integer> updateSurname(long id, String surname) throws SQLException;
    Optional<Integer> updatePassword(long id, String password) throws SQLException;
    Optional<Integer> updateStatus(long id, String status) throws SQLException;
    Optional<User> getById(long id) throws SQLException;
    Optional<User> getByPhone(int phone) throws SQLException;
}
