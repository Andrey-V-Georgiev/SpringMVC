package org.softuni.cardealer.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Car;
import org.softuni.cardealer.domain.entities.Supplier;
import org.softuni.cardealer.domain.models.service.CarServiceModel;
import org.softuni.cardealer.domain.models.service.SupplierServiceModel;
import org.softuni.cardealer.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CarServiceTests {

    @Autowired
    private CarRepository carRepository;
    private ModelMapper modelMapper = new ModelMapper();
    private CarService carService;

    @Before
    public void init() {
         carService = new CarServiceImpl(this.carRepository, this.modelMapper);
    }

    @Test
    public void supplierService_saveSupplierCorrectValues_ReturnsCorrect() {

        CarServiceModel newCar = new CarServiceModel();
        newCar.setMake("BMW");
        newCar.setModel("E46");
        newCar.setTravelledDistance(250900L);

        CarServiceModel actual = this.carService.saveCar(newCar);
        CarServiceModel expected = this.modelMapper
                .map(this.carRepository.findAll().get(0), CarServiceModel.class);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getMake(), actual.getMake());
        assertEquals(expected.getModel(), actual.getModel());
        assertEquals(expected.getTravelledDistance(), actual.getTravelledDistance());
    }

    @Test(expected = Exception.class)
    public void supplierService_saveSupplierNullValues_ThrowsException() {

        CarServiceModel newCar = new CarServiceModel();
        newCar.setMake("BMW");
        newCar.setModel("E46");
        newCar.setTravelledDistance(null);

        this.carService.saveCar(newCar);
    }

    @Test
    public void supplierService_editSupplierCorrectValue_ReturnCorrect() {

        Car newCar = new Car();
        newCar.setMake("BMW");
        newCar.setModel("E46");
        newCar.setTravelledDistance(250900L);

        this.carRepository.saveAndFlush(newCar);

        CarServiceModel carServiceModel = new CarServiceModel();
        carServiceModel.setId(newCar.getId());
        carServiceModel.setMake("MB");
        carServiceModel.setModel("560 SEC");
        carServiceModel.setTravelledDistance(450900L);

        CarServiceModel actual = this.carService.editCar(carServiceModel);
        CarServiceModel expected = this.modelMapper
                .map(this.carRepository.findAll().get(0), CarServiceModel.class);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getMake(), actual.getMake());
        assertEquals(expected.getModel(), actual.getModel());
        assertEquals(expected.getTravelledDistance(), actual.getTravelledDistance());
    }

    @Test(expected = Exception.class)
    public void supplierService_editSupplierNullValue_ThrowsException() {

        Car newCar = new Car();
        newCar.setMake("BMW");
        newCar.setModel("E46");
        newCar.setTravelledDistance(250900L);

        this.carRepository.saveAndFlush(newCar);

        CarServiceModel carServiceModel = new CarServiceModel();
        carServiceModel.setId(carServiceModel.getId());
        carServiceModel.setMake("MB");
        carServiceModel.setModel("560 SEC");
        carServiceModel.setTravelledDistance(450900L);

        this.carService.editCar(carServiceModel);
    }

    @Test
    public void supplierService_deleteSupplierValid_ReturnCorrect() {

        Car newCar = new Car();
        newCar.setMake("BMW");
        newCar.setModel("E46");
        newCar.setTravelledDistance(250900L);
        this.carRepository.saveAndFlush(newCar);

        this.carService.deleteCar(newCar.getId());
        long expectedCount = 0;
        long actualCount = this.carRepository.count();

        assertEquals(expectedCount, actualCount);
    }

    @Test(expected = Exception.class)
    public void supplierService_deleteSupplierInvalid_ThrowsException() {

        this.carService.deleteCar("Invalid ID");
    }

    @Test
    public void supplierService_findSupplierCorrectId_ReturnCorrect() {

        Car newCar = new Car();
        newCar.setMake("BMW");
        newCar.setModel("E46");
        newCar.setTravelledDistance(250900L);
        this.carRepository.saveAndFlush(newCar);

        CarServiceModel supplierById = this.carService.findCarById(newCar.getId());

        assertEquals(newCar.getId(), supplierById.getId());
    }

    @Test(expected = Exception.class)
    public void supplierService_findSupplierIncorrectId_ThrowsException() {

        this.carService.findCarById("Invalid id");
    }
}
