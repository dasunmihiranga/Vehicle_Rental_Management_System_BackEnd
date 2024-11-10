package edu.icet.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SearchCar {
    private String brand;
    private String type;
    private String transmission;
    private String colour;
}
