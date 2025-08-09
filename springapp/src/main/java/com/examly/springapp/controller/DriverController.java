package com.examly.springapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.examly.springapp.model.Driver;
import com.examly.springapp.service.DriverService;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @PostMapping
    public Driver createDriver(@RequestBody Driver driver) {
        return driverService.saveDriver(driver);
    }

    @GetMapping
    public List<Driver> getAllDrivers() {
        return driverService.getAllDrivers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Driver> getDriverById(@PathVariable Long id) {
        Optional<Driver> driver = driverService.getDriverById(id);
        if (driver.isPresent()) {
            return ResponseEntity.ok(driver.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Driver> updateDriver(@PathVariable Long id, @RequestBody Driver driverDetails) {
        Optional<Driver> driverOptional = driverService.getDriverById(id);
        if (!driverOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Driver driver = driverOptional.get();
        driver.setDriverName(driverDetails.getDriverName());
        driver.setCity(driverDetails.getCity());
        driver.setPhone(driverDetails.getPhone());
        driver.setVehicleType(driverDetails.getVehicleType());
        driver.setLicenseNumber(driverDetails.getLicenseNumber());
        driver.setAssignedArea(driverDetails.getAssignedArea());

        Driver updatedDriver = driverService.saveDriver(driver);
        return ResponseEntity.ok(updatedDriver);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable Long id) {
        Optional<Driver> driverOptional = driverService.getDriverById(id);
        if (!driverOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        driverService.deleteDriver(id);
        return ResponseEntity.noContent().build();
    }
}
