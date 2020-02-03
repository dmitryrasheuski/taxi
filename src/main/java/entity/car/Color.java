package entity.car;

import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class Color {
    private Long id;
    @NonNull
    private String title;
}
