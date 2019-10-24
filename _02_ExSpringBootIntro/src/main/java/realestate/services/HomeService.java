package realestate.services;

import org.springframework.stereotype.Service;
import realestate.domain.models.service.OfferServiceModel;

import java.util.List;

@Service
public interface HomeService {

    List<OfferServiceModel> findAllOffers();
}
