package service.interfaces;

import appException.dao.AppSqlException;
import appException.service.AppServiceException;
import entity.user.User;

import java.sql.SQLException;

public interface IRegistryUser {
    User registryUser(User user) throws AppServiceException;
}
