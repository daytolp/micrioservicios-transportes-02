package com.daytolp.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Car {
    private Integer id;
    private String brand;
    private String model;
    private Integer userId;
}
