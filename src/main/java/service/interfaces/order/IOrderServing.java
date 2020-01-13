package service.interfaces.order;

import entity.car.Car;
import entity.order.Order;
import entity.user.User;

public interface IOrderServing {
    Order servePassenger(User passenger, Car car);
}
