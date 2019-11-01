package org.softuni.residentevil.services;

import org.softuni.residentevil.domain.models.service_models.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends UserDetailsService {

    boolean registerUser(UserServiceModel userServiceModel);
}
