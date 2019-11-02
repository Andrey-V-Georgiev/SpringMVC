package org.softuni.residentevil.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

@Controller
public abstract class BaseController {

    protected ModelAndView loadView(String view, ModelAndView modelAndView) {
        modelAndView.setViewName(view);
        return modelAndView;
    }

    protected ModelAndView redirectTo(String path, ModelAndView modelAndView) {
        modelAndView.setViewName(path);
        return modelAndView;
    }
}
