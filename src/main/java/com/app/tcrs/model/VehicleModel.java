package com.app.tcrs.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class VehicleModel {
    private SimpleIntegerProperty vehicleId;
    private SimpleIntegerProperty driverId;
    private SimpleStringProperty ownerFirstName;
    private SimpleStringProperty ownerLastName;
    private SimpleStringProperty registrationNumber;
    private SimpleStringProperty color;
    private SimpleStringProperty type;
    private SimpleStringProperty make;
    private SimpleStringProperty model;

    private DriverModel driver = null;

    public VehicleModel(){
        this.vehicleId = new SimpleIntegerProperty() ;
        this.ownerFirstName = new SimpleStringProperty();
        this.ownerLastName =new SimpleStringProperty();
        this.registrationNumber = new SimpleStringProperty();
        this.color = new SimpleStringProperty();
        this.type = new SimpleStringProperty();
        this.make =  new SimpleStringProperty();
        this.model = new SimpleStringProperty();
        this.driverId = new SimpleIntegerProperty();
    }

    public VehicleModel(int vehicleId, String ownerFirstName, String ownerLastName, String registrationNumber, String color, String type, String make, String model) {
        this.vehicleId = new SimpleIntegerProperty(vehicleId) ;
        this.ownerFirstName = new SimpleStringProperty(ownerFirstName);
        this.ownerLastName =new SimpleStringProperty(ownerLastName);
        this.registrationNumber = new SimpleStringProperty(registrationNumber);
        this.color = new SimpleStringProperty(color);
        this.type = new SimpleStringProperty(type);
        this.make =  new SimpleStringProperty(make);
        this.model = new SimpleStringProperty(model);
    }

    public VehicleModel(int vehicleId, int driverId, String ownerFirstName, String ownerLastName, String registrationNumber, String color, String type, String make, String model) {
        this.vehicleId = new SimpleIntegerProperty(vehicleId) ;
        this.driverId = new SimpleIntegerProperty(driverId) ;
        this.ownerFirstName = new SimpleStringProperty(ownerFirstName);
        this.ownerLastName =new SimpleStringProperty(ownerLastName);
        this.registrationNumber = new SimpleStringProperty(registrationNumber);
        this.color = new SimpleStringProperty(color);
        this.type = new SimpleStringProperty(type);
        this.make =  new SimpleStringProperty(make);
        this.model = new SimpleStringProperty(model);
    }

    public int getVehicleId() {
        return vehicleId.get();
    }

    public SimpleIntegerProperty vehicleIdProperty() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId.set(vehicleId);
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

    public String getOwnerFirstName() {
        return ownerFirstName.get();
    }

    public SimpleStringProperty ownerFirstNameProperty() {
        return ownerFirstName;
    }

    public void setOwnerFirstName(String ownerFirstName) {
        this.ownerFirstName.set(ownerFirstName);
    }

    public String getOwnerLastName() {
        return ownerLastName.get();
    }

    public SimpleStringProperty ownerLastNameProperty() {
        return ownerLastName;
    }

    public void setOwnerLastName(String ownerLastName) {
        this.ownerLastName.set(ownerLastName);
    }
    public SimpleStringProperty ownerFullNameProperty(){
        return new SimpleStringProperty(getFullName());
    }
    public String getFullName(){
        return getOwnerFirstName() + " " + getOwnerLastName();
    }
    public String getRegistrationNumber() {
        return registrationNumber.get();
    }

    public SimpleStringProperty registrationNumberProperty() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber.set(registrationNumber);
    }

    public String getColor() {
        return color.get();
    }

    public SimpleStringProperty colorProperty() {
        return color;
    }

    public void setColor(String color) {
        this.color.set(color);
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getMake() {
        return make.get();
    }

    public SimpleStringProperty makeProperty() {
        return make;
    }

    public void setMake(String make) {
        this.make.set(make);
    }

    public String getModel() {
        return model.get();
    }

    public SimpleStringProperty modelProperty() {
        return model;
    }

    public void setModel(String model) {
        this.model.set(model);
    }

    public DriverModel getDriver() {
        return driver;
    }

    public void setDriver(DriverModel driver) {
        this.driver = driver;
    }
}
