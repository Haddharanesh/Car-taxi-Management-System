package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Driver;
import com.examly.springapp.repository.DriverRepo;

@Service
public class DriverService {

    @Autowired
    private DriverRepo driverRepo;

    public Driver saveDriver(Driver driver) {
        return driverRepo.save(driver);
    }

    public List<Driver> getAllDrivers() {
        return driverRepo.findAll();
    }

    public Optional<Driver> getDriverById(Long driverId) {
        return driverRepo.findById(driverId);
    }

    public void deleteDriver(Long driverId) {
        driverRepo.deleteById(driverId);
    }
}
