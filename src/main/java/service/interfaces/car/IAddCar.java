package service.interfaces.car;

import entity.car.Car;

import java.util.Optional;

public interface IAddCar {
    /**
     * @return If the operation successful complete then return current car as which the 'id' field was installed, otherwise return empty Optional
     */
    Optional<Car> addCar(Car car);
}
