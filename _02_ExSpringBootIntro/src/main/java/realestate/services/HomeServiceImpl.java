package realestate.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import realestate.domain.models.service.OfferRegisterServiceModel;
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
    public List<OfferRegisterServiceModel> findAllOffers() {
        List<OfferRegisterServiceModel> allOffers = this.offerRepository.findAll()
                .stream()
                .map(o-> this.modelMapper.map(o, OfferRegisterServiceModel.class))
                .collect(Collectors.toList());
        return allOffers;
    }
}
