package exodia.services;

import exodia.domain.entities.Document;
import exodia.domain.models.service_models.DocumentServiceModel;
import exodia.repositories.DocumentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final ModelMapper modelMapper;
    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentServiceImpl(ModelMapper modelMapper, DocumentRepository documentRepository) {
        this.modelMapper = modelMapper;
        this.documentRepository = documentRepository;
    }

    @Override
    public DocumentServiceModel saveDocument(DocumentServiceModel documentServiceModel) {
        try {
            Document document = this.documentRepository.saveAndFlush(this.modelMapper.map(documentServiceModel, Document.class));
            return this.modelMapper.map(document, DocumentServiceModel.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public DocumentServiceModel findById(String id) {
        Document document = this.documentRepository.findById(id).orElse(null);
        if (document == null) {
            return null;
        } else {
            return this.modelMapper.map(document, DocumentServiceModel.class);
        }
    }

    @Override
    public List<DocumentServiceModel> findAll() {
        return this.documentRepository
                .findAll()
                .stream()
                .map(d -> this.modelMapper.map(d, DocumentServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean print(String id) {
        DocumentServiceModel documentServiceModel = this.documentRepository
                .findById(id).map(m -> this.modelMapper.map(m, DocumentServiceModel.class)).orElse(null);
        if (documentServiceModel == null) {
            return false;
        } else {
            this.documentRepository.deleteById(documentServiceModel.getId());
            return true;
        }
    }

}
