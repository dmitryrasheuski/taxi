package entity.user;

import dao.impl.mysql.MysqlDaoFactory;
import dao.interfaces.UserStatusDao;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

public enum UserStatusType {
    ADMIN (new MysqlDaoFactory().getUserStatusDao()),
    PASSENGER (new MysqlDaoFactory().getUserStatusDao()),
    DRIVER (new MysqlDaoFactory().getUserStatusDao());

    private UserStatus status;

    UserStatusType(UserStatusDao userStatusDao){
        status = new UserStatus();
        status.setTitle( name() );

        Long id;
        try {
            id = userStatusDao.mergeUserStatus(status)
                    .orElseThrow(() -> new IllegalStateException("UserStatus didn't found in DB. Title: " + status.getTitle()));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

        status.setId(id);
    }

    public UserStatus getStatus() {
        return status;
    }

    public static Optional<UserStatus> getStatus(long id){
        return Arrays.stream( values() )
                .filter( (type) -> type.status.getId() == id )
                .findFirst()
                .map(UserStatusType::getStatus);
    }
}
