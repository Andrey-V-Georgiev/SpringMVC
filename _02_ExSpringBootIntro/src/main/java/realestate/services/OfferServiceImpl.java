package realestate.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import realestate.domain.entities.Offer;
import realestate.domain.models.service.OfferFindServiceModel;
import realestate.domain.models.service.OfferRegisterServiceModel;
import realestate.repositories.OfferRepository;

import javax.validation.Validator;
import java.util.Optional;

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
    public void registerOffer(OfferRegisterServiceModel offerRegisterServiceModel) {
        if (this.validator.validate(offerRegisterServiceModel).size() != 0) {
            throw new IllegalArgumentException("Something went wrong!");
        }
        this.offerRepository.saveAndFlush(this.modelMapper.map(offerRegisterServiceModel, Offer.class));
    }

    @Override
    public void findOffer(OfferFindServiceModel offerFindServiceModel) {

        Optional<Offer> offer = this.offerRepository.findAll()
                .stream()
                .filter(o -> coverRequirements(o, offerFindServiceModel))
                .findFirst();

        offer.orElseThrow(IllegalArgumentException::new);
        offer.ifPresent(o -> this.offerRepository.deleteById(o.getId()));
    }

    private Boolean coverRequirements(Offer offer, OfferFindServiceModel offerFindServiceModel) {
        Boolean typeMatch = offer.getApartmentType().equals(offerFindServiceModel.getFamilyApartmentType());
        Boolean budgetIsEnough = offerFindServiceModel.getFamilyBudget().intValue() >= offer.getApartmentRent().intValue()
                + ((offer.getApartmentRent().intValue() * offer.getAgencyCommission().intValue())/100);

        return typeMatch && budgetIsEnough;
    }
}
