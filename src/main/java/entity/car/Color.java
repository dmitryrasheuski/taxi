package entity.car;

import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class Color {
    private Integer id;
    @NonNull
    private String title;
}
