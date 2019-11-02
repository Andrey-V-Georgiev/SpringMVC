package org.softuni.residentevil.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.residentevil.domain.models.binding_models.UserRegisterBindingModel;
import org.softuni.residentevil.domain.models.service_models.UserServiceModel;
import org.softuni.residentevil.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class AuthController extends BaseController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    public ModelAndView index(ModelAndView modelAndView) {

        return super.loadView("index", modelAndView);
    }

    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView home(ModelAndView modelAndView, Principal principal) {

        modelAndView.addObject("principal", principal);
        return super.loadView("home", modelAndView);
    }

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(required = false) String error,
                              ModelAndView modelAndView) {
        if (error != null) {
            modelAndView.addObject("error", error);
        }
        return super.loadView("login", modelAndView);
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView register(ModelAndView modelAndView) {

        return super.loadView("register", modelAndView);
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@ModelAttribute(name = "model") UserRegisterBindingModel model,
                                        ModelAndView modelAndView) {
        if (!model.getPassword().equals(model.getConfirmPassword())) {
            return super.loadView("register", modelAndView);
        }
        this.userService.registerUser(this.modelMapper.map(model, UserServiceModel.class));
        return super.loadView("login", modelAndView);
    }

    @GetMapping("/unauthorized")
    public ModelAndView unauthorized(ModelAndView modelAndView) {

        return super.loadView("unauthorized", modelAndView);
    }
}