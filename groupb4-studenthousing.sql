-- #######################################################################
-- # CS-157A-01 GROUP B4: StudentHousingDB SQL Script
-- # Group Members: Conner Hsieh, Daniel Huynh, Akash Karthik, & Matthew Gouyer
-- #######################################################################

-- 1. SETUP: CLEAR, CREATE, AND SELECT DATABASE 
DROP DATABASE IF EXISTS StudentHousingDB;
CREATE DATABASE StudentHousingDB;
USE StudentHousingDB;


-- 2. CREATE TABLES (SCHEMA DEFINITION) - EXECUTED IN DEPENDENCY ORDER

-- PARENT TABLES
CREATE TABLE MealPlans (
    MealPlanID INT PRIMARY KEY,
    PlanName VARCHAR(20) UNIQUE,
    CostPerTerm DECIMAL(10 , 2 ),
    IsActive BOOLEAN
);
CREATE TABLE Dorms (
    DormID INT PRIMARY KEY,
    DormName VARCHAR(20) UNIQUE,
    Address VARCHAR(20),
    Gender VARCHAR(10) CHECK (Gender IN ('Male' , 'Female', 'Coed'))
);
CREATE TABLE Technicians (
    TechnicianID INT PRIMARY KEY,
    LastName VARCHAR(20),
    FirstName VARCHAR(20),
    Email VARCHAR(100) UNIQUE,
    Phone INT
);

-- DEPENDENT TABLES
CREATE TABLE Rooms (
    RoomID INT PRIMARY KEY,
    DormID INT,
    RoomNumber INT,
    UNIQUE (DormID , RoomNumber),
    FOREIGN KEY (DormID)
        REFERENCES Dorms (DormID)
);
CREATE TABLE Students (
    StudentID INT PRIMARY KEY,
    FirstName VARCHAR(20),
    Email VARCHAR(100) UNIQUE,
    FinancialAidStatus BOOLEAN,
    MealPlanID INT,
    FOREIGN KEY (MealPlanID)
        REFERENCES MealPlans (MealPlanID)
);
CREATE TABLE MaintenanceTickets (
    TicketID INT PRIMARY KEY,
    Description TEXT,
    TechnicianID INT,
    Cost DECIMAL(10 , 2),
    Status VARCHAR(10),
    CreationDate DATE,
    FinishDate DATE,
    ClosedAt DATETIME,
    FOREIGN KEY (TechnicianID)
        REFERENCES Technicians (TechnicianID)
);

-- FINAL DEPENDENT TABLES
CREATE TABLE RoomAssignments (
    AssignmentID INT PRIMARY KEY,
    StudentID INT,
    RoomID INT,
    LeaseStartDate DATE,
    LeaseEndDate DATE,
    RoomStatus BOOLEAN,
    FOREIGN KEY (StudentID)
        REFERENCES Students (StudentID),
    FOREIGN KEY (RoomID)
        REFERENCES Rooms (RoomID),
    UNIQUE (StudentID , RoomID , LeaseStartDate)
);
CREATE TABLE Requires (
    TicketID INT,
    RoomID INT,
    PRIMARY KEY (TicketID , RoomID),
    FOREIGN KEY (TicketID)
        REFERENCES MaintenanceTickets (TicketID),
    FOREIGN KEY (RoomID)
        REFERENCES Rooms (RoomID)
);


-- 3. CREATE INDEX AND TRIGGER DEFINITIONS
-- Index Definition
CREATE INDEX idx_students_email ON Students(Email);

-- Automatically set the ClosedAt timestamp when a maintenance ticket status changes to 'Closed
DELIMITER //
CREATE TRIGGER trg_set_ticket_closed_at
BEFORE UPDATE ON MaintenanceTickets
FOR EACH ROW
BEGIN
    IF NEW.Status = 'Closed' AND OLD.Status != 'Closed' THEN
        SET NEW.ClosedAt = CURRENT_TIMESTAMP;
    END IF;
END;
//
DELIMITER ;


-- 4. PART 3: INSERT DATA (Clean, continuous block for reliable execution)
INSERT INTO MealPlans (MealPlanID, PlanName, CostPerTerm, IsActive) VALUES (1, 'Basic', 1200.00, TRUE), (2, 'Standard', 1800.00, TRUE), (3, 'Premium', 2500.00, TRUE), (4, 'None', 0.00, FALSE);
INSERT INTO Dorms (DormID, DormName, Address, Gender) VALUES (1, 'Oak Hall', '100 Oak St', 'Coed'), (2, 'Maple Hall', '200 Maple Ave', 'Female'), (3, 'Pine Hall', '300 Pine Rd', 'Male');
INSERT INTO Technicians (TechnicianID, LastName, FirstName, Email, Phone) VALUES (1, 'Smith', 'John', 'jsmith@univ.edu', 5551234), (2, 'Johnson', 'Emily', 'ejohnson@univ.edu', 5555678), (3, 'Lee', 'David', 'dlee@univ.edu', 5559876), (5, 'User', 'Dummy', 'dummy@univ.edu', 5550000);
INSERT INTO Rooms (RoomID, DormID, RoomNumber) VALUES (1, 1, 101), (2, 1, 102), (3, 2, 201), (4, 2, 202), (5, 3, 301), (6, 3, 302);
INSERT INTO Students (StudentID, FirstName, Email, FinancialAidStatus, MealPlanID) VALUES (1, 'Alice', 'alice@univ.edu', TRUE, 2), (2, 'Bob', 'bob@univ.edu', FALSE, 3), (3, 'Clara', 'clara@univ.edu', TRUE, 1), (4, 'Dan', 'dan@univ.edu', FALSE, 4);
INSERT INTO MaintenanceTickets (TicketID, Description, TechnicianID, Cost, Status, CreationDate, FinishDate, ClosedAt) VALUES
(1, 'Leaky faucet in Oak Hall 101', 1, 45.00, 'Complete', '2025-09-02', '2025-09-03', NULL),
(2, 'Broken heater in Maple Hall 201', 2, 120.00, 'Pending', '2025-10-10', NULL, NULL),
(3, 'Light fixture replacement in Pine Hall 301', 3, 60.00, 'Started', '2025-11-01', NULL, NULL),
(101, 'Broken AC unit in room', 5, 150.00, 'Open', '2025-11-01', NULL, NULL);
INSERT INTO RoomAssignments (AssignmentID, StudentID, RoomID, LeaseStartDate, LeaseEndDate, RoomStatus) VALUES (1, 1, 1, '2025-08-15', '2026-05-15', TRUE), (2, 2, 2, '2025-08-15', '2026-05-15', TRUE), (3, 3, 3, '2025-08-15', '2026-05-15', TRUE), (4, 4, 5, '2025-08-15', '2026-05-15', TRUE);
INSERT INTO Requires (TicketID, RoomID) VALUES (1, 1), (2, 3), (3, 5);


-- QUERY SUITE DEMONSTRATION

-- TABLE POPULATION CHECK
SELECT * FROM Students LIMIT 5;
SELECT * FROM MaintenanceTickets LIMIT 5;


-- JOIN (3 Tables)
SELECT 
    S.StudentID, R.RoomID, MP.MealPlanID
FROM
    Students S
        LEFT JOIN
    MealPlans MP ON S.MealPlanID = MP.MealPlanID
        LEFT JOIN
    RoomAssignments RA ON S.StudentID = RA.StudentID
        LEFT JOIN
    Rooms R ON RA.RoomID = R.RoomID;


-- JOIN (2 Tables)
SELECT 
    T.FirstName, MT.TicketID
FROM
    MaintenanceTickets MT
        LEFT JOIN
    Technicians T ON T.TechnicianID = MT.TechnicianID;


-- UPDATE (using WHERE)
UPDATE MealPlans 
SET 
    CostPerTerm = 1900.00
WHERE
    MealPlanID = 2;
SELECT 
    *
FROM
    MealPlans
WHERE
    MealPlanID = 2;


-- SUBQUERY (Find which students have the 'Premium' MealPlan
SELECT
    FirstName
FROM
    Students
WHERE
    MealPlanID = (
        SELECT MealPlanID
        FROM MealPlans
        WHERE PlanName = 'Premium'
    );


-- TRIGGER DEMO
UPDATE MaintenanceTickets 
SET 
    Status = 'Closed'
WHERE
    TicketID = 101;
SELECT 
    TicketID, Status, ClosedAt
FROM
    MaintenanceTickets
WHERE
    TicketID = 101;


-- INDEX DEMO
SELECT 
    StudentID, FirstName, FinancialAidStatus
FROM
    Students
WHERE
    Email = 'alice@univ.edu'; 

-- VIEWS
CREATE VIEW Student_Info AS 
SELECT StudentID, FirstName, MealPlanID, FinancialAidStatus
FROM Students;

CREATE VIEW Student_Count AS
SELECT COUNT(*)
FROM Students;

-- STORED ROUTINE
CREATE PROCEDURE AddNewStudent (
	IN S_ID int,
    IN S_FName VARCHAR(20),
    IN S_Email VARCHAR(100),
    IN S_FinancialAidStatus BOOLEAN,
    IN S_MealPlanID INT	
)
INSERT INTO Students (StudentID, FirstName, Email, FinancialAidStatus, MealPlanID)
values(S_ID, S_FName, S_Email, S_FinancialAidStatus, S_MealPlanID);

