package com.app.tcrs.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class LawAgent {
    private SimpleIntegerProperty agentId;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleStringProperty badgeNumber;
    private SimpleStringProperty uniqueKey;

    public LawAgent() {
        this.agentId = new SimpleIntegerProperty();
        this.firstName = new SimpleStringProperty();
        this.lastName = new SimpleStringProperty();
        this.badgeNumber = new SimpleStringProperty();
        this.uniqueKey = new SimpleStringProperty();
    }

    public LawAgent(int agentId, String firstName, String lastName, String badgeNumber, String uniqueKey) {
        this.agentId = new SimpleIntegerProperty(agentId);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.badgeNumber = new SimpleStringProperty(badgeNumber);
        this.uniqueKey = new SimpleStringProperty(uniqueKey);
    }

    public int getAgentId() {
        return agentId.get();
    }

    public SimpleIntegerProperty agentIdProperty() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId.set(agentId);
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
        SimpleStringProperty fullName = new SimpleStringProperty();
        fullName.set( getFirstName());
        return  fullName;
    }
    public String getFullName(){
        return getFirstName() + " " + getLastName();
    }

    public String getBadgeNumber() {
        return badgeNumber.get();
    }

    public SimpleStringProperty badgeNumberProperty() {
        return badgeNumber;
    }

    public void setBadgeNumber(String badgeNumber) {
        this.badgeNumber.set(badgeNumber);
    }

    public String getUniqueKey() {
        return uniqueKey.get();
    }

    public SimpleStringProperty uniqueKeyProperty() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey.set(uniqueKey);
    }
}
