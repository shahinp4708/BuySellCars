package com.buysellcars.controllers;


import com.buysellcars.dto.BidDto;
import com.buysellcars.dto.CarDto;
import com.buysellcars.dto.SearchCarDto;
import com.buysellcars.services.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
public class AdminController {
    private  final AdminService adminService;
    @GetMapping("/cars")
    public ResponseEntity<?> getAllCars() {
        return ResponseEntity.ok(adminService.getAllCars());
    }

    @GetMapping("/car/{id}")
    public ResponseEntity<?> getCarById(@PathVariable long id) {
        return ResponseEntity.ok(adminService.getCarById(id));
    }

    @DeleteMapping("/car/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable long id) {
        adminService.deleteCar(id);
        return  ResponseEntity.ok(null);
    }
    @PutMapping("/car/{id}")
    public ResponseEntity<?> updateCar(@PathVariable long id, @ModelAttribute CarDto carDto) throws IOException {
        boolean success = adminService.updateCar(id, carDto);
        if(success) {
            return ResponseEntity.ok("Car updated successfully.");
        }
        return ResponseEntity.badRequest().body("Failed to update car.");
    }

    @PostMapping("/car/search")
    public ResponseEntity<?> searchCar(@RequestBody SearchCarDto searchCarDto) {
        return ResponseEntity.ok(adminService.searchCar(searchCarDto));
    }

    @GetMapping("/car/bids")
    public ResponseEntity<List<BidDto>> getBids() {
        return ResponseEntity.ok(adminService.getBids());
    }
    @PutMapping("/car/{id}/{status}")
    public ResponseEntity<?> changeBidStatus(@PathVariable long id, @PathVariable String status) {
        boolean success= adminService.changeBidStatus(id, status);
        if(success)
            return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }




}
