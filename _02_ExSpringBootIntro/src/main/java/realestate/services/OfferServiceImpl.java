package realestate.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import realestate.domain.entities.Offer;
import realestate.domain.models.service.OfferServiceModel;
import realestate.repositories.OfferRepository;

import javax.validation.Validator;

@Service
public class OfferServiceImpl implements OfferService {

    private Validator validator;
    private OfferRepository offerRepository;
    private ModelMapper modelMapper;

    @Autowired
    public OfferServiceImpl(Validator validator, OfferRepository offerRepository, ModelMapper modelMapper) {
        this.validator = validator;
        this.offerRepository = offerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void registerOffer(OfferServiceModel offerServiceModel) {
        if (this.validator.validate(offerServiceModel).size() != 0) {
            throw new IllegalArgumentException("Something went wrong!");
        }
        this.offerRepository.saveAndFlush(this.modelMapper.map(offerServiceModel, Offer.class));

    }
}
