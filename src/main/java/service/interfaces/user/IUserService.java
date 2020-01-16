package service.interfaces.user;

import entity.user.User;

import java.util.Optional;

public interface IUserService {
    /**
     * @param passenger this parameter must have got the not-null fields: phone
     * @return  the current user at which the 'id' field was installed, if the operation is successful completed, otherwise return an empty Optional
     */
    Optional<User> addUser(User passenger);

    /**
     * @param passenger this parameter must have got the not-null fields: phone
     * @return  a new user from database, if the operation is successful completed, otherwise return an empty Optional
     */
    Optional<User> getUser(User passenger);
}
