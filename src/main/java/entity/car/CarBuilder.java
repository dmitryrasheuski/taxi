package entity.car;

public class CarBuilder {
    private Car car;

    private CarBuilder(){
        car = new Car();
    }
    static public CarBuilder createCar(){
        return new CarBuilder();
    }

    public CarBuilder setId(long id){
        car.setId(id);
        return this;

    }
    public CarBuilder setNumber(String number){
        car.setNumber(number);
        return this;

    }
    public CarBuilder setIdDriver(long idDriver){
        car.setIdDriver(idDriver);
        return this;

    }
    public CarBuilder setColor(String color){
        car.setColor(color);
        return this;

    }
    public CarBuilder setModel(String model){
        car.setModel(model);
        return this;

    }
    public CarBuilder setStatus(boolean status){
        car.setStatus(status);
        return this;
    }

    public Car getCar(){
        return car;
    }
}
