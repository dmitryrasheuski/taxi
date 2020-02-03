package dao.impl.orm.jpa;

import entity.user.UserStatus;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

class UserStatusDao extends AbstractDao<UserStatus> implements dao.interfaces.UserStatusDao {
    private static final String getByTitle = "SELECT us FROM UserStatus us WHERE us.title = :title ";
    private static final String getList = "SELECT us FROM UserStatus us";

    UserStatusDao(JpaDaoFactory daoFactory) {
        super(daoFactory);
    }

    @Override
    public Optional<Long> mergeUserStatus(UserStatus status) throws SQLException {
        Map<String, Object> parameters = new LinkedHashMap<>(1);
        parameters.put("title", status.getTitle());

        List<UserStatus> list = getEntities(getByTitle, parameters);

        return list.isEmpty() ?
                addEntity(status).map(UserStatus::getId) :
                Optional.of( list.get(0).getId() );
    }

    @Override
    public Map<Long, String> getMap() throws SQLException {
        List<UserStatus> list = getEntities(getList, new LinkedHashMap<>(0));

        Map<Long, String> map = new LinkedHashMap<>();
        list.forEach( (status) ->
                map.put( status.getId() , status.getTitle() )
        );

        return map;
    }
}
