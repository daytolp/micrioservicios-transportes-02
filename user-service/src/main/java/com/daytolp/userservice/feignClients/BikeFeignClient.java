package com.daytolp.userservice.feignClients;

import com.daytolp.userservice.model.Bike;
import com.daytolp.userservice.model.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "bike-service", url = "http://localhost:8003")
//@RequestMapping("/bike")
public interface BikeFeignClient {
  //  @GetMapping("/byuser/{userId}")
   // public ResponseEntity<List<Bike>> getByUserId(@PathVariable("userId") int userId);
  @PostMapping("/bike")
  Bike save(@RequestBody Bike bike);

  @GetMapping("/byuser/{userId}")
  List<Bike> getBikes(@PathVariable("userId") int userId);
}
