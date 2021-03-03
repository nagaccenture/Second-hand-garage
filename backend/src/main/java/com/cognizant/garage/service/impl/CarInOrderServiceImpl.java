package com.cognizant.garage.service.impl;

import com.cognizant.garage.entity.CarInOrder;
import com.cognizant.garage.entity.User;
import com.cognizant.garage.repository.CarInOrderRepository;
import com.cognizant.garage.service.CarInOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicReference;

@Service
public class CarInOrderServiceImpl implements CarInOrderService {

    @Autowired
    CarInOrderRepository carInOrderRepository;

    @Override
    @Transactional
    public void update(String itemId, Integer quantity, User user) {
        var op = user.getCart().getCarInOrders().stream().filter(e -> itemId.equals(e.getCarId())).findFirst();
        op.ifPresent(productInOrder -> {
            productInOrder.setCount(quantity);
            carInOrderRepository.save(productInOrder);
        });

    }

    @Override
    public CarInOrder findOne(String itemId, User user) {
        var op = user.getCart().getCarInOrders().stream().filter(e -> itemId.equals(e.getCarId())).findFirst();
        AtomicReference<CarInOrder> res = new AtomicReference<>();
        op.ifPresent(res::set);
        return res.get();
    }
}
