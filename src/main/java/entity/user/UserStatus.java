package entity.user;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(value = AccessLevel.PACKAGE)
@Getter
@EqualsAndHashCode
@ToString
public class UserStatus {
    private int id;
    private String title;

    public static UserStatus getInstance(UserStatusType type){
        return type.getStatus();
    }
}
