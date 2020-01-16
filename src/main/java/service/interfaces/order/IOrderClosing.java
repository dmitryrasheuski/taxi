package service.interfaces.order;

import entity.order.Order;

public interface IOrderClosing {
    /**
     * It add this order into the database
     *
     * @param order this parameter must have got not-null fields: passenger, car, from, where
     * @return true, if operation of adding was successful, otherwise return false
     */
    boolean closeOrder(Order order);
}
