package org.softuni.residentevil.services;

import org.softuni.residentevil.domain.models.service_models.CapitalServiceModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CapitalService {

    List<CapitalServiceModel> findAllCapitals();
}
