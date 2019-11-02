package org.softuni.residentevil.web.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController extends BaseController {

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView users(ModelAndView modelAndView) {

        return super.loadView("users", modelAndView);
    }
}
