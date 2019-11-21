package service.interfaces;

import appException.service.AppServiceException;
import entity.car.Car;

public interface IGetCar {
    Car getCar(long idCar) throws AppServiceException;
}
