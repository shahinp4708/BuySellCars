package com.buysellcars.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class CarDto {
    private Long id;

    private  String name;
    private String brand;
    private String type;
    private String transmission;
    private String color;
    private Boolean sold;
    private Double price;
    private String description;

    private MultipartFile img;
    private Long userId;
    private byte[] returnedImg;
}
