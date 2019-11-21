package entity.user;

import java.util.Objects;

public class User {
    private long id;
    private int phone;
    private String name;
    private String surname;
    private String password;
    private String status;

    public long getId() {
        return id;
    }
    public int getPhone() {
        return phone;
    }
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public String getPassword() {
        return password;
    }
    public String getStatus() {
        return status;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setPhone(int phone) {
        this.phone = phone;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id &&
                phone == user.phone &&
                Objects.equals(name, user.name) &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(password, user.password) &&
                Objects.equals(status, user.status);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, phone, name, surname, password, status);
    }
}
