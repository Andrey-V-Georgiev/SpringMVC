package org.softuni.residentevil.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.residentevil.domain.models.service_models.UserServiceModel;
import org.softuni.residentevil.domain.models.view_models.UserViewModel;
import org.softuni.residentevil.services.UserService;
import org.softuni.residentevil.utils.ListMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

    private final ModelMapper modelMapper;
    private final UserService userService;
    private final ListMapperImpl listMapper;

    @Autowired
    public UserController(ModelMapper modelMapper, UserService userService, ListMapperImpl listMapper) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.listMapper = listMapper;
    }


    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView users(ModelAndView modelAndView) {

        modelAndView.addObject("users", this.listMapper.mapUSMtoUVM(this.userService.findAll()));
        return super.loadView("all-users", modelAndView);
    }

}
