package edu.icet.controller;

import edu.icet.dto.Car;
import edu.icet.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/vehicle")
    public ResponseEntity<?>postCar(@ModelAttribute Car car){
        System.out.println(car);
        boolean success=adminService.postCar(car);
        System.out.println(success);
        if (success){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }
    @GetMapping("/vehicles")
    public ResponseEntity<?>getAllCars(){
        return ResponseEntity.ok(adminService.getAllCars());
    }
    @DeleteMapping("/vehicle/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id){
        adminService.deleteCar(id);
        return ResponseEntity.ok(null);
    }
    @GetMapping("/vehicle/{id}")
    public ResponseEntity<Car>getCarById(@PathVariable Long id){
        Car car =adminService.getCarById(id);
        return ResponseEntity.ok(car);
    }


}
