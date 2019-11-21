package entity.user;

public class UserBuilder {
    private User user;

    private UserBuilder(){
        user = new User();
    }
    static public UserBuilder createUser(){
        return new UserBuilder();
    }

    public UserBuilder setId(long id){
        user.setId(id);
        return this;
    }
    public UserBuilder setPhone(int phone){
        user.setPhone(phone);
        return this;
    }
    public UserBuilder setName(String name){
        user.setName(name);
        return this;
    }
    public UserBuilder setSurname(String surname){
        user.setSurname(surname);
        return this;
    }
    public UserBuilder setPassword(String password){
        user.setPassword(password);
        return this;
    }
    public UserBuilder setStatus(String status){
        user.setStatus(status);
        return this;
    }

    public User getUser(){
        return user;
    }
}
