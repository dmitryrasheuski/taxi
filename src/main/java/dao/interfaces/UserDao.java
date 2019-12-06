package dao.interfaces;

import appException.dao.AppSqlException;
import entity.user.User;

import java.sql.SQLException;
import java.util.Optional;

public interface UserDao {

    Optional<Long> addUser(User user) throws SQLException, AppSqlException;
    Optional<Integer> deleteUser(long id) throws SQLException, AppSqlException;
    Optional<Integer> updatePhone(long id, int phone) throws SQLException, AppSqlException;
    Optional<Integer> updateName(long id, String name) throws SQLException, AppSqlException;
    Optional<Integer> updateSurname(long id, String surname) throws SQLException, AppSqlException;
    Optional<Integer> updatePassword(long id, String password) throws SQLException, AppSqlException;
    Optional<Integer> updateStatus(long id, String status) throws SQLException, AppSqlException;
    Optional<User> getById(long id) throws SQLException, AppSqlException;
    Optional<User> getByPhone(int phone) throws SQLException, AppSqlException;
}
