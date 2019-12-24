package dao.interfaces;

import entity.user.UserStatus;

import java.util.Optional;

public interface UserStatusDao {
    Optional<Integer> getUserStatusId(String title);
}
