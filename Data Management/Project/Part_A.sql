-- DROP TABLES
-- SQL is a fucking pain in my ass....have to drop tables in certain order due to primary key constraints
-- OR we can drop the foreign key constraints b4 dropping table....not worth time just drop tables in correct order.
-- Drop training first, skill can't be dropped b4 due to unique/primary keys in table referenced by foreign keys"
DROP TABLE training; 
DROP TABLE assignment;
DROP TABLE project;
DROP TABLE skill;
DROP TABLE employee;
DROP TABLE department;
DROP TABLE client;





-- Create Skill (Contraints PK and NOT NULL(arbitrary...), Skill Name should be unique)
CREATE TABLE Skill
(Code CHAR(4) CONSTRAINT Skill_CODE_PK PRIMARY KEY,
Name VARCHAR2(20) CONSTRAINT Skill_NAME_NN NOT NULL
CONSTRAINT Skill_NAME_UK UNIQUE,
Category VARCHAR2(20));   
-- Category changed from 12 to 20





/*
Can't create Training before Employee and can't create Employee before department. 

Department has FK Manager_ID, which will have to be added after table creation(THIS IS THE 1 CONSTRAINT THAT COMES AFTER CREATE TABLES)
*/


-- Create Department (PK, FK and NOT NULL CONSTRAINTS)
-- REMEMBER!!!!! -- Add Manager_ID FK after creation of EMPLOYEE

CREATE TABLE Department
(Dept_Code CHAR(2)  CONSTRAINT Department_DEPT_CODE_PK PRIMARY KEY,
Name VARCHAR2(20) CONSTRAINT Department_NAME_NN NOT NULL,  -- VARCHAR to VARCHAR2
Location VARCHAR(12) CONSTRAINT Department_LOCATION_NN NOT NULL,  
Phone CHAR(10),   
Manager_ID CHAR(8));



-- Create Employee (PL, FK, NOT NULL, hire_date, start_date, date_assigned CONSTRAINTS)
CREATE TABLE Employee
(Emp_Num CHAR(8) CONSTRAINT Employee_EMP_NUM_PK PRIMARY KEY,
LName VARCHAR2(20) CONSTRAINT Employee_LNAME_NN NOT NULL,
FName VARCHAR2(20) CONSTRAINT Employee_FNAME_NN NOT NULL,
DOB DATE,
Hire_Date DATE DEFAULT SYSDATE,
Super_ID CHAR(8) CONSTRAINT Employee_SUPER_ID_FK REFERENCES Employee(Emp_Num),
Dept_Code CHAR(2) CONSTRAINT Employee_DEPT_CODE_FK REFERENCES Department (Dept_Code));




-- Create Training (PK and FK Constraints)
CREATE TABLE Training
(Train_Num CHAR(4) CONSTRAINT Training_TRAIN_NUM_PK PRIMARY KEY,
Code CHAR(4) CONSTRAINT Training_CODE_FK REFERENCES Skill (Code),
Emp_Num CHAR(8) CONSTRAINT Training_EMP_NUM_FK REFERENCES Employee (Emp_Num),
Name VARCHAR2(30),
Date_Acquired DATE DEFAULT SYSDATE,
Comments VARCHAR2(100));


-- Create Client (PK, web_address UNIQUE CONSTRAINTS)
CREATE TABLE Client
(Client_ID CHAR(4) CONSTRAINT Client_CLIENT_ID_PK PRIMARY KEY,
Name VARCHAR2(20) CONSTRAINT Client_NAME_NN NOT NULL,
Street VARCHAR2(20),
City VARCHAR2(20),
State CHAR(2),
Zip_Code CHAR(10),
Industry VARCHAR2(20),
Web_Address VARCHAR2(30) CONSTRAINT Client_WEB_ADDRESS_UK UNIQUE,
Phone VARCHAR2(10),
Contact_LName VARCHAR2(20) CONSTRAINT Client_CONTACT_LNAME_NN NOT NULL,
Contact_FName VARCHAR2(20) CONSTRAINT Client_CONTACT_FNAME_NN NOT NULL);



-- Create Project (PK, start_date default sysdate CONSTRAINTS)
CREATE TABLE Project
(Proj_Number CHAR(4) CONSTRAINT Project_PROJ_NUMBER_PK PRIMARY KEY,
Name VARCHAR2(20),
Start_Date DATE DEFAULT SYSDATE,
Total_Cost NUMBER(9,2),
Dept_Code CHAR(2) CONSTRAINT Project_DEPT_CODE_FK REFERENCES Department (Dept_Code),
Client_ID CHAR(4) CONSTRAINT Project_CLIENT_ID_FK REFERENCES Client (Client_ID),
Code CHAR(4) CONSTRAINT Project_CODE_FK REFERENCES Skill (Code)
CONSTRAINT Project_CODE_NN NOT NULL);



-- Create Assignment ( Date_Ended can't be earlier than Date_Assigned, date_assigned default sysdate)
Create TABLE Assignment
(Assign_Num CHAR(4) CONSTRAINT Assignment_ASSIGN_NUM_PK PRIMARY KEY,
Proj_Number CHAR(4) CONSTRAINT Assignment_PROJ_NUMBER_FK REFERENCES Project (Proj_Number),
Emp_Num CHAR(8) CONSTRAINT Assignment_EMP_NUM_FK REFERENCES Employee (Emp_Num),
Date_Assigned DATE DEFAULT SYSDATE,
Date_Ended DATE,
Hours_Used NUMBER(4,0),
CONSTRAINT Assignment_DATE_ENDED_CK CHECK (Date_Ended > Date_Assigned),
CONSTRAINT Assignment_DATE_ENDED_1_M_CK CHECK (EXTRACT(MONTH FROM Date_Ended) - EXTRACT(MONTH FROM Date_Assigned) <= 1));

