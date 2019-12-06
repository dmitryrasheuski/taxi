package dao.impl.mysql;

import appException.dao.AppSqlException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Optional;

abstract class AbstractDao {
    private static final Logger logger = Logger.getLogger(AbstractDao.class);
    private MysqlDaoFactory daoFactory;

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

    Optional<Long> addEntity(Object entity, String sqlInsert) throws SQLException{
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
    List getEntityByOneValue(Object value, String sqlSelect, String exceptionMessage)throws AppSqlException, SQLException{
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = getConnection();

            ps = con.prepareStatement(sqlSelect);
            ps.setObject(1, value);

            rs = ps.executeQuery();
            throwAppSqlException(!rs.next(), exceptionMessage);

            return handleResultSet(rs);

        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
            closeConnection(con);
        }
    }
    void deleteById(long id, String sqlDelete, String exceptionMessage) throws AppSqlException, SQLException{
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = getConnection();

            ps = con.prepareStatement(sqlDelete);
            ps.setLong(1, id);

            int res = ps.executeUpdate();
            throwAppSqlException(res == 0, exceptionMessage);

        } finally {
            closePreparedStatement(ps);
            closeConnection(con);
        }
    }
    void updateOneColumnById(long id, Object value, String sqlUpdate, String exceptionMessage) throws AppSqlException, SQLException{
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = getConnection();

            ps = con.prepareStatement(sqlUpdate);
            ps.setObject(1, value);
            ps.setLong(2, id);

            int res = ps.executeUpdate();
            throwAppSqlException(res == 0, exceptionMessage);

        } catch (Exception ex) {
            try {
                rollback(con);
            } catch (SQLException e){
                this.logger.error("rollback exception", e);
                ex.addSuppressed(e);
            }
            throw ex;

        } finally {
            closePreparedStatement(ps);
            closeConnection(con);
        }
    }

    abstract PreparedStatement getPreparedStatementForAddEntity(Connection con, PreparedStatement ps, String sqlInsert, Object entity) throws SQLException;
    abstract List handleResultSet(ResultSet rs) throws SQLException;
}
