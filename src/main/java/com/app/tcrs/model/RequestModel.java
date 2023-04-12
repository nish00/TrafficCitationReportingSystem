package com.app.tcrs.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class RequestModel {
    private SimpleIntegerProperty requestId;
    private SimpleStringProperty requestedBy;
    private SimpleStringProperty vehicleRegOrDrivingLicense;
    private  SimpleStringProperty requestType;
    private SimpleBooleanProperty approved;
    private SimpleStringProperty approvedBy;
    private SimpleStringProperty comment;

    public RequestModel(){
        this.requestId = new SimpleIntegerProperty();
        this.requestedBy = new SimpleStringProperty();
        this.vehicleRegOrDrivingLicense = new SimpleStringProperty();
        this.requestType = new SimpleStringProperty();
        this.approved = new SimpleBooleanProperty();
        this.approvedBy= new SimpleStringProperty();
        this.comment = new SimpleStringProperty();
    }

    public RequestModel(int requestId, String lawAgentBadgeNumber, String vehicleRegOrDrivingLicense, String requestType, Boolean approved, String approvedBy, String comment){
        this.requestId = new SimpleIntegerProperty(requestId);
        this.requestedBy = new SimpleStringProperty(lawAgentBadgeNumber);
        this.vehicleRegOrDrivingLicense = new SimpleStringProperty(vehicleRegOrDrivingLicense);
        this.requestType = new SimpleStringProperty(requestType);
        this.approved = new SimpleBooleanProperty(approved);
        this.approvedBy= new SimpleStringProperty(approvedBy);
        this.comment = new SimpleStringProperty(comment);
    }
    public int getRequestId() {
        return requestId.get();
    }

    public SimpleIntegerProperty requestIdProperty() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId.set(requestId);
    }

    public String getRequestedBy() {
        return requestedBy.get();
    }

    public SimpleStringProperty requestedByProperty() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy.set(requestedBy);
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

    public String getRequestType() {
        return requestType.get();
    }

    public SimpleStringProperty requestTypeProperty() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType.set(requestType);
    }

    public boolean isApproved() {
        return approved.get();
    }

    public SimpleBooleanProperty approvedProperty() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved.set(approved);
    }

    public String getApprovedBy() {
        return approvedBy.get();
    }

    public SimpleStringProperty approvedByProperty() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy.set(approvedBy);
    }

    public String getComment() {
        return comment.get();
    }

    public SimpleStringProperty commentProperty() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment.set(comment);
    }
}
