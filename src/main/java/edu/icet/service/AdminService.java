package edu.icet.service;

import edu.icet.dto.Car;

import java.util.List;

public interface AdminService {
    boolean postCar(Car car);

    List<Car>getAllCars();

    void deleteCar(Long id);

    Car getCarById(Long id);
}
