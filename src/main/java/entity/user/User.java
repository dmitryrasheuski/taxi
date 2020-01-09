package entity.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private Long id;
    private Integer phone;
    private String name;
    private String surname;
    private String password;
    private UserStatus status;
}
