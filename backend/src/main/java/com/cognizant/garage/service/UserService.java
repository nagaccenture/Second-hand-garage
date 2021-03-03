package com.cognizant.garage.service;


import com.cognizant.garage.entity.User;

import java.util.Collection;

public interface UserService {
    User findOne(String email);

    User save(User user);

}
