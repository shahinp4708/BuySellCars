package com.buysellcars.dto;

import com.buysellcars.enums.BidStatus;
import lombok.Data;

@Data
public class BidDto {
    private long id;
    private double price;
    private BidStatus bidStatus;

    private long userId;
    private long carId;
    private String carName;
    private String carBrand;
    private String email;
    private String username;
    private String sellerName;
}
