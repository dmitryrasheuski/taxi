package dao.impl.mysql;

import dao.interfaces.OrderDao;
import entity.order.Address;
import entity.order.Order;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class OrderDaoImpl extends AbstractDao implements OrderDao {
    private static final Logger logger = Logger.getLogger(OrderDaoImpl.class);
    private static final String addOrder = "INSERT INTO orders(idUser, idCar, `from`, `where`, comments) VALUES (?, ?, ?, ?, ?)";
    private static final String deleteOrder = "DELETE FROM orders WHERE id = ?";
    private static final String getListById = "SELECT  id, idCar, idUser, `from`, `where`, comments FROM orders WHERE idUser = ?";

    OrderDaoImpl(MysqlDaoFactory daoFactory){
        super(daoFactory);
    }

    @Override
    public Optional<Long> addOrder(Order order) throws SQLException {
        return addEntity(order, addOrder);
    }
    @Override
    public Optional<Integer> deleteOrder(long id) throws SQLException {
        return deleteById(id, deleteOrder);
    }
    @Override
    public Optional<List<Order>> getListByIdUser(long idUser) throws SQLException {
        return getEntityByOneValue(idUser, getListById);
    }


    @Override
    PreparedStatement getPreparedStatementForAddEntity(Connection con, PreparedStatement ps, String sqlInsert, Object entity) throws SQLException {
        Order order = (Order)entity;
        Address from = order.getFrom();
        Address where = order.getWhere();

        ps = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
        ps.setLong(1, order.getPassenger().getId());
        ps.setLong(2, order.getCar().getId());
        ps.setString(3, from.getTitle());
        ps.setString(4, where.getTitle());
        ps.setString(5, order.getComments());
        return ps;
    }
    @Override
    Optional<List<Order>> handleResultSet(ResultSet rs) throws SQLException {
        if(!rs.next()) {
            return Optional.empty();
        }

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
            order = Order.builder()
                    .id(id)
                    .passenger(daoFactory.getUserDao().getById(idUser).orElseThrow(() -> new NullPointerException("User was not found")))
                    .car(daoFactory.getCarDao().getCarById(idCar).orElseThrow(() -> new NullPointerException("Car was not found")))
                    .from(new Address(from))
                    .where(new Address(where))
                    .comments(comments)
                    .build();
            list.add(order);
        } while (rs.next());

        return Optional.of(list);
    }
}
