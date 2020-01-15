package dao.impl.mysql;

import dao.interfaces.OrderDao;
import entity.car.Car;
import entity.order.Address;
import entity.order.Order;
import entity.user.User;
import lombok.extern.log4j.Log4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j
class OrderDaoImpl extends AbstractDao<Order> implements OrderDao {
    private static final String addOrder = "INSERT INTO orders(passenger_id, car_id, from_id, where_id, comment) VALUES (?, ?, ?, ?, ?)";
    private static final String deleteOrder = "DELETE FROM orders WHERE id = ?";
    private static final String getListByPassengerId = "SELECT  id, passenger_id, car_id, from_id, where_id, comment FROM orders WHERE passenger_id = ?";

    OrderDaoImpl(MysqlDaoFactory daoFactory){
        super(daoFactory);
    }

    @Override
    public Optional<Long> addOrder(Order order) throws SQLException {
        return addEntity(order, addOrder);
    }
    @Override
    public Optional<Integer> deleteOrder(long id) throws SQLException {
        Object[] parameters = new Object[] {id};

        return deleteOrUpdateEntity(parameters, deleteOrder);

    }
    @Override
    public Optional<List<Order>> getListByPassengerId(long passengerId) throws SQLException {
        Object[] parameters = new Object[] {passengerId};

        return getEntity(parameters, getListByPassengerId);

    }

    @Override
    PreparedStatement getPreparedStatementForAddEntity(String sqlInsert, Order entity) throws SQLException {
        PreparedStatement ps;

        ps = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
        ps.setLong(1, entity.getPassenger().getId());
        ps.setLong(2, entity.getCar().getId());
        ps.setLong(3, entity.getFrom().getId());
        ps.setLong(4, entity.getWhere().getId());
        ps.setString(5, entity.getComment());

        return ps;

    }
    @Override
    Optional<List<Order>> handleResultSet(ResultSet rs) throws SQLException {
        if( ! rs.next() ) return Optional.empty();

        List<Order> list = new ArrayList<>();
        Order order;
        long id;
        long carId;
        Car car;
        long passengerId;
        User passenger;
        long fromId;
        Address from;
        long whereId;
        Address where;
        String comment;
        do{
            id = rs.getLong("id");
            carId = rs.getLong("car_id");
            car = daoFactory.getCarDao()
                    .getCarById(carId)
                    .orElseThrow(() -> new IllegalStateException("Car wasn't found in database"));
            passengerId = rs.getLong("passenger_id");
            passenger = daoFactory.getUserDao()
                    .getById(passengerId)
                    .orElseThrow(() -> new IllegalStateException("User wasn't found in database"));
            fromId = rs.getLong("from_id");
            from = daoFactory.getAddressDao()
                    .getAddress(fromId)
                    .orElseThrow(() -> new IllegalStateException("Address wasn't found in database"));
            whereId = rs.getLong("where_id");
            where = daoFactory.getAddressDao()
                    .getAddress(whereId)
                    .orElseThrow(() -> new IllegalStateException("Address wasn't found in database"));
            comment = rs.getString("comment");
            order = Order.builder()
                    .id(id)
                    .passenger(passenger)
                    .car(car)
                    .from(from)
                    .where(where)
                    .comment(comment)
                    .build();
            list.add(order);
        } while (rs.next());

        return Optional.of(list);
    }
}
