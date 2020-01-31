package dao.impl.orm.jpa;

import entity.user.UserStatus;

import java.sql.SQLException;
import java.util.*;

class UserStatusDao extends AbstractDao<UserStatus> implements dao.interfaces.UserStatusDao {
    private static final String getByTitle = "SELECT us FROM UserStatus us WHERE us.title = :title ";

    UserStatusDao(JpaDaoFactory daoFactory) {
        super(daoFactory);
    }

    @Override
    public Optional<Integer> mergeUserStatus(UserStatus status) throws SQLException {
        Map<String, Object> parameters = new LinkedHashMap<>(1);
        parameters.put("title", status.getTitle());

        List<UserStatus> list = getEntities(getByTitle, parameters);

        return list.isEmpty() ? addEntity(status).map(UserStatus::getId) : Optional.of( list.get(0).getId() );
    }
}
