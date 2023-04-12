package com.app.tcrs.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class CitationModel {
    private SimpleIntegerProperty citationId;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private LocalDate dateOfBirth;
    private SimpleIntegerProperty age;
    private SimpleStringProperty address1;
    private SimpleStringProperty address2;
    private SimpleStringProperty city;
    private SimpleStringProperty province;
    private LocalDate citationDate;
    private SimpleStringProperty vehicleRegOrDrivingLicense;
    private SimpleStringProperty typeOfOffense;
    private LocalDate dateOfOffense;
    private SimpleDoubleProperty fineIssued;
    private LocalDate lastPaymentDate;
    private SimpleStringProperty status;
    private SimpleStringProperty remarks;
    private SimpleStringProperty issuingOfficer;

    public CitationModel() {
        this.citationId = new SimpleIntegerProperty();
        this.firstName = new SimpleStringProperty();
        this.lastName = new SimpleStringProperty();
        this.dateOfBirth = LocalDate.now();
        this.age = new SimpleIntegerProperty();
        this.address1 = new SimpleStringProperty();
        this.address2 = new SimpleStringProperty();
        this.city = new SimpleStringProperty();
        this.province = new SimpleStringProperty();
        this.vehicleRegOrDrivingLicense = new SimpleStringProperty();
        this.typeOfOffense = new SimpleStringProperty();
        this.dateOfOffense = LocalDate.now();
        this.fineIssued = new SimpleDoubleProperty();
        this.lastPaymentDate = LocalDate.now();
        this.status = new SimpleStringProperty();
        this.remarks = new SimpleStringProperty();
        this.issuingOfficer = new SimpleStringProperty();
        this.citationDate = LocalDate.now();

    }

    public CitationModel(int citationId,String firstName, String lastName, LocalDate dateOfBirth, int age, String address1, String address2, String city, String province, String vehicleRegOrDrivingLicense, String typeOfOffense, LocalDate dateOfOffense, Double fineIssued, LocalDate lastPaymentDate, String status, String remarks,  LocalDate citationDate, String issuingOfficer) {
        this.citationId = new SimpleIntegerProperty(citationId);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.dateOfBirth = dateOfBirth;
        this.age = new SimpleIntegerProperty(age);
        this.address1 = new SimpleStringProperty(address1);
        this.address2 = new SimpleStringProperty(address2);
        this.city = new SimpleStringProperty(city);
        this.province = new SimpleStringProperty(province);
        this.vehicleRegOrDrivingLicense = new SimpleStringProperty(vehicleRegOrDrivingLicense);
        this.typeOfOffense = new SimpleStringProperty(typeOfOffense);
        this.dateOfOffense = dateOfOffense;
        this.fineIssued = new SimpleDoubleProperty(fineIssued);
        this.lastPaymentDate = lastPaymentDate;
        this.status= new SimpleStringProperty(status);
        this.remarks = new SimpleStringProperty(remarks);
        this.citationDate = citationDate;
        this.issuingOfficer = new SimpleStringProperty(issuingOfficer);
    }

    public int getCitationId() {
        return citationId.get();
    }

    public SimpleIntegerProperty citationIdProperty() {
        return citationId;
    }

    public void setCitationId(int citationId) {
        this.citationId.set(citationId);
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

    public LocalDate getCitationDate() {
        return citationDate;
    }

    public void setCitationDate(LocalDate citationDate) {
        this.citationDate = citationDate;
    }

    public String getVehicleRegOrDrivingLicense() {
        return vehicleRegOrDrivingLicense.get();
    }

    public SimpleStringProperty vehicleRegOrDrivingLicenseProperty() {
        return vehicleRegOrDrivingLicense;
    }

    public void setVehicleRegOrDrivingLicense(String vehicleRegOrDrivingLicense) {
        this.vehicleRegOrDrivingLicense.set(vehicleRegOrDrivingLicense);
    }

    public String getTypeOfOffense() {
        return typeOfOffense.get();
    }

    public SimpleStringProperty typeOfOffenseProperty() {
        return typeOfOffense;
    }

    public void setTypeOfOffense(String typeOfOffense) {
        this.typeOfOffense.set(typeOfOffense);
    }

    public LocalDate getDateOfOffense() {
        return dateOfOffense;
    }

    public void setDateOfOffense(LocalDate dateOfOffense) {
        this.dateOfOffense = dateOfOffense;
    }

    public double getFineIssued() {
        return fineIssued.get();
    }

    public SimpleDoubleProperty fineIssuedProperty() {
        return fineIssued;
    }

    public void setFineIssued(double fineIssued) {
        this.fineIssued.set(fineIssued);
    }

    public LocalDate getLastPaymentDate() {
        return lastPaymentDate;
    }

    public void setLastPaymentDate(LocalDate lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getRemarks() {
        return remarks.get();
    }

    public SimpleStringProperty remarksProperty() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks.set(remarks);
    }

    public String getIssuingOfficer() {
        return issuingOfficer.get();
    }

    public SimpleStringProperty issuingOfficerProperty() {
        return issuingOfficer;
    }

    public void setIssuingOfficer(String issuingOfficer) {
        this.issuingOfficer.set(issuingOfficer);
    }
}
