package com.app.tcrs.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class DriverModel {
    private SimpleIntegerProperty driverId;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private LocalDate dateOfBirth;
    private SimpleIntegerProperty age;
    private SimpleStringProperty address1;
    private SimpleStringProperty address2;
    private SimpleStringProperty city;
    private SimpleStringProperty province;
    private SimpleIntegerProperty licenseId;

    private LicenseModel license = null;

    public DriverModel() {
        this.driverId = new SimpleIntegerProperty();
        this.firstName = new SimpleStringProperty();
        this.lastName = new SimpleStringProperty();
        this.dateOfBirth = LocalDate.now();
        this.age = new SimpleIntegerProperty();
        this.address1 = new SimpleStringProperty();
        this.address2 = new SimpleStringProperty();
        this.city = new SimpleStringProperty();
        this.province = new SimpleStringProperty();
    }

    public DriverModel(int driverId, String firstName, String lastName, LocalDate dateOfBirth, int age, String address1, String address2, String city, String province) {
        this.driverId = new SimpleIntegerProperty(driverId);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.dateOfBirth = dateOfBirth;
        this.age = new SimpleIntegerProperty(age);
        this.address1 = new SimpleStringProperty(address1);
        this.address2 = new SimpleStringProperty(address2);
        this.city = new SimpleStringProperty(city);
        this.province = new SimpleStringProperty(province);
    }
    public DriverModel(int driverId, String firstName, String lastName, LocalDate dateOfBirth, int age, String address1, String address2, String city, String province, int licenseId) {
        this.driverId = new SimpleIntegerProperty(driverId);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.dateOfBirth = dateOfBirth;
        this.age = new SimpleIntegerProperty(age);
        this.address1 = new SimpleStringProperty(address1);
        this.address2 = new SimpleStringProperty(address2);
        this.city = new SimpleStringProperty(city);
        this.province = new SimpleStringProperty(province);
        this.licenseId = new SimpleIntegerProperty(licenseId);
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

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }
    public SimpleStringProperty fullNameProperty(){
        return new SimpleStringProperty(getFullName());
    }
    public String getFullName(){
        return getFirstName() + " " + getLastName();
    }
    public ObjectProperty<LocalDate> dobProperty() {
        ObjectProperty<LocalDate> date = new SimpleObjectProperty<>();
        date.set(dateOfBirth);
        return  date;
    }
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getAge() {
        return age.get();
    }

    public SimpleIntegerProperty ageProperty() {
        return age;
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    public String getAddress1() {
        return address1.get();
    }

    public SimpleStringProperty address1Property() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1.set(address1);
    }

    public String getAddress2() {
        return address2.get();
    }

    public SimpleStringProperty address2Property() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2.set(address2);
    }

    public String getCity() {
        return city.get();
    }

    public SimpleStringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public String getProvince() {
        return province.get();
    }

    public SimpleStringProperty provinceProperty() {
        return province;
    }

    public void setProvince(String province) {
        this.province.set(province);
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

    public LicenseModel getLicense() {
        return license;
    }

    public void setLicense(LicenseModel license) {
        this.license = license;
    }
}