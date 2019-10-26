package exodia.services;

import exodia.domain.models.service_models.DocumentServiceModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DocumentService {

    DocumentServiceModel saveDocument(DocumentServiceModel documentServiceModel);

    DocumentServiceModel findById(String id);

    List<DocumentServiceModel> findAll();

    boolean print(String id);

}
