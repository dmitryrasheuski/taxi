package entity.car;

import entity.user.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Car {
    private Long id;
    private String number;
    private User driver;
    private Color color;
    private CarModel model;
}
