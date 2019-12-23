package entity.user;

import lombok.Data;

@Data
public class User {
    private long id;
    private int phone;
    private String name;
    private String surname;
    private String password;
    private String status;
}
