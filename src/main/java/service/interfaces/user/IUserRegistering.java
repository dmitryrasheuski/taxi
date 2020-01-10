package service.interfaces.user;

import entity.user.User;

import java.util.Optional;

public interface IUserRegistering {
    /**
     * @throws IllegalArgumentException if the 'name', 'surname', 'password', 'phone', 'status' user's fields were not initialized
     * @return      If the registry successful completed, return the current user at which the 'id' field was installed, otherwise return empty Optional.
     */
    Optional<User> register(User user);
}
