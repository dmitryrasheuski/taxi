package service.interfaces.order;

import entity.order.Order;
import entity.user.User;

import java.util.List;

public interface ITripListProviding {
    List<Order> getTripList(User user);
}
