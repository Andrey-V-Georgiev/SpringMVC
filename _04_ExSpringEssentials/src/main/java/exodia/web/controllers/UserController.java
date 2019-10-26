package exodia.web.controllers;

import exodia.domain.models.binding_models.UserLoginBindingModel;
import exodia.domain.models.binding_models.UserRegisterBindingModel;
import exodia.domain.models.service_models.UserServiceModel;
import exodia.domain.models.view_models.DocumentsHomeViewModel;
import exodia.services.DocumentService;
import exodia.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private final UserService userService;
    private final DocumentService documentService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, DocumentService documentService, ModelMapper modelMapper) {
        this.userService = userService;
        this.documentService = documentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    public ModelAndView index(ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("username") == null) {
            modelAndView.setViewName("index");
        } else {
            modelAndView.setViewName("redirect:/home");
        }
        return modelAndView;
    }

    @GetMapping("/home")
    public ModelAndView home(ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("username") == null) {
            modelAndView.setViewName("login");
        }
        modelAndView.setViewName("home");
        List<DocumentsHomeViewModel> documents = this.documentService
                .findAll()
                .stream()
                .map(d -> this.modelMapper.map(d, DocumentsHomeViewModel.class))
                .peek(d -> {
                    if (d.getTitle().length() > 12) {
                        d.setTitle(d.getTitle().substring(0, 12) + "...");
                    }
                })
                .collect(Collectors.toList());
        modelAndView.addObject("documents", documents);
        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView register(ModelAndView modelAndView) {
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@ModelAttribute UserRegisterBindingModel model
            , ModelAndView modelAndView, HttpSession session) {
        if (!model.getPassword().equals(model.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords doesn't match!");
        }
        if (!this.userService.registerUser(this.modelMapper.map(model, UserServiceModel.class))) {
            throw new IllegalArgumentException("Registration failed!");
        }
        modelAndView.setViewName("redirect:/login");
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView login(ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView loginConfirm(@ModelAttribute UserLoginBindingModel model
            , ModelAndView modelAndView, HttpSession session) {

        UserServiceModel userServiceModel = this.userService
                .loginUser(this.modelMapper.map(model, UserServiceModel.class));
        if (userServiceModel == null) {
            throw new IllegalArgumentException("User login failed!");
        }
        session.setAttribute("username", model.getUsername());
        modelAndView.setViewName("redirect:/home");
        return modelAndView;
    }

    @GetMapping("/logout")
    public ModelAndView logout(ModelAndView modelAndView, HttpSession session) {
        session.invalidate();
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
