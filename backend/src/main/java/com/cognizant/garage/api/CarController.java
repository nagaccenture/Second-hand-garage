package com.cognizant.garage.api;

import com.cognizant.garage.entity.CarInfo;
import com.cognizant.garage.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    CarService carService;

    @GetMapping("")
    public Page<CarInfo> findAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "size", defaultValue = "3") Integer size) {
        PageRequest request = PageRequest.of(page - 1, size);
        return carService.findAll(request);
    }

    @GetMapping("/car/{carId}")
    public CarInfo showOne(@PathVariable("carId") String carId) {

        CarInfo carInfo = carService.findOne(carId);

        return carInfo;
    }



}
