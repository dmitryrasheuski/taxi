package dao.interfaces;

import appException.dao.AppSqlException;
import entity.user.User;

import java.sql.SQLException;

public interface UserDao {

    long addUser(User user) throws SQLException, AppSqlException;
    void deleteUser(long id) throws SQLException, AppSqlException;
    void updatePhone(long id, int phone) throws SQLException, AppSqlException;
    void updateName(long id, String name) throws SQLException, AppSqlException;
    void updateSurname(long id, String surname) throws SQLException, AppSqlException;
    void updatePassword(long id, String password) throws SQLException, AppSqlException;
    void updateStatus(long id, String status) throws SQLException, AppSqlException;
    User getById(long id) throws SQLException, AppSqlException;
    User getByPhone(int phone) throws SQLException, AppSqlException;
}
