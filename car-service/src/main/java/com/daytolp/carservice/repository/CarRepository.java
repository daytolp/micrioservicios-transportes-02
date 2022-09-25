package com.daytolp.carservice.repository;

import com.daytolp.carservice.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer> {

    List<Car> findByUserId(int userId);
}