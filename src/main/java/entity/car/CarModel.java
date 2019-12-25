package entity.car;

import lombok.Data;
import lombok.NonNull;

@Data
public class CarModel {
    private int id;
    @NonNull
    private String title;
}
