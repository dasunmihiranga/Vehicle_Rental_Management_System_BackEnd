package edu.icet.entity;


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
}
