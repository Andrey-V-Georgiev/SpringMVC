package org.softuni.cardealer;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.softuni.cardealer.domain.entities.Supplier;
import org.softuni.cardealer.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SupplierControllerTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private SupplierRepository supplierRepository;

    @Before
    public void emptyDB() {
        this.supplierRepository.deleteAll();
    }

    @Test
    @WithMockUser("spring")
    public void add_saveEntityProperly() throws Exception {
        this.mvc
                .perform(
                        post("/suppliers/add")
                                .param("name", "softuni")
                                .param("isImporter", "false")
                );
        this.mvc
                .perform(
                        post("/suppliers/add")
                                .param("name", "stamat")
                                .param("isImporter", "true")
                );

        Supplier actual = this.supplierRepository.findAll().get(1);
        Assert.assertEquals(2, this.supplierRepository.count());
        Assert.assertEquals("stamat", actual.getName());
        Assert.assertTrue(actual.getIsImporter());
    }

    @Test
    @WithMockUser("spring")
    public void add_redirectCorrectly() throws Exception {
        this.mvc
                .perform(
                        post("/suppliers/add")
                                .param("name", "softuni")
                                .param("isImporter", "false")
                )
                .andExpect(redirectedUrl("all"));
    }

    @Test
    @WithMockUser("spring")
    public void edit_worksCorrectly() throws Exception {

        Supplier first = new Supplier();
        first.setName("softuni");
        first.setIsImporter(false);

        Supplier second = new Supplier();
            second.setName("stamat");
            second.setIsImporter(true);

        first = this.supplierRepository.saveAndFlush(first);
        second = this.supplierRepository.saveAndFlush(second);

        this.mvc
                .perform(
                        post("/suppliers/edit/" + first.getId())
                                .param("name", "SoftUni")
                                .param("isImporter", "true")
                );
        this.mvc
                .perform(
                        post("/suppliers/edit/" + second.getId())
                                .param("name", "Stamat")
                                .param("isImporter", "false")
                );
        Supplier firstActual = this.supplierRepository.findById(first.getId()).orElse(null);
        Supplier secondActual = this.supplierRepository.findById(second.getId()).orElse(null);

        Assert.assertEquals("SoftUni", firstActual.getName());
        Assert.assertTrue(firstActual.getIsImporter());

        Assert.assertEquals("Stamat", secondActual.getName());
        Assert.assertFalse(secondActual.getIsImporter());
    }

    @Test
    @WithMockUser("spring")
    public void delete_worksCorrectly() throws Exception {

        Supplier first = new Supplier();
        first.setName("softuni");
        first.setIsImporter(false);

        Supplier second = new Supplier();
        second.setName("stamat");
        second.setIsImporter(true);

        first = this.supplierRepository.saveAndFlush(first);
        second = this.supplierRepository.saveAndFlush(second);

        this.mvc
                .perform(
                        post("/suppliers/delete/" + first.getId()));

        Assert.assertEquals(1, this.supplierRepository.count());

        this.mvc
                .perform(
                        post("/suppliers/delete/" + second.getId()));

        Assert.assertEquals(0, this.supplierRepository.count());
    }

    @Test(expected = Exception.class)
    @WithMockUser("spring")
    public void delete_throwsException() throws Exception {

        this.mvc
                .perform(
                        post("/suppliers/delete/" + "Pesho"));

    }

    @Test
    public void all_withGuestRedirectToLogin() throws Exception {
        this.mvc
                .perform(get("/suppliers/all"))
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithMockUser("spring")
    public void all_returnCorrectView() throws Exception {
        this.mvc
                .perform(get("/suppliers/all"))
                .andExpect(view().name("all-suppliers"))
                .andExpect(model().attributeExists("suppliers"));
    }
}
