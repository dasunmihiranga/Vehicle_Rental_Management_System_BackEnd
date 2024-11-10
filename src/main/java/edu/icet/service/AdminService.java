package edu.icet.service;

import edu.icet.dto.BookACar;
import edu.icet.dto.Car;
import edu.icet.dto.CarList;
import edu.icet.dto.SearchCar;

import java.io.IOException;
import java.util.List;

public interface AdminService {
    boolean postCar(Car car);

    List<Car>getAllCars();

    void deleteCar(Long id);

    Car getCarById(Long id);

    boolean updateCar(Long carId,Car car) throws IOException;

    List<BookACar>getBookings();

    boolean changeBookingStatus(Long bookingId,String status);

    CarList searchCar(SearchCar searchCar);
}
