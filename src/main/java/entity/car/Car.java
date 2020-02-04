package entity.car;

import entity.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "cars")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 7, nullable = false, unique = true)
    private String number;

    @OneToOne
    @JoinTable(
            name = "car_driver",
            joinColumns = @JoinColumn(name = "car_id", referencedColumnName = "id", nullable = false, unique = true),
            inverseJoinColumns = @JoinColumn(name = "driver_id", referencedColumnName = "id", nullable = false, unique = true)
    )
    private User driver;

    @ManyToOne
    @JoinColumn(name = "color_id", nullable = false)
    private Color color;

    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
    private CarModel model;
}
