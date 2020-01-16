package service.impl.user;

import dao.interfaces.UserDao;
import entity.user.User;
import lombok.extern.log4j.Log4j;
import service.interfaces.user.IUserService;
import service.interfaces.user.IUserAuthenticating;
import service.interfaces.user.IUserRegistering;

import java.sql.SQLException;
import java.util.Optional;

@Log4j
public class UserService implements IUserRegistering, IUserAuthenticating, IUserService {
    private UserDao dao;

    public UserService(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public Optional<User> getUser(User user) {
        if ( user == null || user.getPhone() == null ) return Optional.empty();

        Optional<User> dbUser = Optional.empty();
        try {
            dbUser = dao.getByPhone( user.getPhone() );
        } catch (SQLException ex) {
            log.error("Finding: " + user, ex);
        }

        return dbUser;

    }

    @Override
    public Optional<User> addUser(User user) {
        if ( user == null || user.getPhone() == null ) return Optional.empty();

        Optional<Long> id = Optional.empty();
        try {
            id = dao.addUser(user);
        } catch (SQLException ex) {
            log.error("Adding: " + user, ex);
        }

        if ( ! id.isPresent() ) return Optional.empty();

        user.setId( id.get() );
        return Optional.of(user);

    }

    @Override
    public Optional<User> authentication(User user) {
        Optional<User> dbUser = getUser(user);
        if ( ! dbUser.isPresent() ) return Optional.empty();

        return dbUser.get().getPassword().equals( user.getPassword() ) ?
                dbUser : Optional.empty();
    }

    @Override
    public Optional<User> register(User user) {
        if ( ! validateUser(user) ) return Optional.empty();

        return addUser(user);

    }

    private boolean validateUser(User user) {
        if ( user == null ) return false;
        if ( user.getName() == null ) return false;
        if ( user.getSurname() == null ) return false;
        if ( user.getPhone() == null ) return false;
        if ( user.getPassword() == null ) return false;
        if ( user.getStatus() == null ) return false;

        return true;

    }
}
