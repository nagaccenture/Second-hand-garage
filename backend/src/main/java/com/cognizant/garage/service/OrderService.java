package com.cognizant.garage.service;


import com.cognizant.garage.entity.OrderMain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Page<OrderMain> findAll(Pageable pageable);

    Page<OrderMain> findByBuyerEmail(String email, Pageable pageable);

    OrderMain findOne(Long orderId);
    
}
