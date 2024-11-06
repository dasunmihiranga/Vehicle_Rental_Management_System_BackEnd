package edu.icet.service.impl;


import edu.icet.dto.Car;
import edu.icet.entity.CarEntity;
import edu.icet.repository.CarRepository;
import edu.icet.service.AdminService;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AdminServiceImpl implements AdminService {
    private final CarRepository carRepository;

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
}
