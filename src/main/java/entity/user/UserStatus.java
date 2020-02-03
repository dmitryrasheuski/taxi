package entity.user;

import lombok.*;

@Setter(value = AccessLevel.PACKAGE)
@Getter
@EqualsAndHashCode
@ToString
public class UserStatus {
    private Long id;
    private String title;

    public static UserStatus getInstance(UserStatusType type){
        return type.getStatus();
    }
}
