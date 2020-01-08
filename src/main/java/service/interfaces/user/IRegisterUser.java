package service.interfaces.user;

import entity.user.User;

import java.util.Optional;

public interface IRegisterUser {
    /**
     * @return      If the registry successful completed, return the current user at which the 'id' field was installed, otherwise return empty Optional.
     */
    Optional<User> register(User user);
}
