package realestate.services;

import org.springframework.stereotype.Service;
import realestate.domain.models.service.OfferRegisterServiceModel;

import java.util.List;

@Service
public interface HomeService {

    List<OfferRegisterServiceModel> findAllOffers();
}
