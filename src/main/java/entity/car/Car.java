package entity.car;

import lombok.Data;

@Data
public class Car {
    private long id;
    private String number;
    private long idDriver;
    private String color;
    private String model;
    private boolean active;
}
