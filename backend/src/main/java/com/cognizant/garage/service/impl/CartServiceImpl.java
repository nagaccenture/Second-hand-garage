package com.cognizant.garage.service.impl;


import com.cognizant.garage.service.CarService;
import com.cognizant.garage.entity.Cart;
import com.cognizant.garage.entity.OrderMain;
import com.cognizant.garage.entity.CarInOrder;
import com.cognizant.garage.entity.User;
import com.cognizant.garage.repository.CartRepository;
import com.cognizant.garage.repository.OrderRepository;
import com.cognizant.garage.repository.CarInOrderRepository;
import com.cognizant.garage.repository.UserRepository;
import com.cognizant.garage.service.CartService;
import com.cognizant.garage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;


@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CarService carService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    CarInOrderRepository carInOrderRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    UserService userService;

    @Override
    public Cart getCart(User user) {
        return user.getCart();
    }

    @Override
    @Transactional
    public void mergeLocalCart(Collection<CarInOrder> carInOrders, User user) {
        Cart finalCart = user.getCart();
        carInOrders.forEach(carInOrder -> {
            Set<CarInOrder> set = finalCart.getCarInOrders();
            Optional<CarInOrder> old = set.stream().filter(e -> e.getCarId().equals(carInOrder.getCarId())).findFirst();
            CarInOrder prod;
            if (old.isPresent()) {
                prod = old.get();
                prod.setCount(carInOrder.getCount() + prod.getCount());
            } else {
                prod = carInOrder;
                prod.setCart(finalCart);
                finalCart.getCarInOrders().add(prod);
            }
            carInOrderRepository.save(prod);
        });
        cartRepository.save(finalCart);

    }

    @Override
    @Transactional
    public void delete(String itemId, User user) {
        var op = user.getCart().getCarInOrders().stream().filter(e -> itemId.equals(e.getCarId())).findFirst();
        op.ifPresent(carInOrder -> {
            carInOrder.setCart(null);
            carInOrderRepository.deleteById(carInOrder.getId());
        });
    }



    @Override
    @Transactional
    public void checkout(User user) {
        // Creat an order
        OrderMain order = new OrderMain(user);
        orderRepository.save(order);

        user.getCart().getCarInOrders().forEach(carInOrder -> {
            carInOrder.setCart(null);
            carInOrder.setOrderMain(order);
            carInOrderRepository.save(carInOrder);
        });

    }
}
