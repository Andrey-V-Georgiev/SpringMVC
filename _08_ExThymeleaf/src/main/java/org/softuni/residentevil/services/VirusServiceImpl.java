package org.softuni.residentevil.services;

import org.modelmapper.ModelMapper;
import org.softuni.residentevil.domain.entities.Virus;
import org.softuni.residentevil.domain.models.service_models.VirusServiceModel;
import org.softuni.residentevil.repositories.VirusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VirusServiceImpl implements VirusService {

    private final VirusRepository virusRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public VirusServiceImpl(VirusRepository virusRepository, ModelMapper modelMapper) {
        this.virusRepository = virusRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public VirusServiceModel saveVirus(VirusServiceModel virusServiceModel) {

        Virus virus = this.modelMapper.map(virusServiceModel, Virus.class);
        virus = this.virusRepository.saveAndFlush(virus);

        return this.modelMapper.map(virus, VirusServiceModel.class);
    }

    @Override
    public List<VirusServiceModel> findAll() {
        return this.virusRepository.findAll()
                .stream()
                .map(v -> this.modelMapper.map(v, VirusServiceModel.class))
                .collect(Collectors.toList());
    }
}
