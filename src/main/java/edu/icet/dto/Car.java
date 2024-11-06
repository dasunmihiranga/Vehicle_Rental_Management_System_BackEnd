package edu.icet.dto;

import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
@Data
@ToString
public class Car {
    private long id;

    private String brand;

    private  String colour;

    private String name;

    private String type;

    private String transmission;

    private String description;

    private Long price;

    private String year;

    private MultipartFile image;

    private byte[] returnedImage;
}
