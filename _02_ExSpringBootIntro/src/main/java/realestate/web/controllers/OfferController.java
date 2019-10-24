package realestate.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import realestate.domain.models.binding.OfferFindBindingModel;
import realestate.domain.models.binding.OfferRegisterBindingModel;
import realestate.domain.models.service.OfferFindServiceModel;
import realestate.domain.models.service.OfferRegisterServiceModel;
import realestate.services.OfferService;

@Controller
public class OfferController {

    private ModelMapper modelMapper;
    private OfferService offerService;

    @Autowired
    public OfferController(ModelMapper modelMapper, OfferService offerService) {
        this.modelMapper = modelMapper;
        this.offerService = offerService;
    }

    @GetMapping("/register")
    public String register() {
        return "register.html";
    }

    @PostMapping("/register")
    public String registerConfirm(@ModelAttribute(name = "model") OfferRegisterBindingModel model) {

        try{
           this.offerService.registerOffer(this.modelMapper.map(model, OfferRegisterServiceModel.class));
        } catch(IllegalArgumentException IAE) {
            IAE.printStackTrace();
            return "redirect:/register";
        }

        return "redirect:/";
    }

    @GetMapping("/find")
    public String find() {
        return "find.html";
    }

    @PostMapping("/find")
    public String findConfirm(@ModelAttribute(name = "model") OfferFindBindingModel model) {
        try{
            this.offerService.findOffer(this.modelMapper.map(model, OfferFindServiceModel.class));
        } catch(IllegalArgumentException IAE) {
            IAE.printStackTrace();
            return "redirect:/find";
        }

        return "redirect:/";
    }
}
