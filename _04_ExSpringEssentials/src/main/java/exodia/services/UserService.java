package exodia.services;

import exodia.domain.models.service_models.UserServiceModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    boolean registerUser(UserServiceModel userServiceModel);

    UserServiceModel loginUser(UserServiceModel userServiceModel);
}
