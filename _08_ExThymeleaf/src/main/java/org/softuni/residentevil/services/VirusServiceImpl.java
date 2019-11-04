package org.softuni.residentevil.services;

import org.modelmapper.ModelMapper;
import org.softuni.residentevil.domain.entities.Virus;
import org.softuni.residentevil.domain.models.service_models.VirusServiceModel;
import org.softuni.residentevil.repositories.VirusRepository;
import org.softuni.residentevil.services.VirusService;
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

    @Override
    public VirusServiceModel findById(String id) {
        return this.virusRepository.findById(id)
                .stream()
                .map(v -> this.modelMapper.map(v, VirusServiceModel.class))
                .findFirst().orElse(null);
    }

    @Override
    public void deleteById(String id) throws Exception {
        try {
            this.virusRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Something went wrong!");
        }

    }

    @Override
    public void editVirus(VirusServiceModel virusServiceModel) {

            VirusServiceModel oldVirusServiceModel = this.modelMapper
                    .map(this.virusRepository.findById(virusServiceModel.getId()), VirusServiceModel.class);

            oldVirusServiceModel.setId(virusServiceModel.getId());
            oldVirusServiceModel.setName(virusServiceModel.getName());
            oldVirusServiceModel.setDescription(virusServiceModel.getDescription());
            oldVirusServiceModel.setSideEffects(virusServiceModel.getSideEffects());
            oldVirusServiceModel.setCreator(virusServiceModel.getCreator());
            oldVirusServiceModel.setDeadly(virusServiceModel.getDeadly());
            oldVirusServiceModel.setCurable(virusServiceModel.getCurable());
            oldVirusServiceModel.setMutation(virusServiceModel.getMutation());
            oldVirusServiceModel.setTurnoverRate(virusServiceModel.getTurnoverRate());
            oldVirusServiceModel.setHoursUntilTurn(virusServiceModel.getHoursUntilTurn());
            oldVirusServiceModel.setMagnitude(virusServiceModel.getMagnitude());
            oldVirusServiceModel.setReleasedOn(virusServiceModel.getReleasedOn());
            oldVirusServiceModel.setCapitals(virusServiceModel.getCapitals());

            this.virusRepository.save(this.modelMapper.map(oldVirusServiceModel, Virus.class));
    }
}
