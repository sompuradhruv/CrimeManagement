
CREATE DATABASE learn;
USE learn;

CREATE TABLE Agency (
    agencyID INT PRIMARY KEY AUTO_INCREMENT,      -- Primary Key with auto-increment
    agencyName VARCHAR(100) NOT NULL,             -- Name of the agency
    jurisdiction VARCHAR(100) NOT NULL,           -- Jurisdiction of the agency
    contactInfo VARCHAR(255),                      -- Contact information
    officer VARCHAR(100)                           -- Officer associated with the agency
);

CREATE TABLE Case_ (
    caseID INT PRIMARY KEY AUTO_INCREMENT,         -- Primary Key with auto-increment
    caseDescription VARCHAR(255) NOT NULL          -- Description of the case
);
CREATE TABLE IF NOT EXISTS Case_Incident(
    caseID INT NOT NULL,
    incidentID INT NOT NULL,
    PRIMARY KEY (caseID, incidentID),
    FOREIGN KEY (caseID) REFERENCES Case_(caseID) ON DELETE CASCADE,
    FOREIGN KEY (incidentID) REFERENCES Incident(incidentID) ON DELETE CASCADE
);

CREATE TABLE Evidence (
    evidenceID INT PRIMARY KEY AUTO_INCREMENT,     -- Primary Key with auto-increment
    description VARCHAR(255) NOT NULL,             -- Description of the evidence
    locationFound VARCHAR(255),                    -- Location where the evidence was found
    incidentID INT,                                -- Foreign Key referencing the Incident table
    FOREIGN KEY (incidentID) REFERENCES Incident(incidentID) ON DELETE CASCADE ON UPDATE CASCADE -- Foreign Key constraint
);

CREATE TABLE Incident (
    incidentID INT PRIMARY KEY AUTO_INCREMENT,      -- Primary Key with auto-increment
    incidentType VARCHAR(100) NOT NULL,            -- Type of incident
    incidentDate DATE NOT NULL,                     -- Date of incident
    latitude DOUBLE,                                -- Latitude of the incident location
    longitude DOUBLE,                               -- Longitude of the incident location
    description VARCHAR(500),                       -- Description of the incident
    status VARCHAR(50),                             -- Status of the incident
    victimID INT,                                   -- Foreign Key to Victim
    suspectID INT,                                  -- Foreign Key to Suspect
    agencyID INT,                                   -- Foreign Key to Agency
    FOREIGN KEY (agencyID) REFERENCES Agency(agencyID) ON DELETE SET NULL ON UPDATE CASCADE,  -- Foreign Key constraint to Agency
    FOREIGN KEY (victimID) REFERENCES Victim(victimID) ON DELETE SET NULL ON UPDATE CASCADE,  -- Foreign Key constraint to Victim
    FOREIGN KEY (suspectID) REFERENCES Suspect(suspectID) ON DELETE SET NULL ON UPDATE CASCADE -- Foreign Key constraint to Suspect
);

CREATE TABLE Officer (
    officerID INT PRIMARY KEY AUTO_INCREMENT,       -- Primary Key with auto-increment
    firstName VARCHAR(100) NOT NULL,               -- First Name of the Officer
    lastName VARCHAR(100) NOT NULL,                -- Last Name of the Officer
    badgeNumber VARCHAR(50) UNIQUE NOT NULL,       -- Unique Badge Number
    rank_ VARCHAR(50),                              -- Rank of the Officer
    contactInfo VARCHAR(255),                       -- Contact Information of the Officer
    agencyID INT,                                   -- Foreign Key to the Agency
    FOREIGN KEY (agencyID) REFERENCES Agency(agencyID) ON DELETE SET NULL ON UPDATE CASCADE -- Foreign Key constraint to Agency
);

CREATE TABLE Report (
    reportID INT PRIMARY KEY AUTO_INCREMENT,        -- Primary Key with auto-increment
    incidentID INT NOT NULL,                        -- Foreign Key referencing Incident
    reportingOfficer INT,                           -- Foreign Key referencing Officer (now nullable)
    reportDate DATE NOT NULL,                       -- Date of the report
    reportDetails TEXT,                             -- Detailed description of the report
    status VARCHAR(50),                             -- Status of the report (e.g., open, closed)
    FOREIGN KEY (incidentID) REFERENCES Incident(incidentID) ON DELETE CASCADE ON UPDATE CASCADE, -- Foreign Key to Incident table
    FOREIGN KEY (reportingOfficer) REFERENCES Officer(officerID) ON DELETE SET NULL ON UPDATE CASCADE -- Foreign Key to Officer table
);

CREATE TABLE Suspect (
    suspectID INT PRIMARY KEY AUTO_INCREMENT,       -- Primary Key with auto-increment
    firstName VARCHAR(100) NOT NULL,               -- First Name of the suspect
    lastName VARCHAR(100) NOT NULL,                -- Last Name of the suspect
    dateOfBirth DATE NOT NULL,                      -- Date of Birth of the suspect
    gender VARCHAR(10),                            -- Gender of the suspect
    contactInfo VARCHAR(255)                        -- Contact information for the suspect
);

CREATE TABLE Victim (
    victimID INT PRIMARY KEY AUTO_INCREMENT,       -- Primary Key with auto-increment
    firstName VARCHAR(100) NOT NULL,               -- First Name of the victim
    lastName VARCHAR(100) NOT NULL,                -- Last Name of the victim
    dateOfBirth DATE NOT NULL,                      -- Date of Birth of the victim
    gender VARCHAR(10),                            -- Gender of the victim
    contactInfo VARCHAR(255)                        -- Contact information of the victim
);

CREATE TABLE LawEnforcementAgencies (
    agencyID INT PRIMARY KEY AUTO_INCREMENT,        -- Primary Key with auto-increment
    agencyName VARCHAR(255) NOT NULL,              -- Name of the agency
    jurisdiction VARCHAR(255) NOT NULL,            -- Jurisdiction of the agency
    contactInfo VARCHAR(255) NOT NULL              -- Contact information for the agency
);


