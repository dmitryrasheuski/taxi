package entity.car;

import lombok.Data;
import lombok.NonNull;

@Data
public class Color {
    private Integer id;
    @NonNull
    private String title;
}
