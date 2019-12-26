package dao.impl.mysql;

import dao.interfaces.OrderDao;
import entity.order.Address;
import entity.order.Order;
import lombok.extern.log4j.Log4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j
class OrderDaoImpl extends AbstractDao<Order> implements OrderDao {
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
    PreparedStatement getPreparedStatementForAddEntity(Connection con, PreparedStatement ps, String sqlInsert, Order entity) throws SQLException {
        ps = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
        ps.setLong(1, entity.getPassenger().getId());
        ps.setLong(2, entity.getCar().getId());
        ps.setString(3, entity.getFrom().getTitle());
        ps.setString(4, entity.getWhere().getTitle());
        ps.setString(5, entity.getComment());
        return ps;
    }
    @Override
    Optional<List<Order>> handleResultSet(ResultSet rs) throws SQLException {
        if(!rs.next()) {
            return Optional.empty();
        }

        List<Order> list = new ArrayList<>();
        Order order;
        long id;
        long idCar;
        long idUser;
        String from;
        String where;
        String comment;
        do{
            id = rs.getLong("id");
            idCar = rs.getLong("idCar");
            idUser = rs.getLong("idUser");
            from = rs.getString("from");
            where = rs.getString("where");
            comment = rs.getString("comments");
            order = Order.builder()
                    .id(id)
                    .passenger(daoFactory.getUserDao().getById(idUser).orElseThrow(() -> new NullPointerException("User was not found")))
                    .car(daoFactory.getCarDao().getCarById(idCar).orElseThrow(() -> new NullPointerException("Car was not found")))
                    .from(new Address(from))
                    .where(new Address(where))
                    .comment(comment)
                    .build();
            list.add(order);
        } while (rs.next());

        return Optional.of(list);
    }
}
