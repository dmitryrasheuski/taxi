package service.interfaces.user;

import entity.user.User;

import java.util.Optional;

public interface IUserAuthenticating {
    /**
     This method find the user in the database by phone.
     If user was found then the password is verified.
     Return Optional with database user if the verification of password return true, otherwise return empty Optional.

     @param user this parameter must have got the not-null fields: phone, password
     @return    an Optional describing the database user, if the verification of password return true, otherwise return an empty Optional.
     */
    Optional<User> authentication(User user);
}
