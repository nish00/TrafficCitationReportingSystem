package com.app.tcrs.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class DrivingSchoolModel {
    private SimpleIntegerProperty registrationId;
    private SimpleIntegerProperty citationId;
    private SimpleStringProperty drivingClass;
    private LocalDate startDate;
    private LocalDate endDate;

    public DrivingSchoolModel(){
        this.registrationId = new SimpleIntegerProperty();
        this.citationId = new SimpleIntegerProperty();
        this.drivingClass = new SimpleStringProperty();
        this.startDate = LocalDate.now();
        this.endDate = LocalDate.now().plusDays(2);
    }

    public DrivingSchoolModel(int regId, int citationId, String drivingClass, LocalDate startDate, LocalDate endDate){
        this.registrationId = new SimpleIntegerProperty(regId);
        this.citationId = new SimpleIntegerProperty(citationId);
        this.drivingClass = new SimpleStringProperty(drivingClass);
        this.startDate = LocalDate.from(startDate);
        this.endDate = LocalDate.from(endDate);
    }

    private CitationModel citationModel;

    public int getRegistrationId() {
        return registrationId.get();
    }

    public SimpleIntegerProperty registrationIdProperty() {
        return registrationId;
    }

    public void setRegistrationId(int registrationId) {
        this.registrationId.set(registrationId);
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

    public String getDrivingClass() {
        return drivingClass.get();
    }

    public SimpleStringProperty drivingClassProperty() {
        return drivingClass;
    }

    public void setDrivingClass(String drivingClass) {
        this.drivingClass.set(drivingClass);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public CitationModel getCitationModel() {
        return citationModel;
    }

    public void setCitationModel(CitationModel citationModel) {
        this.citationModel = citationModel;
    }
}
