package edu.icet.service.impl;


import edu.icet.dto.BookACar;
import edu.icet.dto.Car;
import edu.icet.entity.BookACarEntity;
import edu.icet.entity.CarEntity;
import edu.icet.repository.BookACarRepository;
import edu.icet.repository.CarRepository;
import edu.icet.service.AdminService;
import edu.icet.util.BookCarStatus;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class AdminServiceImpl implements AdminService {
    private final CarRepository carRepository;
    private final BookACarRepository bookACarRepository;

    @Override
    public boolean postCar(Car car) throws IOException {
        try {
            CarEntity carEntity=new CarEntity();
            carEntity.setName(car.getName());
            carEntity.setBrand(car.getBrand());
            carEntity.setColour(car.getColour());
            carEntity.setPrice(car.getPrice());
            carEntity.setYear(car.getYear());
            carEntity.setType(car.getType());
            carEntity.setDescription(car.getDescription());
            carEntity.setTransmission(car.getTransmission());
            carEntity.setImage(car.getImage().getBytes());
            carRepository.save(carEntity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll().stream().map(CarEntity::getCar).collect(Collectors.toList());
    }

    @Override
    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public Car getCarById(Long id) throws IOException{
        Optional<CarEntity> optionalCar = carRepository.findById(id);
        return optionalCar.map(CarEntity::getCar).orElse(null);
    }

    @Override
    public boolean updateCar(Long carId, Car car) throws java.io.IOException {
        Optional<CarEntity> optionalCar = carRepository.findById(carId);
        if (optionalCar.isPresent()){
            CarEntity existingCar =optionalCar.get();
            if (car.getImage()!=null)
                existingCar.setImage(car.getImage().getBytes());
            existingCar.setPrice(car.getPrice());
            existingCar.setYear(car.getYear());
            existingCar.setType(car.getType());
            existingCar.setDescription(car.getDescription());
            existingCar.setTransmission(car.getTransmission());
            existingCar.setColour(car.getColour());
            existingCar.setName(car.getName());
            existingCar.setBrand(car.getBrand());

            carRepository.save(existingCar);
            return true;
        }
        return false;

    }

    @Override
    public List<BookACar> getBookings() {
        return bookACarRepository.findAll().stream().map(BookACarEntity::getBookACar).collect(Collectors.toList());
    }

    @Override
    public boolean changeBookingStatus(Long bookingId, String status) {
        System.out.println(bookingId+"  "+status);
        Optional<BookACarEntity>optionalBookACarEntity =bookACarRepository.findById(bookingId);
        if (optionalBookACarEntity.isPresent()){
            BookACarEntity existingBookACar = optionalBookACarEntity.get();
            if (status.equals("Approve")){
                existingBookACar.setBookCarStatus(BookCarStatus.APPROVED);
            }else if (status.equals("Rejects")){
                existingBookACar.setBookCarStatus(BookCarStatus.REJECTED);

            }
            bookACarRepository.save(existingBookACar);
            return true;
        }
        return false;
    }
}
