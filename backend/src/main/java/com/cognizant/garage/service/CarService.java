package com.cognizant.garage.service;


import com.cognizant.garage.entity.CarInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CarService {

    CarInfo findOne(String carId);


    Page<CarInfo> findAll(Pageable pageable);


}
