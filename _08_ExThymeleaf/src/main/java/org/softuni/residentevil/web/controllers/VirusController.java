package org.softuni.residentevil.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.residentevil.domain.models.binding_models.VirusAddBindingModel;
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
public class VirusController {

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
    public ModelAndView add(@ModelAttribute(name = "virusAddBindingModel") VirusAddBindingModel virusAddBindingModel,
                            ModelAndView modelAndView) {
        modelAndView.addObject("capitals", capitals());
        modelAndView.addObject("virusAddBindingModel", virusAddBindingModel);
        modelAndView.setViewName("add-virus");
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView addConfirm(@Valid
                                   @ModelAttribute(name = "virusAddBindingModel") VirusAddBindingModel virusAddBindingModel,
                                   BindingResult bindingResult, ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("virusAddBindingModel", virusAddBindingModel);
            modelAndView.addObject("capitals", capitals());
            modelAndView.setViewName("add-virus");
            return modelAndView;
        }

        this.virusService.saveVirus(this.modelMapper.map(virusAddBindingModel, VirusServiceModel.class));
        modelAndView.setViewName("redirect:/viruses/show");
        return modelAndView;
    }

    @GetMapping("/show")
    public ModelAndView show(ModelAndView modelAndView) {
        List<VirusViewModel> virusViewModels = this.virusService.findAll().stream()
                .map(v -> this.modelMapper.map(v, VirusViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("virusViewModels", virusViewModels);
        modelAndView.setViewName("all-viruses");
        return modelAndView;
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
                                  @ModelAttribute(name = "virusAddBindingModel") VirusAddBindingModel virusAddBindingModel,
                                  ModelAndView modelAndView) {
        List<CapitalViewModel> capitals = this.capitalService.findAllCapitals()
                .stream()
                .map(c -> this.modelMapper.map(c, CapitalViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("capitals", capitals);
        modelAndView.addObject("virusServiceModel", this.virusService.findById(id));

        virusAddBindingModel = this.modelMapper.map(this.virusService.findById(id), VirusAddBindingModel.class);
        modelAndView.addObject("virusAddBindingModel", virusAddBindingModel);

        modelAndView.setViewName("edit-virus");
        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView editVirusConfirm(@Valid @ModelAttribute(name = "virusAddBindingModel") VirusAddBindingModel virusAddBindingModel,
                                         @PathVariable("id") String id, BindingResult bindingResult, ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("virusAddBindingModel", virusAddBindingModel);
            modelAndView.setViewName("edit-virus");
            return modelAndView;
        }

        this.virusService.editVirus(this.modelMapper.map(virusAddBindingModel, VirusServiceModel.class), id);
        modelAndView.setViewName("edit-virus");
        return modelAndView;
    }

    private List<CapitalViewModel> capitals() {
        return this.capitalService.findAllCapitals()
                .stream()
                .map(c -> this.modelMapper.map(c, CapitalViewModel.class))
                .collect(Collectors.toList());
    }
}
