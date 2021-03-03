package com.cognizant.garage.service;

import com.cognizant.garage.entity.CarInOrder;
import com.cognizant.garage.entity.User;
import com.cognizant.garage.entity.Cart;

import java.util.Collection;

public interface CartService {
    Cart getCart(User user);

    void mergeLocalCart(Collection<CarInOrder> carInOrders, User user);

    void delete(String itemId, User user);

    void checkout(User user);
}
