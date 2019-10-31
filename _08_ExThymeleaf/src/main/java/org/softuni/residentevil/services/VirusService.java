package org.softuni.residentevil.services;

import org.softuni.residentevil.domain.models.service_models.VirusServiceModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VirusService {

    VirusServiceModel saveVirus(VirusServiceModel virusServiceModel);

    List<VirusServiceModel> findAll();
}
