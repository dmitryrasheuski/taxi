package dao.impl.orm.jpa;

import lombok.extern.log4j.Log4j;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@Log4j
abstract class AbstractDao <T> {
    JpaDaoFactory daoFactory;
    EntityManager entityManager;
    EntityTransaction transaction;

    public AbstractDao(JpaDaoFactory daoFactory) {
        this.daoFactory = daoFactory;
        this.entityManager = daoFactory.getEntityManager();
        this.transaction = entityManager.getTransaction();
    }

    Optional<T> addEntity(T entity) throws SQLIntegrityConstraintViolationException {
        try {
            transaction.begin();
            entityManager.persist(entity);
            transaction.commit();
            return Optional.of(entity);

        } catch (PersistenceException ex) {
            if( ex.getCause() == null ) throw ex;
            if ( ex.getCause().getClass() != ConstraintViolationException.class ) throw  ex;

            rollback(ex);
            log.error("Entity is duplicate. Entity: " + entity, ex);
            throw new SQLIntegrityConstraintViolationException();

        }catch (Exception ex) {
            rollback(ex);
            log.error("An exception was generated when an entity was added to the database. Entity: " + entity, ex);
            return Optional.empty();
        }
    }
    boolean removeEntity(Class<T> clazz, Long id) {
        boolean success = false;

        try {
            transaction.begin();
            entityManager.remove(
                    entityManager.getReference(clazz, id) );
            transaction.commit();
            success = true;
        } catch (Exception ex) {
            rollback(ex);
            log.error("An exception was generated when an entity was removed from the database. id: " + id + ", class: " + clazz.getName(), ex);
        }

        return success;
    }
    Optional<T> getEntity(Class<T> clazz, Long id) {
        T entity = null;

        try {
            transaction.begin();
            entity = entityManager.find(clazz, id);
            transaction.commit();
        } catch (Exception ex) {
            rollback(ex);
            log.error("An exception was generated when an entity was found in the database. Entity: " + entity, ex);
        }

        return Optional.ofNullable(entity);
    }
    boolean updateEntity(Class<T> clazz, Long entityId, Consumer<T> updateMethod) {
        boolean success = false;

        try {
            transaction.begin();
            T entity = entityManager.getReference(clazz, entityId);
            updateMethod.accept(entity);
            transaction.commit();
            success = true;

        } catch (Exception ex) {
            rollback(ex);
            log.error("An exception was generated when an entity was updated in the database. Class: " + clazz + " entityId: " + entityId, ex);
        }

        return success;
    }
    List<T> getEntities (String jpqlStr, Map<String, Object> parameters) {
        List<T> list = new ArrayList<>();

        try {
            transaction.begin();
            Query query = getQuery(jpqlStr, parameters);
            list = query.getResultList();
            transaction.commit();
        } catch (Exception ex) {
            rollback(ex);
            log.error("The exception was generated at the time of query.", ex);
        }

        return list;
    }
    int updateOrDelete(String jpqlStr, Map<String, Object> parameters) {
        int res = 0 ;

        try {
            transaction.begin();
            Query query = getQuery(jpqlStr, parameters);
            res = query.executeUpdate();
            transaction.commit();
        } catch (Exception ex) {
            rollback(ex);
            log.error("The exception was generated at the time of query.", ex);
        }

        return res;
    }

    void rollback(Exception ex) {
        if (transaction == null) return;

        try {
            transaction.rollback();
        } catch (Exception e) {

            if (ex == null) {
                ex = e;
            } else {
                ex.addSuppressed(e);
            }

        }
    }
    private Query getQuery(String jpqlStr, Map<String, Object> parameters) {
        Query query = entityManager.createQuery(jpqlStr);
        parameters.forEach(query::setParameter);

        return query;
    }

}
