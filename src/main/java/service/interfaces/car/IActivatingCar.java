package service.interfaces.car;

import entity.car.Car;

public interface IActivatingCar {
    /**
     * This method add car to WorkingCar. The car most have id.
     * @param car The car is instance of "Car" class in which the 'id' field must be set
     * @return true if 'workingCar' changed as a result of the call
     */
    boolean carActivation(Car car);
    /**
     * This method remove car to WorkingCar. The car most have id.
     * @param car The car is instance of "Car" class in which the 'id' field must be set
     * @return true if 'workingCar' changed as a result of the call
     */
    boolean carDeactivation(Car car);
}
