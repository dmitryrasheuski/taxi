package service.impl;

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
            user = userDao.getByPhone(phone).orElseThrow(NullPointerException::new);
        } catch (SQLException |NullPointerException ex) {
            logger.info("login(int phone, String password) catch(SQLException | NullPointerException){} : userDao.getByPhone(int) throw exception");
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
            dbUser = userDao.getByPhone(newUser.getPhone()).orElseThrow(NullPointerException::new);
        } catch (SQLException | NullPointerException ex) {
            logger.info("ignore exception", ex);
        }

        long id = 0;
        //new user
        if(dbUser == null){
            try {
                id = userDao.addUser(newUser).orElseThrow(NullPointerException::new);
            } catch (SQLException | NullPointerException ex) {
                logger.info("registry(User newUser) catch(SQLException | NullPointerException){} : userDao.addUser throw exception");
                throw new AppServiceException(ex);
            }
        //The database have user, but user isn"t register
        } else if ((dbUser.getName()==null) && (dbUser.getSurname()==null) && (dbUser.getPassword()==null)){
            id = dbUser.getId();
            try {
                userDao.updateName(id, newUser.getName()).orElseThrow(NullPointerException::new);
                userDao.updateSurname(id, newUser.getSurname()).orElseThrow(NullPointerException::new);
                userDao.updatePassword(id, newUser.getPassword()).orElseThrow(NullPointerException::new);
            } catch (SQLException | NullPointerException ex) {
                logger.info("registry(User newUser) catch(SQLException){} : userDao.update throw exception");
                throw new AppServiceException(ex);
            }
        //The database user with this name and surname
        } else {
            logger.info("new user didn't registry because user with this phone have name, surname and password");
            throw new AppServiceException("new user didn't registry");
        }

        newUser.setId(id);
        logger.debug("finish registry(User newUser)");
        return newUser;
    }
}
