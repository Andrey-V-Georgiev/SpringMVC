package org.softuni.residentevil.web.controllers;

import org.hibernate.validator.constraints.Range;
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
import org.springframework.validation.annotation.Validated;
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
    public ModelAndView add(@ModelAttribute(name = "virusAddBindingModel") VirusAddBindingModel virusAddBindingModel,
                            ModelAndView modelAndView) {

        modelAndView.addObject("capitals", capitals());
        modelAndView.addObject("virusAddBindingModel", virusAddBindingModel);
        return super.loadView("add-virus", modelAndView);
    }

    @PostMapping("/add")
    public ModelAndView addConfirm(@Valid
                                   @ModelAttribute(name = "virusAddBindingModel") VirusAddBindingModel virusAddBindingModel,
                                   BindingResult bindingResult,
                                   ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("virusAddBindingModel", virusAddBindingModel);
            modelAndView.addObject("capitals", capitals());
            return super.loadView("add-virus", modelAndView);
        }

        this.virusService.saveVirus(this.modelMapper.map(virusAddBindingModel, VirusServiceModel.class));
        return super.redirectTo("redirect:/viruses/show", modelAndView);
    }

    @GetMapping("/show")
    public ModelAndView show(ModelAndView modelAndView) {

        List<VirusViewModel> virusViewModels = this.virusService.findAll().stream()
                .map(v -> this.modelMapper.map(v, VirusViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("virusViewModels", virusViewModels);
        return super.loadView("all-viruses", modelAndView);
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteVirus(@PathVariable("id") String id,
                                    ModelAndView modelAndView) {
        try {
            this.virusService.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.redirectTo("redirect:/viruses/show", modelAndView);
    }

    @GetMapping("/edit/{virusID}")
    public ModelAndView editVirus(@PathVariable("virusID") String virusID,
                                  @ModelAttribute(name = "virusAddBindingModel") VirusAddBindingModel virusAddBindingModel,
                                  ModelAndView modelAndView) {

        List<CapitalViewModel> capitals = this.capitalService.findAllCapitals()
                .stream()
                .map(c -> this.modelMapper.map(c, CapitalViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("capitals", capitals);
        modelAndView.addObject("virusID", virusID);

        virusAddBindingModel = this.modelMapper.map(this.virusService.findById(virusID), VirusAddBindingModel.class);
        modelAndView.addObject("virusAddBindingModel", virusAddBindingModel);
        return super.loadView("edit-virus", modelAndView);
    }

    @PostMapping("/edit/{virusID}")
    public ModelAndView editVirusConfirm(@PathVariable("virusID") String virusID,
                                         @Valid
                                         @ModelAttribute(name = "virusAddBindingModel") VirusAddBindingModel virusAddBindingModel,
                                         BindingResult bindingResult,
                                         ModelAndView modelAndView) {

        modelAndView.addObject("virusID", virusID);

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("virusAddBindingModel", virusAddBindingModel);
            modelAndView.addObject("capitals", capitals());
            return super.loadView("edit-virus", modelAndView);
        }
        this.virusService.editVirus(this.modelMapper.map(virusAddBindingModel, VirusServiceModel.class), virusID);
        return super.redirectTo("redirect:/viruses/show", modelAndView);
    }

    private List<CapitalViewModel> capitals() {
        return this.capitalService.findAllCapitals()
                .stream()
                .map(c -> this.modelMapper.map(c, CapitalViewModel.class))
                .collect(Collectors.toList());
    }
}
