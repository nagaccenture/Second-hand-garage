package com.cognizant.garage.repository;

import com.cognizant.garage.entity.CarInOrder;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CarInOrderRepository extends JpaRepository<CarInOrder, Long> {

}
