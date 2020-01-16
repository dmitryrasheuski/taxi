package service.interfaces.order;

import entity.order.Order;

import java.util.List;

public interface IOrderWaitingListProviding {
    /**
     * @return a list of orders from OrderWaitingList
     */
    List<Order> getOrderWaitingList();
}
