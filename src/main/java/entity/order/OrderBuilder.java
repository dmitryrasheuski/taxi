package entity.order;

public class OrderBuilder {
    private Order order;

    private OrderBuilder() {
        order = new Order();
    }
    static public OrderBuilder createOrder() {
        return new OrderBuilder();
    }


    public OrderBuilder setId(long id) {
        order.setId(id);
        return this;
    }
    public OrderBuilder setIdUser(long id) {
        order.setIdUser(id);
        return this;
    }
    public OrderBuilder setIdCar(long id) {
        order.setIdCar(id);
        return this;
    }
    public OrderBuilder setFrom(String from) {
        order.setFrom(from);
        return this;

    }
    public OrderBuilder setWhere(String where) {
        order.setWhere(where);
        return this;
    }
    public OrderBuilder setComments(String comments) {
        order.setComments(comments);
        return this;
    }

    public Order getOrder(){
        return order;
    }
}
