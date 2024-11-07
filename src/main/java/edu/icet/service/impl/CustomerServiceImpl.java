package edu.icet.service.impl;

import edu.icet.dto.BookACar;
import edu.icet.dto.Car;
import edu.icet.entity.BookACarEntity;
import edu.icet.entity.CarEntity;
import edu.icet.entity.UserEntity;
import edu.icet.repository.BookACarRepository;
import edu.icet.repository.CarRepository;
import edu.icet.repository.UserRepository;
import edu.icet.service.CustomerService;
import edu.icet.util.BookCarStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final BookACarRepository bookACarRepository;

    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll().stream().map(CarEntity::getCar).collect(Collectors.toList());
    }

    @Override
    public boolean bookACar(BookACar bookACar) {
        Optional<CarEntity>optionalCar = carRepository.findById(bookACar.getCarId());
        Optional<UserEntity> optionalUser =userRepository.findById(bookACar.getUserId());
        if (optionalCar.isPresent() && optionalUser.isPresent()){
            CarEntity existingCar=optionalCar.get();
            BookACarEntity bookACarEntity = new BookACarEntity();
            bookACarEntity.setUser(optionalUser.get());
            bookACarEntity.setCar(existingCar);
            bookACarEntity.setBookCarStatus(BookCarStatus.PENDING);

            long diffInMilliSeconds = bookACar.getToDate().getTime() - bookACar.getFromDate().getTime();
            long days= TimeUnit.MICROSECONDS.toDays(diffInMilliSeconds);
            bookACarEntity.setDays(days);
            bookACarEntity.setPrice(existingCar.getPrice() * days);

            bookACarRepository.save(bookACarEntity);
            return true;
        }

        return false;
    }

    @Override
    public Car getCarById(Long id) {
        Optional<CarEntity>optionalCar =carRepository.findById(id);
        return optionalCar.map(CarEntity::getCar).orElse(null);
    }
}
