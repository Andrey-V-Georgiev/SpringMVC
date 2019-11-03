package org.softuni.residentevil.utils;

import org.modelmapper.ModelMapper;
import org.softuni.residentevil.domain.models.service_models.CapitalServiceModel;
import org.softuni.residentevil.domain.models.service_models.UserServiceModel;
import org.softuni.residentevil.domain.models.service_models.VirusServiceModel;
import org.softuni.residentevil.domain.models.view_models.CapitalViewModel;
import org.softuni.residentevil.domain.models.view_models.UserViewModel;
import org.softuni.residentevil.domain.models.view_models.VirusViewModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ListMapperImpl implements ListMapper {

    private ModelMapper modelMapper;

    public ListMapperImpl() {
        this.modelMapper = new ModelMapper();
    }

    public List<CapitalViewModel> mapCSMtoCVM(List<CapitalServiceModel> capitalServiceModels) {
        return capitalServiceModels
                .stream()
                .map(c -> this.modelMapper.map(c, CapitalViewModel.class))
                .collect(Collectors.toList());
    }

    public List<VirusViewModel> mapVSMtoVVM(List<VirusServiceModel> virusServiceModels) {
        return virusServiceModels
                .stream()
                .map(c -> this.modelMapper.map(c, VirusViewModel.class))
                .collect(Collectors.toList());
    }


    public List<UserViewModel> mapUSMtoUVM(List<UserServiceModel> userServiceModels) {
        return userServiceModels.stream()
                .map(m-> this.modelMapper.map(m,UserViewModel.class))
                .collect(Collectors.toList());
    }
}
