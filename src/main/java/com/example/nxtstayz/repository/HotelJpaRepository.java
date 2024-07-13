package com.example.nxtstayz.repository;

import com.example.nxtstayz.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelJpaRepository extends JpaRepository<Hotel, Integer> {

}