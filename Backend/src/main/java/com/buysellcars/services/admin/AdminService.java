package com.buysellcars.services.admin;

import com.buysellcars.dto.BidDto;
import com.buysellcars.dto.CarDto;
import com.buysellcars.dto.SearchCarDto;

import java.io.IOException;
import java.util.List;

public interface AdminService {
    List<CarDto> getAllCars();
    CarDto getCarById(long id);

    void deleteCar(long id);
    boolean updateCar(long id , CarDto carDto)  throws IOException;
    List<CarDto> searchCar(SearchCarDto searchCarDto);
    List<BidDto> getBids();

    boolean changeBidStatus(long id,String status);
}

