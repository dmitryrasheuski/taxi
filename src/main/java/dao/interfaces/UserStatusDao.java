package dao.interfaces;

import entity.user.UserStatus;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserStatusDao {
    Optional<Long> mergeUserStatus(UserStatus status) throws SQLException;
    Map<Long, String> getMap() throws SQLException;
}
