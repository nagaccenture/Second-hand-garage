package com.cognizant.garage.repository;

import com.cognizant.garage.entity.CarInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarInfoRepository extends JpaRepository<CarInfo, String> {
    CarInfo findByCarId(String id);


    Page<CarInfo> findAllByOrderByAddedDateAsc(Pageable pageable);
}
