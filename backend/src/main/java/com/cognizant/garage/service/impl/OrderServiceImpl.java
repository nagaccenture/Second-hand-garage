package com.cognizant.garage.service.impl;


import com.cognizant.garage.entity.OrderMain;
import com.cognizant.garage.entity.CarInOrder;
import com.cognizant.garage.entity.CarInfo;
import com.cognizant.garage.enums.OrderStatusEnum;
import com.cognizant.garage.enums.ResultEnum;
import com.cognizant.garage.exception.MyException;
import com.cognizant.garage.repository.OrderRepository;
import com.cognizant.garage.repository.CarInOrderRepository;
import com.cognizant.garage.repository.CarInfoRepository;
import com.cognizant.garage.repository.UserRepository;
import com.cognizant.garage.service.OrderService;
import com.cognizant.garage.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CarInfoRepository carInfoRepository;
    @Autowired
    CarService carService;
    @Autowired
    CarInOrderRepository carInOrderRepository;

    @Override
    public Page<OrderMain> findAll(Pageable pageable) {
        return orderRepository.findAllByOrderByOrderStatusAscCreateTimeDesc(pageable);
    }

    @Override
    public Page<OrderMain> findByBuyerEmail(String email, Pageable pageable) {
        return orderRepository.findAllByBuyerEmailOrderByOrderStatusAscCreateTimeDesc(email, pageable);
    }


    @Override
    public OrderMain findOne(Long orderId) {
        OrderMain orderMain = orderRepository.findByOrderId(orderId);
        if(orderMain == null) {
            throw new MyException(ResultEnum.ORDER_NOT_FOUND);
        }
        return orderMain;
    }

    
}
