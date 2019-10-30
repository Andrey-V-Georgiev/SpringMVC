package org.softuni.residentevil.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.residentevil.domain.models.binding_models.VirusBindingModel;
import org.softuni.residentevil.domain.models.view_models.CapitalViewModel;
import org.softuni.residentevil.services.CapitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/viruses")
public class VirusController extends BaseController {

    private CapitalService capitalService;
    private ModelMapper modelMapper;

    @Autowired
    public VirusController(CapitalService capitalService, ModelMapper modelMapper) {
        this.capitalService = capitalService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public ModelAndView add(ModelAndView modelAndView, @ModelAttribute(name = "virusBindingModel") VirusBindingModel virusBindingModel) {

        List<CapitalViewModel> capitals = this.capitalService.findAllCapitals()
                .stream()
                .map(c -> this.modelMapper.map(c, CapitalViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("capitals", capitals);
        modelAndView.addObject("virusBindingModel", virusBindingModel);
        return super.view("add-virus", modelAndView);
    }

    @PostMapping("/add")
    public ModelAndView addConfirm(@Valid @ModelAttribute(name = "virusBindingModel") VirusBindingModel virusBindingModel,
                                   BindingResult bindingResult, ModelAndView modelAndView) {
        if(bindingResult.hasErrors()) {
            modelAndView.addObject("virusBindingModel", virusBindingModel);
            return super.view("add-virus", modelAndView);
        }
        return super.redirect("/");
    }
}
