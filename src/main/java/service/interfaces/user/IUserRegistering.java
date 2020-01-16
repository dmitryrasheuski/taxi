package service.interfaces.user;

import entity.user.User;

import java.util.Optional;

public interface IUserRegistering {
    /**
     * @param user this parameter must have got the not-null fields: name, surname, phone, password, status
     * @return  an Optional describing the current user at which the 'id' field has been installed, if the registration was successful completed, otherwise return an empty Optional.
     */
    Optional<User> register(User user);
}
