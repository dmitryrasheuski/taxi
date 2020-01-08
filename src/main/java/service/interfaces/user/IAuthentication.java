package service.interfaces.user;

import entity.user.User;

import java.util.Optional;

public interface IAuthentication {
    /**
     This method find the user in the database by phone.
     If user was found then the password is verified.
     Return Optional with database user if the verification of password return true, otherwise return empty Optional.

     @return    Return Optional with database user if the verification of password return true, otherwise return empty Optional.
     */
    Optional<User> authentication(User user);
}
