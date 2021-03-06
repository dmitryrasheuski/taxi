package dao.impl.mysql;

import appException.dao.AppSqlException;
import dao.interfaces.OrderDao;
import entity.order.Order;
import entity.order.OrderBuilder;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class OrderDaoImpl extends AbstractDao implements OrderDao {
    private static final Logger logger = Logger.getLogger(OrderDaoImpl.class);
    private static final String addOrder = "INSERT INTO orders(idUser, idCar, `from`, `where`, comments) VALUES (?, ?, ?, ?, ?)";
    private static final String deleteOrder = "DELETE FROM orders WHERE id = ?";
    private static final String getListById = "SELECT  id, idCar, idUser, `from`, `where`, comments FROM orders WHERE idUser = ?";

    OrderDaoImpl(MysqlDaoFactory daoFactory){
        super(daoFactory);
    }

    @Override
    public long addOrder(Order order) throws SQLException, AppSqlException{
        return addEntity(order, addOrder, "order didn't add");
    }
    @Override
    public void deleteOrder(long id) throws SQLException, AppSqlException{
        deleteById(id, deleteOrder, "order didn't delete");
    }
    @Override
    public List<Order> getListByIdUser(long idUser) throws SQLException, AppSqlException{
        return getEntityByOneValue(idUser, getListById, "list is empty");
    }


    @Override
    PreparedStatement getPreparedStatementForAddEntity(Connection con, PreparedStatement ps, String sqlInsert, Object entity) throws SQLException, AppSqlException {
        Order order = (Order)entity;
        ps = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
        ps.setLong(1, order.getIdUser());
        ps.setLong(2, order.getIdCar());
        ps.setString(3, order.getFrom());
        ps.setString(4, order.getWhere());
        ps.setString(5, order.getComments());
        return ps;
    }
    @Override
    List handleResultSet(ResultSet rs) throws SQLException {
        List<Order> list = new ArrayList<>();
        Order order = null;
        long id = 0;
        long idCar = 0;
        long idUser = 0;
        String from = null;
        String where = null;
        String comments = null;
        do{
            id = rs.getLong("id");
            idCar = rs.getLong("idCar");
            idUser = rs.getLong("idUser");
            from = rs.getString("from");
            where = rs.getString("where");
            comments = rs.getString("comments");
            order = OrderBuilder.createOrder().setId(id).setIdUser(idUser).setIdCar(idCar).setFrom(from).setWhere(where).setComments(comments).getOrder();
            list.add(order);
        } while (rs.next());
        return list;
    }
}
