package com.buysellcars.repositories;

import com.buysellcars.entities.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<Bid,Long> {
    List<Bid> findAllByUserId(long userId);
    List<Bid> findAllByCarId(long carId);
}
