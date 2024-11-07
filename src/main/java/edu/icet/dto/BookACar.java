package edu.icet.dto;

import edu.icet.util.BookCarStatus;
import lombok.Data;

import java.util.Date;
@Data
public class BookACar {
    private Long id;
    private Date fromDate;
    private Date toDate;
    private Long days;
    private Long price;
    private BookCarStatus bookCarStatus;
    private Long userId;
    private Long carId;

}
