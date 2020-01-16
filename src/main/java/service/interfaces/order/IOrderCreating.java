package service.interfaces.order;

import entity.order.Order;

public interface IOrderCreating {
    /**
     * It is adding this order into OrderWaitingList
     *
     * @param order this parameter must have got not-null fields: passenger, from, where
     * @return true, if the operation of adding was successful, otherwise return false
     */
    boolean createOrder(Order order);
}
