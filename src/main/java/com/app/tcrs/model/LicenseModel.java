package com.app.tcrs.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class LicenseModel {

    private SimpleIntegerProperty licenseId;
    private SimpleIntegerProperty driverId;
    private SimpleStringProperty licenseNumber;
    private SimpleStringProperty licenseType;
    private LocalDate licenseExpiryDate;

    private DriverModel driver;

    public LicenseModel(){
        this.licenseId = new SimpleIntegerProperty();
        this.driverId = new SimpleIntegerProperty();
        this.licenseNumber = new SimpleStringProperty();
        this.licenseType = new SimpleStringProperty() ;
        this.licenseExpiryDate = LocalDate.now();
    }

    public LicenseModel(int licenseId, int driverId, String licenseNumber, String licenseType, LocalDate licenseExpiryDate) {
        this.licenseId = new SimpleIntegerProperty(licenseId);
        this.driverId = new SimpleIntegerProperty(driverId);
        this.licenseNumber = new SimpleStringProperty(licenseNumber);
        this.licenseType = new SimpleStringProperty(licenseType) ;
        this.licenseExpiryDate = licenseExpiryDate;
    }

    public int getLicenseId() {
        return licenseId.get();
    }

    public SimpleIntegerProperty licenseIdProperty() {
        return licenseId;
    }

    public void setLicenseId(int licenseId) {
        this.licenseId.set(licenseId);
    }

    public int getDriverId() {
        return driverId.get();
    }

    public SimpleIntegerProperty driverIdProperty() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId.set(driverId);
    }

    public String getLicenseNumber() {
        return licenseNumber.get();
    }

    public SimpleStringProperty licenseNumberProperty() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber.set(licenseNumber);
    }

    public String getLicenseType() {
        return licenseType.get();
    }

    public SimpleStringProperty licenseTypeProperty() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType.set(licenseType);
    }

    public LocalDate getLicenseExpiryDate() {
        return licenseExpiryDate;
    }

    public void setLicenseExpiryDate(LocalDate licenseExpiryDate) {
        this.licenseExpiryDate = licenseExpiryDate;
    }

    public ObjectProperty<LocalDate> expiryDateProperty() {
        ObjectProperty<LocalDate> date = new SimpleObjectProperty<>();
        date.set(licenseExpiryDate);
        return  date;
    }
    public DriverModel getDriver() {
        return driver;
    }

    public void setDriver(DriverModel driver) {
        this.driver = driver;
    }
}
