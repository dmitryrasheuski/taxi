package entity.car;

import java.util.Objects;

public class Car {
    private long id;
    private String number;
    private long idDriver;
    private String color;
    private String model;
    private boolean active;

    public long getId() {
        return id;
    }
    public String getNumber() {
        return number;
    }
    public long getIdDriver() {
        return idDriver;
    }
    public String getColor() {
        return color;
    }
    public String getModel() {
        return model;
    }
    public boolean isActive() {
        return active;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public void setIdDriver(long idDriver) {
        this.idDriver = idDriver;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) return false;
        Car car = (Car) o;
        return id == car.id &&
                idDriver == car.idDriver &&
                active == car.active &&
                Objects.equals(number, car.number) &&
                Objects.equals(color, car.color) &&
                Objects.equals(model, car.model);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, number, idDriver, color, model, active);
    }
}
