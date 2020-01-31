package dao.impl.orm.jpa;

import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import javax.persistence.EntityManagerFactory;
import java.util.ResourceBundle;

public class HibernateDataSource implements DataSource {
    private static final HibernateDataSource INSTANCE;
    static {
        INSTANCE = new HibernateDataSource();
    }

    private EntityManagerFactory entityManagerFactory;

    private HibernateDataSource() {
        entityManagerFactory = getConfiguration().buildSessionFactory();
    }
    public static HibernateDataSource getInstance(){
        return INSTANCE;
    }

    @Override
    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    private Configuration getConfiguration() {
        ResourceBundle rb = ResourceBundle.getBundle("db");

        return new Configuration().configure("META-INF/persistent/hibernate/conf.xml")
                .setProperty( Environment.USER, rb.getString("user") )
                .setProperty(Environment.PASS, rb.getString("password"))
                .setProperty(Environment.DRIVER, rb.getString("driver"))
                .setProperty(Environment.URL, rb.getString("url"));
    }
}
