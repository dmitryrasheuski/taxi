package entity.car;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Car {
    private long id;
    private String number;
    private long idDriver;
    private String color;
    private String model;
    private boolean active;
}
