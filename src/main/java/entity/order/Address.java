package entity.order;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
