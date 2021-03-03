package com.cognizant.garage.service;

import com.cognizant.garage.entity.CarInOrder;
import com.cognizant.garage.entity.User;

public interface CarInOrderService {
    void update(String itemId, Integer quantity, User user);
    CarInOrder findOne(String itemId, User user);
}
