package org.softuni.residentevil.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/")
    public ModelAndView index(ModelAndView modelAndView, Principal principal) {
        modelAndView.addObject("principal", principal);
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/home")
    public ModelAndView home(ModelAndView modelAndView, Principal principal) {
        modelAndView.addObject("principal", principal);
        modelAndView.setViewName("home");
        return modelAndView;
    }
}
