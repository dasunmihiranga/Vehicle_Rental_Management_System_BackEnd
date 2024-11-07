package edu.icet.service.impl;

import edu.icet.dto.Car;
import edu.icet.entity.CarEntity;
import edu.icet.repository.CarRepository;
import edu.icet.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CarRepository carRepository;

    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll().stream().map(CarEntity::getCar).collect(Collectors.toList());
    }
}
