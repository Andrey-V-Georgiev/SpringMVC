package realestate.services;

import org.springframework.stereotype.Service;
import realestate.domain.models.service.OfferFindServiceModel;
import realestate.domain.models.service.OfferRegisterServiceModel;

@Service
public interface OfferService {

    void registerOffer(OfferRegisterServiceModel offerRegisterServiceModel);

    void findOffer(OfferFindServiceModel offerFindServiceModel);
}
