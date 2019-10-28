package org.softuni.cardealer.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Part;
import org.softuni.cardealer.domain.models.service.PartServiceModel;
import org.softuni.cardealer.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PartServiceTests {
    @Autowired
    private PartRepository partRepository;
    private ModelMapper modelMapper = new ModelMapper();
    private PartService partService;

    @Before
    public void init() {
        this.partService = new PartServiceImpl(this.partRepository, this.modelMapper);
    }

    @Test
    public void partService_savePartCorrectValue_ReturnCorrect() {
        PartServiceModel newPart = new PartServiceModel();
        newPart.setName("Turbo charger");
        newPart.setPrice(new BigDecimal("1230"));

        PartServiceModel actual = this.partService.savePart(newPart);
        PartServiceModel expected = this.modelMapper
                .map(this.partRepository.findAll().get(0), PartServiceModel.class);

        assertEquals(expected.getId(), actual.getId());
    }

    @Test(expected = Exception.class)
    public void partService_savePartNullValue_ThrowsException() {
        PartServiceModel newPart = new PartServiceModel();
        this.partService.savePart(newPart);
    }

    @Test
    public void partService_editPartCorrectValue_ReturnCorrect() {
        Part part = new Part();
        part.setName("Super Charger");
        part.setPrice(new BigDecimal("2300"));

        this.partRepository.saveAndFlush(part);

        PartServiceModel partServiceModel = new PartServiceModel();
        partServiceModel.setId(part.getId());
        partServiceModel.setName("Turbo Compressor");
        partServiceModel.setPrice(new BigDecimal("3200"));

        PartServiceModel actual = this.partService.editPart(partServiceModel);
        PartServiceModel expected = this.modelMapper
                .map(this.partRepository.findAll().get(0), PartServiceModel.class);
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getPrice(), actual.getPrice());
    }

    @Test(expected = Exception.class)
    public void partService_editPartNullValue_ThrowsException() {
        Part part = new Part();
        part.setName("Super Charger");

        this.partRepository.saveAndFlush(part);

        PartServiceModel partServiceModel = new PartServiceModel();
        partServiceModel.setId(part.getId());
        partServiceModel.setName("Turbo Compressor");
        partServiceModel.setPrice(new BigDecimal("3200"));

        this.partService.editPart(partServiceModel);
    }

    //PartServiceModel deletePart(String id);
    @Test
    public void partService_deletePartCorrectValue_ReturnCorrect() {
        Part part = new Part();
        part.setName("Super Charger");
        part.setPrice(new BigDecimal("2300"));

        this.partRepository.saveAndFlush(part);

        PartServiceModel partServiceModel = this.partService.deletePart(part.getId());
        long expected = 0;
        long actual = this.partRepository.count();

        assertEquals(expected, actual);
    }

    @Test(expected = Exception.class)
    public void partService_deletePartNullValue_ThrowsException() {
        Part part = new Part();
        part.setPrice(new BigDecimal("2300"));

        this.partRepository.saveAndFlush(part);

        PartServiceModel partServiceModel = this.partService.deletePart(part.getId());
    }

    //PartServiceModel findPartById(String id);
    @Test
    public void partService_findPartByIdCorrectValue_ReturnCorrect() {
        Part part = new Part();
        part.setName("Super Charger");
        part.setPrice(new BigDecimal("2300"));

        this.partRepository.saveAndFlush(part);

        PartServiceModel actual = this.partService.findPartById(part.getId());
        PartServiceModel expected = this.modelMapper
                .map(this.partRepository.findAll().get(0), PartServiceModel.class);

        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getId(), actual.getId());
    }

    @Test(expected = Exception.class)
    public void partService_findPartByIdNullValue_ThrowsException() {
        Part part = new Part();
        part.setPrice(new BigDecimal("2300"));

        this.partRepository.saveAndFlush(part);
        this.partService.findPartById(part.getId());
    }
}
