package dao.impl.orm.jpa;

import dao.interfaces.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class JpaDaoFactory implements DaoFactory {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    
    public JpaDaoFactory(DataSource dataSource) {
        entityManagerFactory = dataSource.getEntityManagerFactory();
        entityManager = entityManagerFactory.createEntityManager();
    }

    EntityManager getEntityManager(){
        return entityManager;
    }
    @Override
    public UserDao getUserDao() {
        return new UserDao(this);
    }
    @Override
    public UserStatusDao getUserStatusDao() {
        return new UserStatusDao(this);
    }
    @Override
    public OrderDao getOrderDao() {
        return new OrderDao(this);
    }
    @Override
    public CarDao getCarDao() {
        return new CarDao(this);
    }
    @Override
    public AddressDao getAddressDao() {
        return new AddressDao(this);
    }
    @Override
    public ColorDao getColorDao() {
        return new ColorDao(this);
    }
    @Override
    public CarModelDao getCarModelDao() {
        return new CarModelDao(this);
    }
}
