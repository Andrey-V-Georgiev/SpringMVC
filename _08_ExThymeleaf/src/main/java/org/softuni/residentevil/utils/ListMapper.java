package org.softuni.residentevil.utils;

import org.softuni.residentevil.domain.models.service_models.CapitalServiceModel;
import org.softuni.residentevil.domain.models.service_models.UserServiceModel;
import org.softuni.residentevil.domain.models.service_models.VirusServiceModel;
import org.softuni.residentevil.domain.models.view_models.CapitalViewModel;
import org.softuni.residentevil.domain.models.view_models.UserViewModel;
import org.softuni.residentevil.domain.models.view_models.VirusViewModel;

import java.util.List;

public interface ListMapper {

    public List<CapitalViewModel> mapCSMtoCVM(List<CapitalServiceModel> capitalServiceModels);

    public List<VirusViewModel> mapVSMtoVVM(List<VirusServiceModel> virusServiceModels);

    public List<UserViewModel> mapUSMtoUVM(List<UserServiceModel> userServiceModels);
}
