package entity.car;

import java.util.ArrayList;
import java.util.List;

public class WorkingCars {
    private static final List<Car> list = new ArrayList<>();
    private static final WorkingCars INSTANCE = new WorkingCars();

    private WorkingCars(){}
    public static WorkingCars getInstance(){
        return INSTANCE;
    }

    public boolean addCar(Car car) {
        if (car == null) throw new IllegalArgumentException("Car is null");
        return list.add(car);
    }
    public boolean removeCar(Car car) {
        if (car == null) throw new IllegalArgumentException("Car is null");
        return list.remove(car);
    }
}
