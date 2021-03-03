package com.cognizant.garage.service.impl;


import com.cognizant.garage.service.CarService;
import com.cognizant.garage.entity.CarInfo;
import com.cognizant.garage.repository.CarInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class CarServiceImpl implements CarService {

    @Autowired
    CarInfoRepository carInfoRepository;

    @Override
    public CarInfo findOne(String carId) {

        CarInfo carInfo = carInfoRepository.findByCarId(carId);
        return carInfo;
    }

    @Override
    public Page<CarInfo> findAll(Pageable pageable) {
        return carInfoRepository.findAllByOrderByAddedDateAsc(pageable);
    }
    
}
