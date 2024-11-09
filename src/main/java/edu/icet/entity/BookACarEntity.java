package edu.icet.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.icet.dto.BookACar;
import edu.icet.util.BookCarStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@Data
public class BookACarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date fromDate;
    private Date toDate;
    private Long days;
    private Long price;
    private BookCarStatus bookCarStatus;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="user_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private UserEntity user;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="car_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private CarEntity car;

    public BookACar getBookACar(){
        BookACar bookACar=new BookACar();
        bookACar.setId(id);
        bookACar.setDays(days);
        bookACar.setBookCarStatus(bookCarStatus);
        bookACar.setPrice(price);
        bookACar.setToDate(toDate);
        bookACar.setFromDate(fromDate);
        bookACar.setEmail(user.getEmail());
        bookACar.setUsername(user.getName());
        bookACar.setUserId(user.getId());
        bookACar.setCarId(car.getId());


        return bookACar;
    }


}
