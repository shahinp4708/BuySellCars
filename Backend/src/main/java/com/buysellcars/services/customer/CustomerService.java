package com.buysellcars.services.customer;

import com.buysellcars.dto.AnalyticsDto;
import com.buysellcars.dto.BidDto;
import com.buysellcars.dto.CarDto;
import com.buysellcars.dto.SearchCarDto;

import java.io.IOException;
import java.util.List;

public interface CustomerService {

    boolean createCar(CarDto carDto) throws IOException;
    List<CarDto> getAllCars();

    CarDto getCarById(long id);

    void deleteCar(long id);

    boolean updateCar(long id , CarDto carDto)  throws IOException;

    List<CarDto>  searchCar(SearchCarDto searchCarDto);

    List<CarDto>  getMyCars(long id);

    boolean bidACar(BidDto bidDto);

    List<BidDto> getBidsByUserId(long userId);

    List<BidDto> getBidsByCarId(long carId);
    boolean changeBidStatus(long id, String status);

    AnalyticsDto getAnalytics(long userId);


}
