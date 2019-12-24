package entity.user;

import lombok.*;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
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
