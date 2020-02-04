package entity.order;

import entity.car.Car;
import entity.user.User;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
@Builder
public class Order {
    private Long id;
    private User passenger;
    private Car car;
    private Address from;
    private Address where;
    private String comment;
}
