package entity.user;

import dao.interfaces.DaoFactory;
import dao.interfaces.UserStatusDao;

public enum UserStatusType {
    ADMIN ("admin", DaoFactory.getFactory(DaoFactory.TypesDatabases.MY_SQL).getUserStatusDao()),
    PASSENGER ("passenger", DaoFactory.getFactory(DaoFactory.TypesDatabases.MY_SQL).getUserStatusDao()),
    DRIVER ("driver", DaoFactory.getFactory(DaoFactory.TypesDatabases.MY_SQL).getUserStatusDao());

    private UserStatus status;

    UserStatusType(String title, UserStatusDao userStatusDao){
        int id = userStatusDao.getUserStatusId(title)
                .orElseThrow(() -> new IllegalStateException("UserStatus with title " + "'" + title + "'" + " didn't found in DB"));
        this.status = new UserStatus(id, title);
    }

    public UserStatus getStatus() {
        return status;
    }
}
