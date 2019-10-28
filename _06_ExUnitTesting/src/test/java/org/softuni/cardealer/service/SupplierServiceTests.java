package org.softuni.cardealer.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Supplier;
import org.softuni.cardealer.domain.models.service.SupplierServiceModel;
import org.softuni.cardealer.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SupplierServiceTests {

    @Autowired
    private SupplierRepository supplierRepository;
    private ModelMapper modelMapper = new ModelMapper();
    private SupplierService supplierService;

    @Before
    public void init() {
        this.supplierService = new SupplierServiceImpl(this.supplierRepository, this.modelMapper);
    }

    @Test
    public void supplierService_saveSupplierCorrectValues_ReturnsCorrect() {

        SupplierServiceModel newSupplier = new SupplierServiceModel();
        newSupplier.setName("Pesho");
        newSupplier.setImporter(true);

        SupplierServiceModel actual = this.supplierService.saveSupplier(newSupplier);
        SupplierServiceModel expected = this.modelMapper
                .map(this.supplierRepository.findAll().get(0), SupplierServiceModel.class);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.isImporter(), actual.isImporter());
    }

    @Test(expected = Exception.class)
    public void supplierService_saveSupplierNullValues_ThrowsException() {

        SupplierServiceModel newSupplier = new SupplierServiceModel();
        newSupplier.setName(null);
        newSupplier.setImporter(true);

        SupplierServiceModel actual = this.supplierService.saveSupplier(newSupplier);
    }

    @Test
    public void supplierService_editSupplierCorrectValue_ReturnCorrect() {

        Supplier supplier = new Supplier();
        supplier.setName("Pesho");
        supplier.setImporter(true);

        supplier = this.supplierRepository.saveAndFlush(supplier);

        SupplierServiceModel supplierServiceModel = new SupplierServiceModel();
        supplierServiceModel.setId(supplier.getId());
        supplierServiceModel.setName("Gosho");
        supplierServiceModel.setImporter(false);

        SupplierServiceModel actual = this.supplierService.editSupplier(supplierServiceModel);
        SupplierServiceModel expected = this.modelMapper
                .map(this.supplierRepository.findAll().get(0), SupplierServiceModel.class);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.isImporter(), actual.isImporter());
    }

    @Test(expected = Exception.class)
    public void supplierService_editSupplierNullValue_ThrowsException() {

        Supplier supplier = new Supplier();
        supplier.setName("Pesho");
        supplier.setImporter(true);

        supplier = this.supplierRepository.saveAndFlush(supplier);

        SupplierServiceModel supplierServiceModel = new SupplierServiceModel();
        supplierServiceModel.setId(supplier.getId());
        supplierServiceModel.setName(null);
        supplierServiceModel.setImporter(false);

        SupplierServiceModel actual = this.supplierService.editSupplier(supplierServiceModel);
    }

    @Test
    public void supplierService_deleteSupplierValid_ReturnCorrect() {

        Supplier supplier = new Supplier();
        supplier.setName("Pesho");
        supplier.setImporter(true);

        supplier = this.supplierRepository.saveAndFlush(supplier);
        this.supplierService.deleteSupplier(supplier.getId());
        long expectedCount = 0;
        long actualCount = this.supplierRepository.count();

        assertEquals(expectedCount, actualCount);
    }

    @Test(expected = Exception.class)
    public void supplierService_deleteSupplierInvalid_ThrowsException() {

        Supplier supplier = new Supplier();
        supplier.setName("Pesho");
        supplier.setImporter(true);

        supplier = this.supplierRepository.saveAndFlush(supplier);
        this.supplierService.deleteSupplier("Invalid id");
    }

    @Test
    public void supplierService_findSupplierCorrectId_ReturnCorrect() {

        Supplier supplier = new Supplier();
        supplier.setName("Pesho");
        supplier.setImporter(true);

        supplier = this.supplierRepository.saveAndFlush(supplier);
        SupplierServiceModel supplierById = this.supplierService.findSupplierById(supplier.getId());

        assertEquals(supplier.getId(), supplierById.getId());
    }

    @Test(expected = Exception.class)
    public void supplierService_findSupplierIncorrectId_ThrowsException() {

        Supplier supplier = new Supplier();
        supplier.setName("Pesho");
        supplier.setImporter(true);

        this.supplierRepository.saveAndFlush(supplier);
        this.supplierService.findSupplierById("Invalid id");
    }
}
