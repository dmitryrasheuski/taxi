package entity.order;

import java.util.LinkedList;
import java.util.List;

public class WaitingList {
    private static final List<Order> list = new LinkedList<>();
    private static final WaitingList INSTANCE = new WaitingList();

    private WaitingList(){}
    public static WaitingList getInstance(){
        return INSTANCE;
    }

    public boolean addOrder(Order order){
        if(order == null) throw new IllegalArgumentException("Order is null");
        return list.add(order);
    }
    public boolean removeOrder(Order order){
        if(order == null) throw new IllegalArgumentException("Order is null");
        return list.remove(order);
    }
}
