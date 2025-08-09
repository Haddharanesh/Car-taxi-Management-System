package com.examly.springapp;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = SpringappApplication.class)
@AutoConfigureMockMvc
class SpringappApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    // ✅ Basic CRUD endpoint tests (example: addDriver, getAllDrivers)
    @Test
    void testAddDriver() throws Exception {
        String json = "{\"driverName\": \"John Doe\", \"driverCity\": \"Chennai\", \"phone\": \"9876543210\", \"licenseNumber\": \"DL12345678\", \"vehicleType\": \"Sedan\"}";
        mockMvc.perform(post("/addDriver")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllDrivers() throws Exception {
        mockMvc.perform(get("/getAllDrivers"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    // ✅ File existence tests
    @Test
    void test_Controller_Directory_Exists() {
        assertTrue(new File("src/main/java/com/examly/springapp/controller").isDirectory());
    }

    @Test
    void test_DriverController_File_Exists() {
        assertTrue(new File("src/main/java/com/examly/springapp/controller/DriverController.java").isFile());
    }

    @Test
    void test_Model_Directory_Exists() {
        assertTrue(new File("src/main/java/com/examly/springapp/model").isDirectory());
    }

    @Test
    void test_DriverModel_File_Exists() {
        assertTrue(new File("src/main/java/com/examly/springapp/model/Driver.java").isFile());
    }

    @Test
    void test_Repository_Directory_Exists() {
        assertTrue(new File("src/main/java/com/examly/springapp/repository").isDirectory());
    }

    @Test
    void test_Service_Directory_Exists() {
        assertTrue(new File("src/main/java/com/examly/springapp/service").isDirectory());
    }

    @Test
    void test_Configuration_Directory_Exists() {
        assertTrue(new File("src/main/java/com/examly/springapp/configuration").isDirectory());
    }

    // ✅ Class existence checks
    @Test
    void test_DriverController_Class_Exists() {
        checkClassExists("com.examly.springapp.controller.DriverController");
    }

    @Test
    void test_DriverRepo_Class_Exists() {
        checkClassExists("com.examly.springapp.repository.DriverRepo");
    }

    @Test
    void test_DriverService_Class_Exists() {
        checkClassExists("com.examly.springapp.service.DriverService");
    }

    @Test
    void test_Driver_Model_Class_Exists() {
        checkClassExists("com.examly.springapp.model.Driver");
    }

    // ✅ Field existence in model
    @Test
    void test_Driver_Model_Has_driverName_Field() {
        checkFieldExists("com.examly.springapp.model.Driver", "driverName");
    }

    

    @Test
    void test_Driver_Model_Has_phone_Field() {
        checkFieldExists("com.examly.springapp.model.Driver", "phone");
    }

    @Test
    void test_Driver_Model_Has_licenseNumber_Field() {
        checkFieldExists("com.examly.springapp.model.Driver", "licenseNumber");
    }

    @Test
    void test_Driver_Model_Has_vehicleType_Field() {
        checkFieldExists("com.examly.springapp.model.Driver", "vehicleType");
    }

    // ✅ Cors Configuration class tests
    @Test
    void test_TaxiAppCorsConfiguration_Class_Exists() {
        checkClassExists("com.examly.springapp.configuration.TaxiAppCorsConfiguration");
    }

    @Test
    void test_TaxiAppCorsConfiguration_Has_Configuration_Annotation() {
        checkClassHasAnnotation("com.examly.springapp.configuration.TaxiAppCorsConfiguration",
                "org.springframework.context.annotation.Configuration");
    }

    @Test
    void test_TaxiAppCorsConfiguration_Implements_WebMvcConfigurer() {
        checkClassImplementsInterface("com.examly.springapp.configuration.TaxiAppCorsConfiguration",
                "org.springframework.web.servlet.config.annotation.WebMvcConfigurer");
    }

    // ✅ Utility methods
    private void checkClassExists(String className) {
        try {
            Class.forName(className);
        } catch (ClassNotFoundException e) {
            fail("Class not found: " + className);
        }
    }

    private void checkFieldExists(String className, String fieldName) {
        try {
            Class<?> clazz = Class.forName(className);
            clazz.getDeclaredField(fieldName);
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            fail("Field " + fieldName + " not found in class: " + className);
        }
    }

    private void checkClassHasAnnotation(String className, String annotationClassName) {
        try {
            Class<?> clazz = Class.forName(className);
            Class<?> annotationClass = Class.forName(annotationClassName);
            assertTrue(clazz.isAnnotationPresent((Class<? extends java.lang.annotation.Annotation>) annotationClass));
        } catch (Exception e) {
            fail("Annotation or class not found.");
        }
    }

    private void checkClassImplementsInterface(String className, String interfaceName) {
        try {
            Class<?> clazz = Class.forName(className);
            Class<?> iface = Class.forName(interfaceName);
            assertTrue(iface.isAssignableFrom(clazz));
        } catch (Exception e) {
            fail("Class " + className + " does not implement " + interfaceName);
        }
    }
}
