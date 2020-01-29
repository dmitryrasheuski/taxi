package dao.impl.orm.jpa;

import javax.persistence.EntityManagerFactory;

interface DataSource {
    EntityManagerFactory getEntityManagerFactory();
}
