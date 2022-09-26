package com.daytolp.userservice.service;


import com.daytolp.userservice.entity.User;
import com.daytolp.userservice.feignClients.BikeFeignClient;
import com.daytolp.userservice.feignClients.CarFeignClient;
import com.daytolp.userservice.model.Bike;
import com.daytolp.userservice.model.Car;
import com.daytolp.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    CarFeignClient carFeignClient;
    @Autowired
    BikeFeignClient bikeFeignClient;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public User save(User user) {
        User userNew = userRepository.save(user);
        return userNew;
    }

    public List getCars(Integer userId) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwt.getTokenValue());
        ResponseEntity<List>  cars = this.restTemplate.exchange("http://car-service/car/byuser/" +userId, HttpMethod.GET, new HttpEntity<>(httpHeaders), List.class);
        return  cars.getBody();
    }

    public List getBikes(Integer userId) {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwt.getTokenValue());
        ResponseEntity<List> bikes = this.restTemplate.exchange("http://bike-service/bike/byuser/" +userId, HttpMethod.GET, new HttpEntity<>(httpHeaders), List.class);
        return  bikes.getBody();
    }

    public Car saveCar(Integer userId, Car car) {
        car.setUserId(userId);
        Car carNew = this.carFeignClient.save(car);
        return  carNew;
    }

    public Bike saveBike(Integer userId, Bike bike) {
        bike.setUserId(userId);
        Bike bikeNew = this.bikeFeignClient.save(bike);
        return  bikeNew;
    }


    public Map<String, Object> getUserAndVehicles(int userId) {
        Map<String, Object> result = new HashMap<>();
        User user = userRepository.findById(userId).orElse(null);
        if(user == null) {
            result.put("Mensaje", "no existe el usuario");
            return result;
        }
        result.put("User", user);
        List<Car> cars = carFeignClient.getCars(userId);
        if(cars.isEmpty())
            result.put("Cars", "ese user no tiene coches");
        else
            result.put("Cars", cars);
        List<Bike> bikes = bikeFeignClient.getBikes(userId);
        if(bikes.isEmpty())
            result.put("Bikes", "ese user no tiene motos");
        else
            result.put("Bikes", bikes);
        return result;
    }
}
