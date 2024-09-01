package com.buysellcars.entities;

import com.buysellcars.dto.CarDto;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@Data
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  String name;
    private String brand;
    private String type;
    private String transmission;
    private String color;
    private Double price;
    private Boolean sold;
    @Lob
    private String description;
    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;


    public CarDto getCarDto() {
        CarDto carDto= new CarDto();
        carDto.setId(id);
        carDto.setName(name);
        carDto.setBrand(brand);
        carDto.setType(type);
        carDto.setTransmission(transmission);
        carDto.setColor(color);
        carDto.setSold(sold);
        carDto.setDescription(description);
        carDto.setPrice(price);
        carDto.setReturnedImg(img);
        return carDto;

    }
}
