package org.softuni.residentevil.services;

import org.softuni.residentevil.domain.models.service_models.CapitalServiceModel;
import org.softuni.residentevil.domain.models.view_models.CapitalViewModel;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface CapitalService {

    List<CapitalServiceModel> findAllCapitals();

    boolean capitalsTableIsEmpty();

    void seedCapitalsInDB() throws IOException;
}
