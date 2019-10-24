package realestate.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import realestate.domain.entities.Offer;
import realestate.domain.models.service.OfferServiceModel;
import realestate.repositories.OfferRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeServiceImpl implements HomeService {

    private OfferRepository offerRepository;
    private ModelMapper modelMapper;

    @Autowired
    public HomeServiceImpl(OfferRepository offerRepository, ModelMapper modelMapper) {
        this.offerRepository = offerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<OfferServiceModel> findAllOffers() {
        List<OfferServiceModel> allOffers = this.offerRepository.findAll()
                .stream()
                .map(o-> this.modelMapper.map(o, OfferServiceModel.class))
                .collect(Collectors.toList());
        return allOffers;
    }
}
