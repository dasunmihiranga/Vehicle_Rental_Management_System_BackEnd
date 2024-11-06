package edu.icet.entity;


import edu.icet.dto.Car;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "cars")

public class CarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String brand;

    private  String colour;

    private String name;

    private String type;

    private String transmission;

    private String description;

    private Long price;

    private String year;

    @Column(columnDefinition = "longblob")
    private byte[] image;

    public Car getCar(){
        Car car =new Car();
        car.setId(id);
        car.setName(name);
        car.setBrand(brand);
        car.setColour(colour);
        car.setPrice(price);
        car.setDescription(description);
        car.setType(type);
        car.setTransmission(transmission);
        car.setYear(year);
        car.setReturnedImage(image);

        return car;
    }
}
