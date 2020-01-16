package service.interfaces.car;

import entity.car.Car;

import java.util.Optional;

public interface ICarRegistering {
    /**
     * @param car this parameter must have got the not-null fields: number, color, model, driver
     * @return an Optional discrobing the current car at which the 'id' field was installed, if the operation successful complete, otherwise return empty Optional
     */
    Optional<Car> registerCar(Car car);
}
