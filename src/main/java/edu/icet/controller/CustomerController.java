package edu.icet.controller;

import edu.icet.dto.BookACar;
import edu.icet.dto.Car;
import edu.icet.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@CrossOrigin
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/cars")
    public ResponseEntity<List<Car>> getAllCars(){
        List<Car>carList =customerService.getAllCars();
        return ResponseEntity.ok(carList);
    }

    @PostMapping("/car/book")
    public ResponseEntity<Void>bookACar(@RequestBody BookACar bookACar){
        System.out.println(bookACar);
        boolean success = customerService.bookACar(bookACar);
        if (success)return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @GetMapping("/car/{carId}")
    public ResponseEntity<Car> getCarById(@PathVariable Long carId){
        Car car = customerService.getCarById(carId);
        if (car==null)return ResponseEntity.notFound().build();
        return ResponseEntity.ok(car);
    }

    @GetMapping("/car/booking/{userId}")
    public ResponseEntity<List<BookACar>> getBookingByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(customerService.getBookingByUserId(userId));
    }
}
