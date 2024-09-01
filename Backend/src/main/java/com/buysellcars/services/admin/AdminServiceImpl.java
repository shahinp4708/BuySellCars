package com.buysellcars.services.admin;


import com.buysellcars.dto.BidDto;
import com.buysellcars.dto.CarDto;
import com.buysellcars.dto.SearchCarDto;
import com.buysellcars.entities.Bid;
import com.buysellcars.entities.Car;
import com.buysellcars.enums.BidStatus;
import com.buysellcars.repositories.BidRepository;
import com.buysellcars.repositories.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final CarRepository carRepository;
    private final BidRepository bidRepository;
    public List<CarDto> getAllCars() {
        return carRepository.findAll().stream().map(Car::getCarDto).collect(Collectors.toList());
    }

    @Override
    public CarDto getCarById(long id) {
        Optional<Car> optionalCar = carRepository.findById(id);
        return optionalCar.map(Car::getCarDto).orElse(null);
    }

    @Override
    public void deleteCar(long id) {
        carRepository.deleteById(id);
    }
    public boolean updateCar(long id,CarDto carDto) throws IOException {
        Optional<Car> optionalCar = carRepository.findById(id);
        if(optionalCar.isPresent()){
            Car car = optionalCar.get();
            car.setName(carDto.getName());
            car.setDescription(carDto.getDescription());
            car.setColor(carDto.getColor());
            if(carDto.getImg()!=null)
                car.setImg(carDto.getImg().getBytes());
            car.setBrand(carDto.getBrand());
            car.setType(carDto.getType());
            car.setPrice(carDto.getPrice());
            carRepository.save(car);
            return true;
        }
        return false;
    }
    public List<CarDto>  searchCar(SearchCarDto searchCarDto){
        Car car = new Car();
        car.setType(searchCarDto.getType());
        car.setColor(searchCarDto.getColor());
        car.setTransmission(searchCarDto.getTransmission());
        car.setBrand(searchCarDto.getBrand());

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
                .withMatcher("brand",ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("color",ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("transmission",ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("type",ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
        Example<Car> carExample = Example.of(car,exampleMatcher);
        List<Car> cars= carRepository.findAll(carExample);
        return cars.stream().map(Car::getCarDto).collect(Collectors.toList());
    }

    @Override
    public List<BidDto> getBids() {
        return bidRepository.findAll().stream().map(Bid::getBidDto).collect(Collectors.toList());

    }

    @Override
    public boolean changeBidStatus(long id, String status) {
        Optional<Bid> optionalBid= bidRepository.findById(id);
        if(optionalBid.isPresent()){
            Bid existingBid= optionalBid.get();
            if(Objects.equals(status,"ACCEPTED")){
                existingBid.setBidStatus(BidStatus.ACCEPTED);
            }
            else if(Objects.equals(status,"REJECTED")){
                existingBid.setBidStatus(BidStatus.REJECTED);
            }
            bidRepository.save(existingBid);
            return true;
        }
        return false;
    }
}
