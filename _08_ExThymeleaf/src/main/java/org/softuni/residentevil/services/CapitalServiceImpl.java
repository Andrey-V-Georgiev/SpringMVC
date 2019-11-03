package org.softuni.residentevil.services;

import org.modelmapper.ModelMapper;
import org.softuni.residentevil.domain.models.service_models.CapitalServiceModel;
import org.softuni.residentevil.domain.models.view_models.CapitalViewModel;
import org.softuni.residentevil.repositories.CapitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Service
public class CapitalServiceImpl implements CapitalService {

    private ModelMapper modelMapper;
    private CapitalRepository capitalRepository;

    @Autowired
    public CapitalServiceImpl(ModelMapper modelMapper, CapitalRepository capitalRepository) {
        this.modelMapper = modelMapper;
        this.capitalRepository = capitalRepository;
    }


    @Override
    public List<CapitalServiceModel> findAllCapitals() {

        List<CapitalServiceModel> capitals = this.capitalRepository.findAllOrderByName()
                .stream()
                .map(c -> this.modelMapper.map(c, CapitalServiceModel.class))
                .collect(Collectors.toList());

        return capitals;
    }

    @Override
    public boolean capitalsTableIsEmpty() {
        return this.capitalRepository.count() == 0;
    }

    @Override
    public void seedCapitalsInDB() throws IOException {
        this.capitalRepository.seedCapitals();
    }
}
