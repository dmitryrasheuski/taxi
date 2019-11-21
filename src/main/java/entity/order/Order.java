package entity.order;

import java.util.Objects;

public class Order {
    private long id;
    private long idUser;
    private long idCar;
    private String from;
    private String where;
    private String comments;

    public long getId() {
        return id;
    }
    public long getIdUser() {
        return idUser;
    }
    public long getIdCar() {
        return idCar;
    }
    public String getFrom() {
        return from;
    }
    public String getWhere() {
        return where;
    }
    public String getComments() {
        return comments;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }
    public void setIdCar(long idCar) {
        this.idCar = idCar;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    public void setWhere(String where) {
        this.where = where;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return id == order.id &&
                idUser == order.idUser &&
                idCar == order.idCar &&
                Objects.equals(from, order.from) &&
                Objects.equals(where, order.where) &&
                Objects.equals(comments, order.comments);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, idUser, idCar, from, where, comments);
    }
}
