package service.interfaces.order;

import entity.order.Order;
import entity.user.User;

import java.util.List;

public interface ITripListProviding {
    /**
     * @param user It is 'passenger' field from a order . It must have not-null fields: id
     * @return a list of order by user from database
     */
    List<Order> getTripList(User user);
}
