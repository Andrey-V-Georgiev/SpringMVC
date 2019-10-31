package org.softuni.residentevil.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.residentevil.domain.models.binding_models.VirusBindingModel;
import org.softuni.residentevil.domain.models.service_models.VirusServiceModel;
import org.softuni.residentevil.domain.models.view_models.CapitalViewModel;
import org.softuni.residentevil.domain.models.view_models.VirusViewModel;
import org.softuni.residentevil.services.CapitalService;
import org.softuni.residentevil.services.VirusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/viruses")
public class VirusController extends BaseController {

    private final CapitalService capitalService;
    private final VirusService virusService;
    private final ModelMapper modelMapper;

    @Autowired
    public VirusController(CapitalService capitalService, VirusService virusService, ModelMapper modelMapper) {
        this.capitalService = capitalService;
        this.virusService = virusService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public ModelAndView add(@ModelAttribute(name = "virusBindingModel") VirusBindingModel virusBindingModel, ModelAndView modelAndView) {
        modelAndView.addObject("capitals", capitals());
        modelAndView.addObject("virusBindingModel", virusBindingModel);
        return super.view("add-virus", modelAndView);
    }

    @PostMapping("/add")
    public ModelAndView addConfirm(@Valid @ModelAttribute(name = "virusBindingModel") VirusBindingModel virusBindingModel,
                                   BindingResult bindingResult, ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("virusBindingModel", virusBindingModel);
            modelAndView.addObject("capitals", capitals());
            modelAndView.setViewName("add-virus");
            return modelAndView;
        }

        this.virusService.saveVirus(this.modelMapper.map(virusBindingModel, VirusServiceModel.class));

        return super.redirect("/viruses/show");
    }

    @GetMapping("/show")
    public ModelAndView show(ModelAndView modelAndView) {
        List<VirusViewModel> virusViewModels = this.virusService.findAll().stream()
                .map(v -> this.modelMapper.map(v, VirusViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("virusViewModels", virusViewModels);
        return super.view("all-viruses", modelAndView);
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteVirus(@PathVariable("id") String id, ModelAndView modelAndView) {
        try {
            this.virusService.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        modelAndView.setViewName("redirect:/viruses/show");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editVirus(@PathVariable("id") String id,
                                  @ModelAttribute(name = "virusBindingModel") VirusBindingModel virusBindingModel,
                                  ModelAndView modelAndView) {
        List<CapitalViewModel> capitals = this.capitalService.findAllCapitals()
                .stream()
                .map(c -> this.modelMapper.map(c, CapitalViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("capitals", capitals);
        modelAndView.addObject("virusServiceModel", this.virusService.findById(id));
        modelAndView.setViewName("edit-virus");
        modelAndView.addObject("virusBindingModel", virusBindingModel);
        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView editVirusConfirm(@Valid
                                         @ModelAttribute(name = "virusBindingModel") VirusBindingModel virusBindingModel,
                                         @PathVariable("id") String id,
                                         BindingResult bindingResult,
                                         ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("virusBindingModel", virusBindingModel);
            return super.view("edit-virus", modelAndView);
        }
        this.virusService.editVirus(this.modelMapper.map(virusBindingModel, VirusServiceModel.class), id);
        return super.redirect("/viruses/show");
    }

    public List<CapitalViewModel> capitals() {
       return  this.capitalService.findAllCapitals()
                .stream()
                .map(c -> this.modelMapper.map(c, CapitalViewModel.class))
                .collect(Collectors.toList());
    }
}
