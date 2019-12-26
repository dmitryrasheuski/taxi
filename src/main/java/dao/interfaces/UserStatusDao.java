package dao.interfaces;

import entity.user.UserStatus;

import java.sql.SQLException;
import java.util.Optional;

public interface UserStatusDao {
    Optional<Integer> mergeUserStatus(UserStatus status) throws SQLException;
}
