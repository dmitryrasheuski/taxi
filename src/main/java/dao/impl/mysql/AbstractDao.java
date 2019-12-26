package dao.impl.mysql;

import appException.dao.AppSqlException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Optional;

abstract class AbstractDao<T> {
    private static final Logger logger = Logger.getLogger(AbstractDao.class);
    protected MysqlDaoFactory daoFactory;

    AbstractDao(MysqlDaoFactory factory) {
        this.daoFactory = factory;
    }

    Connection getConnection() throws SQLException {
        return daoFactory.getConnection();

    }
    void startTransaction(Connection con) throws SQLException {
        if (con != null) {
            con.setAutoCommit(false);
        }

    }
    void commit(Connection con) throws SQLException {
        if (con != null) {
            con.commit();
        }

    }
    void rollback(Connection con, Exception ex) throws SQLException {
        if (con != null) {
            try {
                con.rollback();
            }catch (SQLException e){
                logger.error("rollback exception", e);
                ex.addSuppressed(e);
            }
        }
    }
    void close(AutoCloseable resource, Exception ex){
        if (resource != null){
            try {
                resource.close();
            } catch (Exception e) {
                logger.error(resource.getClass().getSimpleName() + " close exception", e);
                if (ex != null) {
                    ex.addSuppressed(e);
                }
            }
        }
    }

    Optional<Long> addEntity(T entity, String sqlInsert) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Exception exception = null;
        try {
            con = getConnection();
            startTransaction(con);

            ps = getPreparedStatementForAddEntity(con, ps, sqlInsert, entity);
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            commit(con);

            if(rs.next()){
                return Optional.of(rs.getLong(1));
            } else {
                return Optional.empty();
            }

        } catch (Exception ex){
            rollback(con, ex);
            exception = ex;
            throw ex;

        } finally {
            close(rs, exception);
            close(ps, exception);
            close(con, exception);
        }
    }
    Optional<List<T>> getEntityByOneValue(Object value, String sqlSelect) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Exception exception = null;
        try {
            con = getConnection();

            ps = con.prepareStatement(sqlSelect);
            ps.setObject(1, value);

            rs = ps.executeQuery();

            return handleResultSet(rs);

        } catch (Exception ex) {
            exception = ex;
            throw ex;

        } finally {
            close(rs, exception);
            close(ps, exception);
            close(con, exception);
        }
    }
    Optional<Integer> deleteById(long id, String sqlDelete) throws SQLException{
        Connection con = null;
        PreparedStatement ps = null;
        Exception exception = null;
        try {
            con = getConnection();
            startTransaction(con);
            ps = con.prepareStatement(sqlDelete);
            ps.setLong(1, id);
            int res = ps.executeUpdate();
            commit(con);

            if(res > 0){
                return Optional.of(res);
            } else {
                return Optional.empty();
            }

        } catch (Exception ex) {
            rollback(con, ex);
            exception = ex;
            throw ex;

        } finally {
            close(ps, exception);
            close(con, exception);
        }
    }
    Optional<Integer> updateOneColumnById(long id, Object value, String sqlUpdate) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        Exception exception = null;
        try {
            con = getConnection();
            startTransaction(con);
            ps = con.prepareStatement(sqlUpdate);
            ps.setObject(1, value);
            ps.setLong(2, id);
            int res = ps.executeUpdate();
            commit(con);

            if(res > 0){
                return Optional.of(res);
            } else {
                return Optional.empty();
            }

        } catch (Exception ex) {
            rollback(con, ex);
            exception = ex;
            throw ex;

        } finally {
            close(ps, exception);
            close(con, exception);
        }
    }

    abstract PreparedStatement getPreparedStatementForAddEntity(Connection con, PreparedStatement ps, String sqlInsert, T entity) throws SQLException;
    abstract Optional<List<T>> handleResultSet(ResultSet rs) throws SQLException;
}
