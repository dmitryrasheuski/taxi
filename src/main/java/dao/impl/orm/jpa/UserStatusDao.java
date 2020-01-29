package dao.impl.orm.jpa;

import entity.user.UserStatus;

import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.Optional;

public class UserStatusDao implements dao.interfaces.UserStatusDao {
    private JpaDaoFactory daoFactory;
    private EntityManager entityManager;

    public UserStatusDao(JpaDaoFactory daoFactory) {
        this.daoFactory = daoFactory;
        this.entityManager = daoFactory.getEntityManager();
    }

    @Override
    public Optional<Integer> mergeUserStatus(UserStatus status) throws SQLException {
        return Optional.empty();
    }
}
