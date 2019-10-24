package realestate.services;

import org.springframework.stereotype.Service;
import realestate.domain.models.service.OfferServiceModel;

@Service
public interface OfferService {

    void registerOffer(OfferServiceModel offerServiceModel);
}
