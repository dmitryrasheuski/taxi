package entity.order;

import lombok.Data;
import lombok.NonNull;

@Data
public class Address {
    private long id;
    @NonNull
    private String title;
}
