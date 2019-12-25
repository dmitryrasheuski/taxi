package entity.order;

import entity.car.Car;
import entity.user.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Order {
    private long id;
    private User passenger;
    private Car car;
    private Address from;
    private Address where;
    private String comments;
}
