package org.softuni.cardealer.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Customer;
import org.softuni.cardealer.domain.models.service.CustomerServiceModel;
import org.softuni.cardealer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CustomerServiceTests {

    @Autowired
    private CustomerRepository customerRepository;
    private ModelMapper modelMapper = new ModelMapper();
    private CustomerService customerService;

    @Before
    public void init() {
        this.customerService = new CustomerServiceImpl(this.customerRepository, this.modelMapper);
    }

    //    CustomerServiceModel saveCustomer(CustomerServiceModel customerServiceModel);
    @Test
    public void customerService_saveCustomerCorrectValue_ReturnCorrect() {
        Customer customer = new Customer();
        customer.setName("Koko");
        customer.setBirthDate(LocalDate.of(2004, 5, 1));

        CustomerServiceModel actual = this.customerService
                .saveCustomer(this.modelMapper.map(customer, CustomerServiceModel.class));
        CustomerServiceModel expected = this.modelMapper
                .map(this.customerRepository.findAll().get(0), CustomerServiceModel.class);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getBirthDate(), actual.getBirthDate());
    }

    @Test(expected = Exception.class)
    public void customerService_saveCustomerWithoutBirthDate_ThrowsException() {
        Customer customer = new Customer();
        customer.setName("Koko");

        CustomerServiceModel actual = this.customerService
                .saveCustomer(this.modelMapper.map(customer, CustomerServiceModel.class));
    }

    //    CustomerServiceModel editCustomer(CustomerServiceModel customerServiceModel);
    @Test
    public void customerService_editCustomerCorrectValue_ReturnCorrect() {
        Customer customer = new Customer();
        customer.setName("Koko");
        customer.setBirthDate(LocalDate.of(2004, 5, 1));
        this.customerRepository.saveAndFlush(customer);

        CustomerServiceModel customerServiceModel = new CustomerServiceModel();
        customerServiceModel.setId(customer.getId());
        customerServiceModel.setName("Gatzo");
        customerServiceModel.setBirthDate(LocalDate.of(1985, 5, 1));

        CustomerServiceModel actual = this.customerService.editCustomer(customerServiceModel);
        CustomerServiceModel expected = this.modelMapper
                .map(this.customerRepository.findAll().get(0), CustomerServiceModel.class);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getBirthDate(), actual.getBirthDate());
    }

    @Test(expected = Exception.class)
    public void customerService_editCustomerWithoutBirthdayDate_ThrowsException() {
        Customer customer = new Customer();
        customer.setName("Koko");
        customer.setBirthDate(LocalDate.of(2004, 5, 1));
        this.customerRepository.saveAndFlush(customer);

        CustomerServiceModel customerServiceModel = new CustomerServiceModel();
        customerServiceModel.setId(customer.getId());
        customerServiceModel.setName("Gatzo");

        this.customerService.editCustomer(customerServiceModel);
    }

    //    CustomerServiceModel deleteCustomer(String id);
    @Test
    public void customerService_deleteCustomerCorrectValue_ReturnCorrect() {
        Customer customer = new Customer();
        customer.setName("Koko");
        customer.setBirthDate(LocalDate.of(2004, 5, 1));
        this.customerRepository.saveAndFlush(customer);

        this.customerService.deleteCustomer(customer.getId());
        long expectedCount = 0;
        long actualCount = this.customerRepository.count();

        assertEquals(expectedCount, actualCount);
    }

    @Test(expected = Exception.class)
    public void customerService_deleteCustomerWithoutName_ThrowsException() {
        Customer customer = new Customer();
        customer.setBirthDate(LocalDate.of(2004, 5, 1));
        this.customerRepository.saveAndFlush(customer);

        this.customerService.deleteCustomer(customer.getId());
    }

    //    CustomerServiceModel findCustomerById(String id);
    @Test
    public void customerService_findCustomerByIdCorrectValue_ReturnCorrect() {
        Customer customer = new Customer();
        customer.setName("Koko");
        customer.setBirthDate(LocalDate.of(2004, 5, 1));
        this.customerRepository.saveAndFlush(customer);

        CustomerServiceModel supplierById = this.customerService.findCustomerById(customer.getId());

        assertEquals(customer.getId(), supplierById.getId());
    }

    @Test(expected = Exception.class)
    public void customerService_findCustomerByIdInvalidId_ThrowsException() {

        this.customerService.findCustomerById("Invalid ID");
    }

}
