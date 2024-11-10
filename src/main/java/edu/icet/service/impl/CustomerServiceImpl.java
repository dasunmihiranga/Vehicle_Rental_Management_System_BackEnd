package edu.icet.service.impl;

import edu.icet.dto.BookACar;
import edu.icet.dto.Car;
import edu.icet.dto.CarList;
import edu.icet.dto.SearchCar;
import edu.icet.entity.BookACarEntity;
import edu.icet.entity.CarEntity;
import edu.icet.entity.UserEntity;
import edu.icet.repository.BookACarRepository;
import edu.icet.repository.CarRepository;
import edu.icet.repository.UserRepository;
import edu.icet.service.CustomerService;
import edu.icet.util.BookCarStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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
            bookACarEntity.setToDate(bookACar.getToDate());
            bookACarEntity.setFromDate(bookACar.getFromDate());

            long diffInMilliSeconds = bookACar.getToDate().getTime() - bookACar.getFromDate().getTime();
            long days = TimeUnit.MILLISECONDS.toDays(diffInMilliSeconds);
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

    @Override
    public List<BookACar> getBookingByUserId(Long userId) {
        return bookACarRepository.findAllByUserId(userId).stream().map(BookACarEntity::getBookACar).collect(Collectors.toList());
    }

    @Override
    public CarList searchCar(SearchCar searchCar) {
        System.out.println("Searching for car with criteria: " + searchCar);

        CarEntity carEntity = new CarEntity();

        carEntity.setBrand(searchCar.getBrand());
        carEntity.setType(searchCar.getType());
        carEntity.setTransmission(searchCar.getTransmission());
        carEntity.setColour(searchCar.getColour());


        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
                .withMatcher("brand", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("transmission", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("colour", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withIgnorePaths("id");

        Example<CarEntity> carEntityExample = Example.of(carEntity, exampleMatcher);
        List<CarEntity> carEntityList = carRepository.findAll(carEntityExample);

        CarList carList = new CarList();
        carList.setCarList(carEntityList.stream()
                .map(CarEntity::getCar) // Ensure getCar returns a Car instance
                .collect(Collectors.toList()));
        return carList;
    }

}
