package org.softuni.residentevil.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.residentevil.domain.models.binding_models.UserRegisterBindingModel;
import org.softuni.residentevil.domain.models.service_models.UserServiceModel;
import org.softuni.residentevil.services.CapitalService;
import org.softuni.residentevil.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;

@Controller
public class AuthController extends BaseController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final CapitalService capitalService;

    @Autowired
    public AuthController(UserService userService, ModelMapper modelMapper, CapitalService capitalService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.capitalService = capitalService;
    }

    @GetMapping("/")
    public ModelAndView index(ModelAndView modelAndView) {

        return super.loadView("index", modelAndView);
    }

    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView home(ModelAndView modelAndView, Principal principal) throws IOException {

        if (this.capitalService.capitalsTableIsEmpty()) {
            this.capitalService.seedCapitalsInDB();
        }

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