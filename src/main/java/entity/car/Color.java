package entity.car;

import lombok.Data;
import lombok.NonNull;

@Data
public class Color {
    private int id;
    @NonNull
    private String title;
}
