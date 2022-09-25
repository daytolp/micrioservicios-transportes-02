package com.daytolp.userservice.feignClients;

import com.daytolp.userservice.model.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "car-service", url = "http://localhost:8002")
//@RequestMapping("/car")
public interface CarFeignClient {
//    @GetMapping("/byuser/{userId}")
//    public ResponseEntity<List<Car>> getByUserId(@PathVariable("userId") int userId);
    @PostMapping("/car")
    Car save(@RequestBody Car car);

    @GetMapping("/byuser/{userId}")
    List<Car> getCars(@PathVariable("userId") int userId);

}
