package com.buysellcars.entities;

import com.buysellcars.dto.BidDto;
import com.buysellcars.enums.BidStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;
    private double price;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "car_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Car car;

    private BidStatus bidStatus;

    public BidDto getBidDto(){
        BidDto bidDto = new BidDto();
        bidDto.setId(id);
        bidDto.setPrice(price);
        bidDto.setUserId(user.getId());
        bidDto.setCarId(car.getId());
        bidDto.setBidStatus(bidStatus);
        bidDto.setCarName(car.getName());
        bidDto.setUsername(user.getName());
        bidDto.setEmail(user.getEmail());
        bidDto.setSellerName(car.getUser().getName());
        return bidDto;
    }

}
