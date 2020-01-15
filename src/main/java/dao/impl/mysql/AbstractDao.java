package dao.impl.mysql;

import lombok.extern.log4j.Log4j;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Log4j
abstract class AbstractDao<T> {
    protected MysqlDaoFactory daoFactory;
    protected Connection con;

    protected AbstractDao(MysqlDaoFactory factory) {
        this.daoFactory = factory;

        try {
            con = daoFactory.getConnection();
        } catch (SQLException ex) {
            log.error("The exception of the 'AbstractDao' class initialization had occurred at the moment of the received connection to the database", ex);
            throw new RuntimeException("The exception of the 'AbstractDao' class initialization had occurred at the moment of the received connection to the database", ex);
        }

    }

    void startTransaction() throws SQLException {
        if (con == null) return;

        con.setAutoCommit(false);

    }
    void commit() throws SQLException {
        if (con == null) return;

        con.commit();
        con.setAutoCommit(true);

    }
    void rollback(Exception ex) {
        if (con == null) return;

        try {
            con.rollback();
        }catch (SQLException e){
            log.error("rollback exception", e);
            ex.addSuppressed(e);
        }

        try {
            con.setAutoCommit(true);
        }catch (SQLException e){
            log.error("setAutoCommit exception", e);
            ex.addSuppressed(e);
        }

    }
    void close(AutoCloseable resource, Exception ex){
        if (resource == null) return;

        try {
            resource.close();
        } catch (Exception e) {
            log.error("The exception of the '" + resource.getClass().getSimpleName() + "' class closing" , e);
            if (ex != null) {
                ex.addSuppressed(e);
            }
        }

    }
    void preparedStatementParameterSetter(Object[] parameters, PreparedStatement ps) throws SQLException{
        int i = 0;

        for (Object param : parameters) {
            ps.setObject(++i, param);
        }

    }

    Optional<Long> addEntity(T entity, String sqlInsert) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Exception exception = null;

        try {
            startTransaction();

            ps = getPreparedStatementForAddEntity(sqlInsert, entity);
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            commit();

            if(!rs.next()) return Optional.empty();

            return Optional.of(rs.getLong(1));

        } catch (Exception ex){
            rollback(ex);
            exception = ex;
            throw ex;

        } finally {
            close(rs, exception);
            close(ps, exception);
        }

    }
    Optional<List<T>> getEntity(Object[] parameters, String sqlSelect) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Exception exception = null;

        try {
            ps = con.prepareStatement(sqlSelect);
            preparedStatementParameterSetter(parameters, ps);
            rs = ps.executeQuery();

            return handleResultSet(rs);

        } catch (Exception ex) {
            exception = ex;
            throw ex;

        } finally {
            close(rs, exception);
            close(ps, exception);

        }
    }
    Optional<Integer> deleteOrUpdateEntity(Object[] parameters, String sqlQuery) throws SQLException{
        PreparedStatement ps = null;
        Exception exception = null;

        try {
            startTransaction();

            ps = con.prepareStatement(sqlQuery);
            preparedStatementParameterSetter(parameters, ps);
            int res = ps.executeUpdate();
            commit();

            if(res == 0) return Optional.empty();

            return Optional.of(res);

        } catch (Exception ex) {
            rollback(ex);
            exception = ex;
            throw ex;

        } finally {
            close(ps, exception);
        }

    }

    abstract PreparedStatement getPreparedStatementForAddEntity(String sqlInsert, T entity) throws SQLException;
    abstract Optional<List<T>> handleResultSet(ResultSet rs) throws SQLException;

}
