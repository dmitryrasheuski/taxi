package entity.user;

import dao.interfaces.DaoFactory;
import dao.interfaces.UserStatusDao;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

public enum UserStatusType {
    ADMIN (DaoFactory.getFactory(DaoFactory.TypesDatabases.MY_SQL).getUserStatusDao()),
    PASSENGER (DaoFactory.getFactory(DaoFactory.TypesDatabases.MY_SQL).getUserStatusDao()),
    DRIVER (DaoFactory.getFactory(DaoFactory.TypesDatabases.MY_SQL).getUserStatusDao());

    private UserStatus status;

    UserStatusType(UserStatusDao userStatusDao){
        status = new UserStatus();
        status.setTitle(name());
        int id = 0;
        try {
            id = userStatusDao.mergeUserStatus(status)
                    .orElseThrow(() -> new IllegalStateException("UserStatus with title " + "'" + status.getTitle() + "'" + " didn't found in DB"));
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        status.setId(id);
    }

    public UserStatus getStatus() {
        return status;
    }
    public static Optional<UserStatus> getStatus(int id){
        return Arrays.stream(values())
                .filter((i) -> i.status.getId() == id)
                .findFirst()
                .map(i -> i.status);
    }
}
