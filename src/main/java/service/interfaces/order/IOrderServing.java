package service.interfaces.order;

import entity.car.Car;
import entity.order.Order;
import entity.user.User;

public interface IOrderServing {
    /**
     * It get the order from OrderWaitingList to which the specified passenger is mapped and set the specified car in the 'car' field this order
     *
     * @param passenger It is a key which links to the order from OrderWaitingList
     * @param car It is the car which serves the order
     * @return The order to which the specified passenger refers, or null
     */
    Order servePassenger(User passenger, Car car);
}
