CREATE DATABASE IF NOT EXISTS `tcrs`;

USE `tcrs`;

-- LAW AGENT TABLE
CREATE TABLE IF NOT EXISTS LawAgent (
    AgentID INT NOT NULL PRIMARY KEY AUTO_INCREMENT AUTO_INCREMENT,
    FirstName VARCHAR(255),
    LastName VARCHAR(255),
    BadgeNumber VARCHAR(255),
    UniqueKey VARCHAR(255),
    CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
-- dummy data
INSERT INTO LawAgent (AgentId, FirstName, LastName, BadgeNumber, UniqueKey)
VALUES (1, 'Jack', 'Brown', 'ABC123', md5('X1Y2Z3'));

INSERT INTO LawAgent (AgentId, FirstName, LastName, BadgeNumber, UniqueKey)
VALUES (2, 'Lisa', 'Wilson', 'DEF456', md5('A1B2C3'));

INSERT INTO LawAgent (AgentId, FirstName, LastName, BadgeNumber, UniqueKey)
VALUES (3, 'Mike', 'Chen', 'GHI789', md5('M1N2O3'));

INSERT INTO LawAgent (AgentId, FirstName, LastName, BadgeNumber, UniqueKey)
VALUES (4, 'Emily', 'Wang', 'JKL012', md5('P4Q5R6'));

INSERT INTO LawAgent (AgentId, FirstName, LastName, BadgeNumber, UniqueKey)
VALUES (5, 'Chris', 'Davis', 'MNO345', md5('S7T8U9'));

-- DRIVERS TABLE
CREATE TABLE IF NOT EXISTS Driver (
    DriverID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    FirstName VARCHAR(255),
    LastName VARCHAR(255),
    DateOfBirth DATE,
    Age INT,
    Address1 VARCHAR(255),
    Address2 VARCHAR(255),
    City VARCHAR(255),
    Province VARCHAR(255),
    LicenseID INT,
    CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- dummy data
INSERT INTO Driver (DriverId, FirstName, LastName, DateOfBirth, Age, Address1, Address2, City, Province, LicenseId)
VALUES (1, 'John', 'Doe', '1990-05-01', 32, '123 Main St', 'Apt 1A', 'Toronto', 'Ontario', 1);

INSERT INTO Driver (DriverId, FirstName, LastName, DateOfBirth, Age, Address1, Address2, City, Province, LicenseId)
VALUES (2, 'Jane', 'Smith', '1985-12-20', 36, '456 Oak St', 'Suite 5B', 'Vancouver', 'British Columbia', 2);

INSERT INTO Driver (DriverId, FirstName, LastName, DateOfBirth, Age, Address1, Address2, City, Province, LicenseId)
VALUES (3, 'Bob', 'Johnson', '1995-07-15', 26, '789 Elm St', NULL, 'Montreal', 'Quebec', 5);

INSERT INTO Driver (DriverId, FirstName, LastName, DateOfBirth, Age, Address1, Address2, City, Province, LicenseId)
VALUES (4, 'Mary', 'Lee', '2000-02-28', 23, '10 Bay St', 'Unit 100', 'Calgary', 'Alberta', 3);

INSERT INTO Driver (DriverId, FirstName, LastName, DateOfBirth, Age, Address1, Address2, City, Province, LicenseId)
VALUES (5, 'Tom', 'Kim', '1980-09-05', 41, '555 Queen St', NULL, 'Ottawa', 'Ontario', 4);

-- LICENSE
CREATE TABLE IF NOT EXISTS License (
    LicenseID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    DriverID INT,
    LicenseNumber VARCHAR(255),
    LicenseType VARCHAR(255),
    LicenseExpiryDate DATE,
    CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (DriverId) REFERENCES Driver(DriverID)
);
-- dummy data
INSERT INTO License (LicenseID, DriverID, LicenseNumber, LicenseType, LicenseExpiryDate)
VALUES (1, 1, 'A1234567', 'G', '2025-06-30');

INSERT INTO License (LicenseID, DriverID, LicenseNumber, LicenseType, LicenseExpiryDate)
VALUES (2, 2, 'B9876543', 'G', '2023-09-30');

INSERT INTO License (LicenseID, DriverID, LicenseNumber, LicenseType, LicenseExpiryDate)
VALUES (3, 4, 'C4567890', 'G', '2024-12-31');

INSERT INTO License (LicenseID, DriverID, LicenseNumber, LicenseType, LicenseExpiryDate)
VALUES (4, 5, 'D2468101', 'G', '2026-03-10');

INSERT INTO License (LicenseID, DriverID, LicenseNumber, LicenseType, LicenseExpiryDate)
VALUES (5, 3, 'D2468104', 'G', '2026-03-10');


-- PROVINCIAL AGENT
CREATE TABLE IF NOT EXISTS ProvincialAgent (
    AgentID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    FirstName VARCHAR(255),
    LastName VARCHAR(255),
    Username VARCHAR(255),
    Password VARCHAR(255),
    CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO ProvincialAgent (AgentID, FirstName, LastName, Username, Password)
VALUES
    (1, 'John', 'Doe', 'johndoe', md5('1234')),
    (2, 'Jane', 'Smith', 'janesmith', md5('1234')),
    (3, 'Bob', 'Johnson', 'bobjohnson', md5('1234')),
    (4, 'Sarah', 'Lee', 'sarahlee', md5('1234')),
    (5, 'Mike', 'Nguyen', 'mikenguyen',md5('1234'));


-- vehicle
CREATE TABLE IF NOT EXISTS Vehicle (
    VehicleID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    DriverID INT,
    OwnerFirstName VARCHAR(255),
    OwnerLastName VARCHAR(255),
    RegistrationNumber VARCHAR(255),
    Color VARCHAR(255),
    `Type` VARCHAR(255),
    Make VARCHAR(255),
    Model VARCHAR(255),
    CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (DriverID) REFERENCES Driver(DriverID)
);

INSERT INTO Vehicle (VehicleID, DriverID, OwnerFirstName, OwnerLastName, RegistrationNumber, Color, Type, Make, Model)
VALUES
    (1, 1, 'John', 'Doe', 'ABC123', 'Red', 'Car', 'Honda', 'Civic'),
    (2, 2, 'Jane', 'Smith', 'DEF456', 'Blue', 'Truck', 'Ford', 'F-150'),
    (3, 3, 'Bob', 'Johnson', 'GHI789', 'Green', 'SUV', 'Toyota', 'Highlander'),
    (4, 4, 'Sarah', 'Lee', 'JKL012', 'Black', 'Motorcycle', 'Harley-Davidson', 'Sportster'),
    (5, 5, 'Mike', 'Nguyen', 'MNO345', 'White', 'Van', 'Dodge', 'Grand Caravan');

CREATE TABLE IF NOT EXISTS Request (
    RequestID INT NOT NULL AUTO_INCREMENT,
    RequestedBy VARCHAR(255) NOT NULL,
    VehicleRegOrDrivingLicense VARCHAR(255) NOT NULL,
    RequestType VARCHAR(255) NOT NULL,
    Approved BOOLEAN NOT NULL DEFAULT false,
    ApprovedBy VARCHAR(255),
    Comment VARCHAR(400),
    CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (RequestID)
);

CREATE TABLE IF NOT EXISTS Citation (
    CitationID INT NOT NULL AUTO_INCREMENT,
    FirstName VARCHAR(255),
    LastName VARCHAR(255),
    DateOfBirth DATE,
    Age INT,
    Address1 VARCHAR(255),
    Address2 VARCHAR(255),
    City VARCHAR(255),
    Province VARCHAR(255),
    CitationDate DATE NOT NULL,
    VehicleRegOrDrivingLicense VARCHAR(255) NOT NULL,
    TypeOfOffense VARCHAR(255) NOT NULL,
    DateOfOffense DATE NOT NULL,
    FineIssued DOUBLE NOT NULL,
    LastPaymentDate DATE,
    Status VARCHAR(255) NOT NULL,
    Remarks VARCHAR(255),
    IssuingOfficer VARCHAR(255) NOT NULL,
    CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (CitationID)
);

CREATE TABLE IF NOT EXISTS DrivingSchool (
    RegistrationID INT NOT NULL AUTO_INCREMENT,
    CitationID INT NOT NULL,
    DrivingClass VARCHAR(255) NOT NULL,
    StartDate DATE,
    EndDate DATE,
    CreatedAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    UpdatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (CitationID),
    FOREIGN KEY (CitationID) REFERENCES Citation(CitationID)
);





