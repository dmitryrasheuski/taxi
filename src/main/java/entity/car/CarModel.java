package entity.car;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class CarModel {
    private Long id;
    @NonNull
    private String title;
}
