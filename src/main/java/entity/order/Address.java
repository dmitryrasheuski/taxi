package entity.order;

import lombok.Data;
import lombok.NonNull;

@Data
public class Address {
    private Long id;
    @NonNull
    private String title;

    public Address(Long id, String title) {
        this.id = id;
        this.title = title.toUpperCase();
    }
    public Address(String title) {
        this.title = title.toUpperCase();
    }
}
