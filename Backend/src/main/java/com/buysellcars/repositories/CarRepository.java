package com.buysellcars.repositories;

import com.buysellcars.dto.CarDto;
import com.buysellcars.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car,Long> {
    List<Car> findAllByUserId(long userId);

    long countByUserIdAndSoldTrue(long userId);

    long countByUserId(long userId);
}
