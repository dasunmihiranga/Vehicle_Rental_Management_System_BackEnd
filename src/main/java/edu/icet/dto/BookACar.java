package edu.icet.dto;

import edu.icet.util.BookCarStatus;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
@Data
@ToString
public class BookACar {
    private Long id;
    private Date fromDate;
    private Date toDate;
    private Long days;
    private Long price;
    private BookCarStatus bookCarStatus;
    private Long userId;
    private Long carId;
    private String username;
    private String email;

}
