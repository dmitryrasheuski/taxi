package dao.impl.mysql;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.extern.log4j.Log4j;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

@Log4j
class DataSource {
    private static final DataSource INSTANCE;
    private static final String URL;
    private static final String DRIVER;
    private static final String USER;
    private static final String PASSWORD;
    static {
        ResourceBundle rb = ResourceBundle.getBundle("db");
        if(rb != null){
            DRIVER = rb.getString("driver");
            URL = rb.getString("url");
            USER = rb.getString("user");
            PASSWORD = rb.getString("password");
        } else {
            DRIVER = "unspecified";
            URL = "unspecified";
            USER = "unspecified";
            PASSWORD = "unspecified";
        }

        try {
            INSTANCE = new DataSource();
        } catch (PropertyVetoException ex) {
            log.error("The exception occurred when initializing the 'DataSource' class." , ex);
            throw new RuntimeException("The exception occurred when initializing the 'DataSource' class." , ex);
        }
    }

    private ComboPooledDataSource pool;

    private DataSource() throws PropertyVetoException {
        pool = new ComboPooledDataSource();
        pool.setDriverClass(DRIVER);
        pool.setJdbcUrl(URL);
        pool.setUser(USER);
        pool.setPassword(PASSWORD);
        pool.setInitialPoolSize(10);
        pool.setMinPoolSize(10);
        pool.setAcquireIncrement(5);
        pool.setMaxPoolSize(20);
        pool.setMaxStatements(180);
    }
    static synchronized DataSource getInstance() {
        return INSTANCE;
    }

    Connection getConnection() throws SQLException {
        return pool.getConnection();
    }

}
