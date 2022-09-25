package com.daytolp.userservice.controller;

import com.daytolp.userservice.entity.User;
import com.daytolp.userservice.model.Bike;
import com.daytolp.userservice.model.Car;
import com.daytolp.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        List<User> users = userService.getAll();
        if(users.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") int id) {
        User user = userService.getUserById(id);
        if(user == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }

    @PostMapping()
    public ResponseEntity<User> save(@RequestBody User user) {
        User userNew = userService.save(user);
        return ResponseEntity.ok(userNew);
    }

    @GetMapping("/cars/{userId}")
    public ResponseEntity<List<Car>> getCars(@PathVariable Integer userId) {
        User user = userService.getUserById(userId);
        if(user == null)
            return ResponseEntity.notFound().build();
        List<Car> cars = userService.getCars(userId);
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/bikes/{userId}")
    public ResponseEntity<List<Bike>> getBikes(@PathVariable Integer userId) {
        User user = userService.getUserById(userId);
        System.out.println(user +"--------");
        if(user == null)
            return ResponseEntity.notFound().build();
        List<Bike> bikes = userService.getBikes(userId);
        return ResponseEntity.ok(bikes);
    }

    @PostMapping("/savecar/{userId}")
    public ResponseEntity<Car> saveCar(@PathVariable Integer userId, @RequestBody Car car) {
        if (userService.getUserById(userId) == null)
            ResponseEntity.notFound().build();
        Car carNew = userService.saveCar(userId, car);
        return ResponseEntity.status(HttpStatus.CREATED).body(carNew);
    }

    @PostMapping("/savebike/{userId}")
    public ResponseEntity<Bike> saveCar(@PathVariable Integer userId, @RequestBody Bike bike) {
        if (userService.getUserById(userId) == null)
            ResponseEntity.notFound().build();
        Bike bikeNew = userService.saveBike(userId, bike);
        return ResponseEntity.status(HttpStatus.CREATED).body(bikeNew);
    }

    @GetMapping("/getAll/{userId}")
    public ResponseEntity<Map<String, Object>> getAllVehicles(@PathVariable("userId") int userId) {
        Map<String, Object> result = userService.getUserAndVehicles(userId);
        return ResponseEntity.ok(result);
    }
}
