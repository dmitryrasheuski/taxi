package service.interfaces.car;

import entity.car.Car;

public interface ICarActivating {
    /**
     * This method add car to WorkingCar. The car most have id.
     * @param car The car is instance of "Car" class in which the 'id' field must be set
     * @return true if 'workingCar' changed as a result of the call
     */
    boolean activateCar(Car car);
    /**
     * This method remove car from WorkingCar. The car most have id.
     * @param car The car is instance of "Car" class in which the 'id' field must be set
     * @return true if 'workingCar' changed as a result of the call
     */
    boolean deactivateCar(Car car);
}
