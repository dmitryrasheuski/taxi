package service.impl;

import appException.dao.AppSqlException;
import appException.service.AppServiceException;
import dao.interfaces.DaoFactory;
import dao.interfaces.UserDao;
import entity.user.User;
import org.apache.log4j.Logger;
import service.interfaces.ILogin;
import service.interfaces.IRegistryUser;

import java.sql.SQLException;

public class UserService implements ILogin, IRegistryUser {
    private static final Logger logger = Logger.getLogger(UserService.class);
    private DaoFactory daoFactory = DaoFactory.getFactory(DaoFactory.TypesDatabases.MY_SQL);
    private UserDao userDao = daoFactory.getUserDao();

    @Override
    public User login(int phone, String password) throws AppServiceException {
        logger.debug("start login(int phone, String password)");

        User user = null;
        try {
            user = userDao.getByPhone(phone);
        } catch (SQLException | AppSqlException ex) {
            logger.info("login(int phone, String password) catch(SQLException | AppSqlException){} : userDao.getByPhone(int) throw exception");
            throw new AppServiceException(ex);
        }
        if (!user.getPassword().equals(password)) {
            logger.info("login(int phone, String password) !password.equals(SqlPassword) - throw new AppServiceException");
            throw new AppServiceException("phone or password is incorrect");
        }

        logger.debug("finish login(int phone, String password)");
        return user;
    }
    @Override
    public User registryUser(User newUser) throws AppServiceException {
        logger.debug("start registry(User newUser)");

        User dbUser = null;
        try {
            dbUser = userDao.getByPhone(newUser.getPhone());
        } catch (AppSqlException | SQLException ex) {
            logger.info("ignore exception", ex);
        }

        long id = 0;
        if(dbUser == null){
            try {
                id = userDao.addUser(newUser);
            } catch (SQLException |AppSqlException ex) {
                logger.info("registry(User newUser) catch(SQLException | AppSqlException){} : userDao.addUser throw exception");
                throw new AppServiceException(ex);
            }
        } else if ((dbUser.getName()==null) && (dbUser.getSurname()==null) && (dbUser.getPassword()==null)){
            id = dbUser.getId();
            try {
                userDao.updateName(id, newUser.getName());
                userDao.updateSurname(id, newUser.getSurname());
                userDao.updatePassword(id, newUser.getPassword());
            } catch (SQLException |AppSqlException ex) {
                logger.info("registry(User newUser) catch(SQLException | AppSqlException){} : userDao.update throw exception");
                throw new AppServiceException(ex);
            }
        } else {
            logger.info("new user didn't registry because user with this phone have name, surname and password");
            throw new AppServiceException("new user didn't registry");
        }

        newUser.setId(id);
        logger.debug("finish registry(User newUser)");
        return newUser;

    }

}
