package entity.car;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class CarModel {
    private int id;
    @NonNull
    private String title;
}
