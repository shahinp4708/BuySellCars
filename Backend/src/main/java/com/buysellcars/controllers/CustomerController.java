package com.buysellcars.controllers;


import com.buysellcars.dto.BidDto;
import com.buysellcars.dto.CarDto;
import com.buysellcars.dto.SearchCarDto;
import com.buysellcars.services.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("api/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/car")
    public ResponseEntity<?> addCar(@ModelAttribute CarDto carDto) throws IOException {
        boolean success = customerService.createCar(carDto);
        if(success) {
            return ResponseEntity.ok("Car added successfully.");
        }
        return ResponseEntity.badRequest().body("Failed to add car.");
    }

    @GetMapping("/cars")
    public ResponseEntity<?> getAllCars() {
        return ResponseEntity.ok(customerService.getAllCars());
    }

    @GetMapping("/car/{id}")
    public ResponseEntity<?> getCarById(@PathVariable long id) {
        return ResponseEntity.ok(customerService.getCarById(id));
    }
    @DeleteMapping("/car/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable long id) {
        customerService.deleteCar(id);
        return ResponseEntity.ok(null);
    }

    @PutMapping("/car/{id}")
    public ResponseEntity<?> updateCar(@PathVariable long id, @ModelAttribute  CarDto carDto) throws IOException {
        boolean success = customerService.updateCar(id, carDto);
        if(success) {
            return ResponseEntity.ok("Car updated successfully.");
        }
        return ResponseEntity.badRequest().body("Failed to update car.");
    }

    @PostMapping("/car/search")
    public ResponseEntity<?> searchCar(@RequestBody SearchCarDto searchCarDto) {
        return ResponseEntity.ok(customerService.searchCar(searchCarDto));
    }

    @GetMapping("/my-cars/{id}")
    public ResponseEntity<List<CarDto>> getMyCars(@PathVariable long id) {
        return ResponseEntity.ok(customerService.getMyCars(id));
    }

    @PostMapping("/car/bid")
    public ResponseEntity<?> bidACar(@RequestBody BidDto bidDto)  {
        boolean success = customerService.bidACar(bidDto);
        if(success) {
            return ResponseEntity.ok("Car added successfully.");
        }
        return ResponseEntity.badRequest().body("Failed to add car.");
    }
    @GetMapping("/car/bids/{id}")
    public ResponseEntity<List<BidDto>> getBidsByUserId(@PathVariable long id) {
        return ResponseEntity.ok(customerService.getBidsByUserId(id));
    }
    @GetMapping("/car/{id}/bids")
    public ResponseEntity<List<BidDto>> getBidsByCarId(@PathVariable long id) {
        return ResponseEntity.ok(customerService.getBidsByCarId(id));
    }
    @PutMapping("/car/{id}/{status}")
    public ResponseEntity<?> changeBidStatus(@PathVariable long id, @PathVariable String status) {
        boolean success= customerService.changeBidStatus(id, status);
        if(success)
            return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/car/analytics/{id}")
    public ResponseEntity<?> getAnalytics(@PathVariable long id) {
        return ResponseEntity.ok(customerService.getAnalytics(id));
    }
}
