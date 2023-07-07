-- skills  category : Technique , Marketing, Project Management
INSERT INTO skill (Code, Name, Category) VALUES ('01', 'JAVA', 'Technique');
INSERT INTO skill (Code, Name, Category) VALUES ('02', 'Advertising', 'Marketing');
INSERT INTO skill (Code, Name, Category) VALUES ('03', 'Writing', 'Project Management');

-- department
INSERT INTO department (Dept_Code, Name, Location, Phone, Manager_ID) VALUES ('01', 'Marketing', '2F', '8008351001', '1001');
INSERT INTO department (Dept_Code, Name, Location, Phone, Manager_ID) VALUES ('02', 'Finance', '3F', '8008351002', '1002');
INSERT INTO department (Dept_Code, Name, Location, Phone, Manager_ID) VALUES ('03', 'Human Resources', '4F', '8008351003', '1003');

-- employee 
INSERT INTO employee (Emp_Num, LName, FName, DOB, Hire_Date, Super_ID, Dept_Code) VALUES ('1001', 'Piveteau', 'Duangkaew', DATE '1963-11-02', DATE '1996-01-12', '', '01');
INSERT INTO employee (Emp_Num, LName, FName, DOB, Hire_Date, Super_ID, Dept_Code) VALUES ('1002', 'Facello', 'Georgi', DATE '1973-09-02', DATE '2006-06-26', '1001', '01');
INSERT INTO employee (Emp_Num, LName, FName, DOB, Hire_Date, Super_ID, Dept_Code) VALUES ('1003', 'Simmel', 'Bezalel', DATE '1974-06-02', DATE '2005-11-21', '1001', '02');
INSERT INTO employee (Emp_Num, LName, FName, DOB, Hire_Date, Super_ID, Dept_Code) VALUES ('1004', 'Bamford', 'Parto', DATE '1979-12-03', DATE '2006-08-28', '1001', '03');
INSERT INTO employee (Emp_Num, LName, FName, DOB, Hire_Date, Super_ID, Dept_Code) VALUES ('1005', 'Facello', 'Georgi', DATE '1984-05-01', DATE '2017-12-01', '1002', '01');
INSERT INTO employee (Emp_Num, LName, FName, DOB, Hire_Date, Super_ID, Dept_Code) VALUES ('1006', 'Koblick', 'Chirstian', DATE '1985-01-21', DATE '2019-06-02', '1003', '02');
INSERT INTO employee (Emp_Num, LName, FName, DOB, Hire_Date, Super_ID, Dept_Code) VALUES ('1007', 'Maliniak', 'Kyoichi', DATE '1983-04-20', DATE '2021-05-01', '1004', '03');
INSERT INTO employee (Emp_Num, LName, FName, DOB, Hire_Date, Super_ID, Dept_Code) VALUES ('1008', 'Preusig', 'Anneke', DATE '1983-09-02', DATE '2021-05-16', '1002', '01');
INSERT INTO employee (Emp_Num, LName, FName, DOB, Hire_Date, Super_ID, Dept_Code) VALUES ('1009', 'Zielinski', 'Tzvetan', DATE '1988-10-12', DATE '2021-05-02', '1003', '02');
INSERT INTO employee (Emp_Num, LName, FName, DOB, Hire_Date, Super_ID, Dept_Code) VALUES ('1010', 'Peac', 'Saniya', DATE '1993-06-01', DATE '2021-05-22', '1004', '03');

-- training
INSERT INTO training (Train_Num, Code, Emp_Num, Name, Date_Acquired, Comments) VALUES ('0001', '01', '1008', 'Computer Skills Training', DATE '2021-06-23', 'Great!'); 
INSERT INTO training (Train_Num, Code, Emp_Num, Name, Date_Acquired, Comments) VALUES ('0002', '02', '1009', 'Marketing Training', DATE '2021-09-11', 'Good enough.');
INSERT INTO training (Train_Num, Code, Emp_Num, Name, Date_Acquired, Comments) VALUES ('0003', '03', '1010', 'Professional Training', DATE '2021-07-02', 'Still need more practice.');
INSERT INTO training (Train_Num, Code, Emp_Num, Name, Date_Acquired, Comments) VALUES ('0004', '03', '1010', 'Professional Training', DATE '2021-10-21', 'Great!');
INSERT INTO training (Train_Num, Code, Emp_Num, Name, Date_Acquired, Comments) VALUES ('0005', '03', '1008', 'Professional Training', DATE '2021-07-11', 'Good enough.');
INSERT INTO training (Train_Num, Code, Emp_Num, Name, Date_Acquired, Comments) VALUES ('0006', '01', '1002', 'Computer Skills Training', DATE '2021-06-23', 'Great!'); 
INSERT INTO training (Train_Num, Code, Emp_Num, Name, Date_Acquired, Comments) VALUES ('0007', '03', '1004', 'Professional Training', DATE '2021-07-01', 'Great!');
INSERT INTO training (Train_Num, Code, Emp_Num, Name, Date_Acquired, Comments) VALUES ('0008', '03', '1010', 'Professional Training', DATE '2021-07-01', 'Great!'); 
INSERT INTO training (Train_Num, Code, Emp_Num, Name, Date_Acquired, Comments) VALUES ('0009', '03', '1009', 'Professional Training', DATE '2021-07-01', 'Great!');

-- client 
INSERT INTO client (Client_ID, Name, Street, City, State, Zip_Code, Industry, Web_Address, Phone, Contact_LName, Contact_FName) VALUES ('0001', 'Cristinel Bouloucos', '114 EAST SAVANNAH', 'ATLANTA', 'GA', '30314', 'Apple', 'www.cristinel.com', '4125531001', 'Bouloucos', 'Cristinel');
INSERT INTO client (Client_ID, Name, Street, City, State, Zip_Code, Industry, Web_Address, Phone, Contact_LName, Contact_FName) VALUES ('0002', 'Kazuhide Peha', '1201 ORANGE AVE', 'SEATTLE', 'WA', '98114', 'Amazon', 'www.kazuhide.com', '4125531002', 'Peha', 'Kazuhide');
INSERT INTO client (Client_ID, Name, Street, City, State, Zip_Code, Industry, Web_Address, Phone, Contact_LName, Contact_FName) VALUES ('0003', 'Lillian Haddadi', '58 TILA CIRCLE', 'CHICAGO', 'IL', '60605', 'FaceBook', 'www.lillian.com', '4125531003', 'Haddadi', 'Lillian');
INSERT INTO client (Client_ID, Name, Street, City, State, Zip_Code, Industry, Web_Address, Phone, Contact_LName, Contact_FName) VALUES ('0004', 'Linde Hartman', '60 BIG SHANDY RD', 'ATLANTA', 'GA', '30022', 'TechCrunch', 'www.linda.edu', '4045531003', 'Hartman', 'Linde');
INSERT INTO client (Client_ID, Name, Street, City, State, Zip_Code, Industry, Web_Address, Phone, Contact_LName, Contact_FName) VALUES ('0005', 'Ken Williams', '625 EDGEHILL PL', 'ALPHARETTA', 'GA', '30022', 'PiggyFoods', NULL, '7705531003', 'Williams', 'Ken');

-- project
--<>ON GOING PROJECT<>--
INSERT INTO project (Proj_Number, Name, Start_Date, Total_Cost, Dept_Code, Client_ID, Code) VALUES ('0001', 'Big Husky', DATE '2021-10-01', NULL, '01', '0001', '01');
INSERT INTO project (Proj_Number, Name, Start_Date, Total_Cost, Dept_Code, Client_ID, Code) VALUES ('0002', 'Tarius', DATE '2021-05-06', '10000', '01', '0002', '02');
INSERT INTO project (Proj_Number, Name, Start_Date, Total_Cost, Dept_Code, Client_ID, Code) VALUES ('0003', 'Allosaurus', DATE '2021-07-01', '5000', '02', '0003', '03');
INSERT INTO project (Proj_Number, Name, Start_Date, Total_Cost, Dept_Code, Client_ID, Code) VALUES ('0004', 'Big Tech Stuff', DATE '2021-08-01', '5000', '02', '0003', '03');
INSERT INTO project (Proj_Number, Name, Start_Date, Total_Cost, Dept_Code, Client_ID, Code) VALUES ('0005', 'Long Term Restore', DATE '2021-01-01', '20000', '02', '0003', '03');


-- assignment 
--<> Currently ongoing project w/ 1 employee<>--
INSERT INTO assignment (Assign_Num, Proj_Number, Emp_Num, Date_Assigned, Date_Ended, Hours_Used) VALUES ('0001', '0001', '1002', DATE '2021-10-01', DATE '2021-10-31', 70);
INSERT INTO assignment (Assign_Num, Proj_Number, Emp_Num, Date_Assigned, Date_Ended, Hours_Used) VALUES ('0002', '0001', '1002', DATE '2021-12-01', NULL, NULL);

--<> Project Completed in 1 month by 4 employee<>--
INSERT INTO assignment (Assign_Num, Proj_Number, Emp_Num, Date_Assigned, Date_Ended, Hours_Used) VALUES ('0003', '0002', '1003', DATE '2021-05-06', DATE '2021-05-31', 165);
INSERT INTO assignment (Assign_Num, Proj_Number, Emp_Num, Date_Assigned, Date_Ended, Hours_Used) VALUES ('0004', '0002', '1007', DATE '2021-05-06', DATE '2021-05-31', 150);
INSERT INTO assignment (Assign_Num, Proj_Number, Emp_Num, Date_Assigned, Date_Ended, Hours_Used) VALUES ('0005', '0002', '1009', DATE '2021-05-06', DATE '2021-05-31', 50);
INSERT INTO assignment (Assign_Num, Proj_Number, Emp_Num, Date_Assigned, Date_Ended, Hours_Used) VALUES ('0006', '0002', '1002', DATE '2021-05-06', DATE '2021-05-31', 50);

--<> Project completed in 2 months by 5 employees<>--
INSERT INTO assignment (Assign_Num, Proj_Number, Emp_Num, Date_Assigned, Date_Ended, Hours_Used) VALUES ('0007', '0003', '1004', DATE '2021-07-01', DATE '2021-07-31', 80);
INSERT INTO assignment (Assign_Num, Proj_Number, Emp_Num, Date_Assigned, Date_Ended, Hours_Used) VALUES ('0008', '0003', '1004', DATE '2021-08-01', DATE '2021-08-31', 80);
INSERT INTO assignment (Assign_Num, Proj_Number, Emp_Num, Date_Assigned, Date_Ended, Hours_Used) VALUES ('0009', '0003', '1005', DATE '2021-07-01', DATE '2021-07-31', 80);
INSERT INTO assignment (Assign_Num, Proj_Number, Emp_Num, Date_Assigned, Date_Ended, Hours_Used) VALUES ('0010', '0003', '1005', DATE '2021-08-01', DATE '2021-08-31', 80);
INSERT INTO assignment (Assign_Num, Proj_Number, Emp_Num, Date_Assigned, Date_Ended, Hours_Used) VALUES ('0011', '0003', '1002', DATE '2021-07-01', DATE '2021-07-31', 50);
INSERT INTO assignment (Assign_Num, Proj_Number, Emp_Num, Date_Assigned, Date_Ended, Hours_Used) VALUES ('0012', '0003', '1003', DATE '2021-07-01', DATE '2021-07-31', 50);

--<> Project completed in 3 months by 1 employee (w/ gap)<>--
INSERT INTO assignment (Assign_Num, Proj_Number, Emp_Num, Date_Assigned, Date_Ended, Hours_Used) VALUES ('0013', '0004', '1006', DATE '2021-08-01', DATE '2021-08-31', 70);
INSERT INTO assignment (Assign_Num, Proj_Number, Emp_Num, Date_Assigned, Date_Ended, Hours_Used) VALUES ('0014', '0004', '1006', DATE '2021-10-01', DATE '2021-10-31', 70);

--<> Project completed in 6 months by 1 employee<>--
INSERT INTO assignment (Assign_Num, Proj_Number, Emp_Num, Date_Assigned, Date_Ended, Hours_Used) VALUES ('0015', '0005', '1009', DATE '2021-01-01', DATE '2021-01-31', 70);
INSERT INTO assignment (Assign_Num, Proj_Number, Emp_Num, Date_Assigned, Date_Ended, Hours_Used) VALUES ('0016', '0005', '1009', DATE '2021-02-01', DATE '2021-02-28', 70);
INSERT INTO assignment (Assign_Num, Proj_Number, Emp_Num, Date_Assigned, Date_Ended, Hours_Used) VALUES ('0017', '0005', '1009', DATE '2021-03-01', DATE '2021-03-31', 70);
INSERT INTO assignment (Assign_Num, Proj_Number, Emp_Num, Date_Assigned, Date_Ended, Hours_Used) VALUES ('0018', '0005', '1009', DATE '2021-04-01', DATE '2021-04-30', 70);
INSERT INTO assignment (Assign_Num, Proj_Number, Emp_Num, Date_Assigned, Date_Ended, Hours_Used) VALUES ('0019', '0005', '1009', DATE '2021-05-01', DATE '2021-05-31', 70);
INSERT INTO assignment (Assign_Num, Proj_Number, Emp_Num, Date_Assigned, Date_Ended, Hours_Used) VALUES ('0020', '0005', '1009', DATE '2021-06-01', DATE '2021-06-30', 70);

