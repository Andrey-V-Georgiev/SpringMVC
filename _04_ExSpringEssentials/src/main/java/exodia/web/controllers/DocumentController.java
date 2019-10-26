package exodia.web.controllers;

import exodia.domain.entities.Document;
import exodia.domain.models.binding_models.DocumentBindingModel;
import exodia.domain.models.service_models.DocumentServiceModel;
import exodia.domain.models.view_models.DocumentDetailsViewModel;
import exodia.repositories.DocumentRepository;
import exodia.services.DocumentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class DocumentController {

    private final ModelMapper modelMapper;
    private final DocumentService documentService;

    @Autowired
    public DocumentController(ModelMapper modelMapper, DocumentService documentService) {
        this.modelMapper = modelMapper;
        this.documentService = documentService;
    }

    @GetMapping("/schedule")
    public ModelAndView schedule(ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("username") == null) {
            modelAndView.setViewName("redirect:/login");
        } else {
            modelAndView.setViewName("schedule");
        }
        return modelAndView;
    }

    @PostMapping("/schedule")
    public ModelAndView scheduleConfirm(@ModelAttribute DocumentBindingModel model,
                                        ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("username") == null) {
            modelAndView.setViewName("redirect:/login");
        }

        DocumentServiceModel documentServiceModel = this.documentService
                .saveDocument(this.modelMapper.map(model, DocumentServiceModel.class));
        if (documentServiceModel == null) {
            throw new IllegalArgumentException("Document creation failed!");
        }
        modelAndView.setViewName("redirect:/details/" + documentServiceModel.getId());
        return modelAndView;
    }

    @GetMapping("/details/{id}")
    public ModelAndView details(@PathVariable("id") String id, ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("username") == null) {
            modelAndView.setViewName("redirect:/login");
        }
        DocumentServiceModel documentServiceModel = this.documentService.findById(id);
        if (documentServiceModel == null) {
            throw new IllegalArgumentException("Document not found");
        }
        modelAndView.setViewName("details");
        DocumentDetailsViewModel model = this.modelMapper.map(documentServiceModel, DocumentDetailsViewModel.class);
        modelAndView.addObject("model", model);
        return modelAndView;
    }

    @GetMapping("/print/{id}")
    public ModelAndView print(@PathVariable("id") String id, ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("username") == null) {
            modelAndView.setViewName("redirect:/login");
        }
        if (!this.documentService.print(id)) {
            throw new IllegalArgumentException("Document printing failed!");
        }
        modelAndView.setViewName("redirect:/home");
        return modelAndView;
    }

}
