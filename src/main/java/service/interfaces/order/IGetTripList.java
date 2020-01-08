package service.interfaces.order;

import entity.order.Order;
import entity.user.User;

import java.util.List;

public interface IGetTripList {
    List<Order> getTripList(User passenger);
}
