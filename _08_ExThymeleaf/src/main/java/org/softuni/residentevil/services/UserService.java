package org.softuni.residentevil.services;

import org.softuni.residentevil.domain.models.service_models.UserServiceModel;
import org.softuni.residentevil.domain.models.view_models.UserViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {

    boolean registerUser(UserServiceModel userServiceModel);

    List<UserServiceModel> findAll();

    UserServiceModel findById(String id);

    void editUser(String id, String role);
}
