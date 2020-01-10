package service.interfaces.user;

import entity.user.User;

import java.util.Optional;

public interface IPassengerInfoProviding {
    /**
     * This method add to the database only user phone if user.status equals UserStatusType.PASSENGER
     *
     * @param passenger Type of 'User' class at which the 'status' field equals UserStatusType.PASSENGER
     * @return If the operation is successful completed then return the current user at which the 'id' field was installed, otherwise return empty Optional
     */
    Optional<User> providePassengerInfo(User passenger);
}
