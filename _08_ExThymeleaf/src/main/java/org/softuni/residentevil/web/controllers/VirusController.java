package org.softuni.residentevil.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.residentevil.domain.models.binding_models.VirusAddBindingModel;
import org.softuni.residentevil.domain.models.service_models.VirusServiceModel;
import org.softuni.residentevil.services.CapitalService;
import org.softuni.residentevil.services.VirusService;
import org.softuni.residentevil.utils.ListMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/viruses")
public class VirusController extends BaseController {

    private final CapitalService capitalService;
    private final VirusService virusService;
    private final ModelMapper modelMapper;
    private final ListMapperImpl listMapper;

    @Autowired
    public VirusController(CapitalService capitalService, VirusService virusService, ModelMapper modelMapper, ListMapperImpl mapper) {
        this.capitalService = capitalService;
        this.virusService = virusService;
        this.modelMapper = modelMapper;
        this.listMapper = mapper;
    }

    @GetMapping("/add")
    public ModelAndView add(@ModelAttribute(name = "virusAddBindingModel") VirusAddBindingModel virusAddBindingModel,
                            ModelAndView modelAndView) {

        modelAndView.addObject("capitals",  this.listMapper.mapCSMtoCVM(this.capitalService.findAllCapitals()));
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
            modelAndView.addObject("capitals",  this.listMapper.mapCSMtoCVM(this.capitalService.findAllCapitals()));
            return super.loadView("add-virus", modelAndView);
        }

        this.virusService.saveVirus(this.modelMapper.map(virusAddBindingModel, VirusServiceModel.class));
        return super.redirectTo("redirect:/viruses/all", modelAndView);
    }

    @GetMapping("/all")
    public ModelAndView allViruses(ModelAndView modelAndView) {

        modelAndView.addObject("virusViewModels", this.listMapper.mapVSMtoVVM(this.virusService.findAll()));
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
        return super.redirectTo("redirect:/viruses/all", modelAndView);
    }

    @GetMapping("/edit/{virusID}")
    public ModelAndView editVirus(@PathVariable("virusID") String virusID,
                                  @ModelAttribute(name = "virusAddBindingModel") VirusAddBindingModel virusAddBindingModel,
                                  ModelAndView modelAndView) {
        virusAddBindingModel.setId(virusID);
        modelAndView.addObject("capitals",  this.listMapper.mapCSMtoCVM(this.capitalService.findAllCapitals()));
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

        virusAddBindingModel.setId(virusID);
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("virusAddBindingModel", virusAddBindingModel);
            modelAndView.addObject("capitals", this.listMapper.mapCSMtoCVM(this.capitalService.findAllCapitals()));
            return super.loadView("edit-virus", modelAndView);
        }
        this.virusService.editVirus(this.modelMapper.map(virusAddBindingModel, VirusServiceModel.class));
        return super.redirectTo("redirect:/viruses/all", modelAndView);
    }

}
