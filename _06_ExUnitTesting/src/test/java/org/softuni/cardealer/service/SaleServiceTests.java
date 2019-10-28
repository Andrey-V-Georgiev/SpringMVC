package org.softuni.cardealer.service;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.CarSale;
import org.softuni.cardealer.domain.models.service.CarSaleServiceModel;
import org.softuni.cardealer.domain.models.service.PartSaleServiceModel;
import org.softuni.cardealer.domain.models.service.PartServiceModel;
import org.softuni.cardealer.repository.CarSaleRepository;
import org.softuni.cardealer.repository.PartSaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SaleServiceTests {

    @Autowired
    private CarSaleRepository carSaleRepository;
    @Autowired
    private PartSaleRepository partSaleRepository;

    private ModelMapper modelMapper = new ModelMapper();
    private SaleService saleService;

    @Before
    public void init() {
        this.saleService = new SaleServiceImpl(this.carSaleRepository, this.partSaleRepository, this.modelMapper);
    }

    @Test
    public void saleService_saleCarCorrectValues_ReturnCorrect() {
        CarSaleServiceModel carSaleServiceModel = new CarSaleServiceModel();
        carSaleServiceModel.setDiscount(20.0);

        CarSaleServiceModel actualCarSale = this.saleService.saleCar(carSaleServiceModel);
        CarSaleServiceModel expectedCarSale = this.modelMapper
                .map(this.carSaleRepository.findAll().get(0), CarSaleServiceModel.class);

        assertEquals(expectedCarSale.getId(), actualCarSale.getId());
    }

    @Test(expected = Exception.class)
    public void saleService_saleCarWithoutDiscount_ThrowsException() {

        CarSaleServiceModel carSaleServiceModel = new CarSaleServiceModel();
        CarSaleServiceModel actualCarSale = this.saleService.saleCar(carSaleServiceModel);
    }

    @Test
    public void saleService_salePartCorrectValue_ReturnCorrect() {
        PartSaleServiceModel partSaleServiceModel = new PartSaleServiceModel();
        partSaleServiceModel.setDiscount(20.0);
        partSaleServiceModel.setQuantity(10);

        PartSaleServiceModel actualPartSale = this.saleService.salePart(partSaleServiceModel);
        PartSaleServiceModel expectedPartSale = this.modelMapper
                .map(this.partSaleRepository.findAll().get(0), PartSaleServiceModel.class);
        assertEquals(expectedPartSale.getId(), actualPartSale.getId());
    }

    @Test(expected = Exception.class)
    public void saleService_salePartNullValues_ThrowsException() {
        PartSaleServiceModel partSaleServiceModel = new PartSaleServiceModel();
        this.saleService.salePart(partSaleServiceModel);
    }
}
