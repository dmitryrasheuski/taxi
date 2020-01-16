package service.impl.car;

import dao.interfaces.CarDao;
import entity.car.Car;
import entity.car.WorkingCars;
import lombok.extern.log4j.Log4j;
import service.interfaces.car.ICarActivating;
import service.interfaces.car.ICarRegistering;

import java.sql.SQLException;
import java.util.Optional;

@Log4j
public class CarService implements ICarRegistering, ICarActivating {
    private CarDao dao;
    private WorkingCars workingCars;

    public CarService(CarDao dao, WorkingCars workingCars) {
        this.dao = dao;
        this.workingCars = workingCars;
    }

    @Override
    public boolean activateCar(Car car) {
        if (car == null || car.getId() == null ) return false;

        return workingCars.addCar(car);
    }

    @Override
    public boolean deactivateCar(Car car) {
        return workingCars.removeCar(car);
    }

    @Override
    public Optional<Car> registerCar(Car car) {
        if( ! validateCar(car) ){
            log.info("Car has been validated: " + car);
            return Optional.empty();
        }

        Optional<Long> id = Optional.empty();
        try {
            id = dao.addCar(car);
        } catch (SQLException ex) {
            log.error("Adding car: " + car, ex);
        }
        if ( ! id.isPresent() ) return Optional.empty();

        car.setId( id.get() );
        return Optional.of(car);

    }

    private boolean validateCar(Car car) {
        if ( car == null ) return false;
        if ( car.getModel() == null ) return false;
        if ( car.getColor() == null ) return false;
        if ( car.getNumber() == null ) return false;
        if ( car.getDriver() == null ) return false;

        return true;
    }
}
