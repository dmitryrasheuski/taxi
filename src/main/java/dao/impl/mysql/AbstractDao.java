package dao.impl.mysql;

import appException.dao.AppSqlException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.List;

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
    void rollback(Connection con) throws SQLException {
        if (con != null) {
            con.rollback();
        }

    }
    void closeConnection(Connection con){
        daoFactory.closeConnection(con);

    }
    void closePreparedStatement(PreparedStatement ps) {
        if(ps != null){
            try {
                ps.close();
            } catch (SQLException ex) {
                logger.error("close preparedStatement exception", ex);
            }
        }

    }
    void closeResultSet(ResultSet rs) {
        if(rs != null){
            try {
                rs.close();
            } catch (SQLException ex) {
                logger.error("ResultSet.close() exception", ex);
            }
        }

    }
    void throwAppSqlException(boolean condition, String message) throws AppSqlException {
        if (condition) {
            throw new AppSqlException(message);
        }

    }

    long addEntity(Object entity, String sqlInsert, String exceptionMessage) throws AppSqlException, SQLException{
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            startTransaction(con);

            ps = getPreparedStatementForAddEntity(con, ps, sqlInsert, entity);
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            throwAppSqlException(!rs.next(), exceptionMessage);

            long id = rs.getLong(1);
            commit(con);
            return id;

        } catch (SQLIntegrityConstraintViolationException ex) {
            try {
                rollback(con);
            }catch (SQLException e){
                this.logger.error("rollback exception", e);
            }
            throw new AppSqlException("phone is duplicate", ex);

        } catch (Exception ex){
            try {
                rollback(con);
            }catch (SQLException e){
                logger.error("rollback exception", e);
            }
            throw ex;

        } finally {
            closeResultSet(rs);
            closePreparedStatement(ps);
            closeConnection(con);
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

    abstract PreparedStatement getPreparedStatementForAddEntity(Connection con, PreparedStatement ps, String sqlInsert, Object entity) throws SQLException, AppSqlException;
    abstract List handleResultSet(ResultSet rs) throws SQLException;
}
