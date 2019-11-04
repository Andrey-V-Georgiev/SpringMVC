package org.softuni.residentevil.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.residentevil.domain.entities.Role;
import org.softuni.residentevil.domain.models.binding_models.UserEditBindingModel;
import org.softuni.residentevil.domain.models.service_models.UserServiceModel;
import org.softuni.residentevil.repositories.RoleRepository;
import org.softuni.residentevil.services.UserService;
import org.softuni.residentevil.utils.ListMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashSet;

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

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView editVirus(@PathVariable("id") String id,
                                  @ModelAttribute(name = "userEditBindingModel") UserEditBindingModel userEditBindingModel,
                                  ModelAndView modelAndView) {

        userEditBindingModel = this.modelMapper.map(this.userService.findById(id), UserEditBindingModel.class);
        modelAndView.addObject("userEditBindingModel", userEditBindingModel);
        return super.loadView("edit-user", modelAndView);
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView editUserConfirm(@PathVariable("id") String id,
                                         @ModelAttribute(name = "userEditBindingModel") UserEditBindingModel userEditBindingModel,
                                         ModelAndView modelAndView) {
       String role = userEditBindingModel.getAuthorities();

        this.userService.editUser(id, role);
        return super.redirectTo("redirect:/users/all", modelAndView);
    }
}
