package edu.icet.service;

import edu.icet.dto.BookACar;
import edu.icet.dto.Car;
import edu.icet.dto.CarList;
import edu.icet.dto.SearchCar;

import java.util.List;

public interface CustomerService {
    List<Car> getAllCars();

    boolean bookACar(BookACar bookACar);

    Car getCarById(Long id);

    List<BookACar> getBookingByUserId(Long user);

    CarList searchCar(SearchCar searchCar);
}
