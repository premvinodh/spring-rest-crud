-- Start the Derby Server in first cmd using the below command at the location
-- D:\MyApps\DB\Derby\10.15.2.0\db-derby-10.15.2.0-bin\bin>startNetworkServer.bat

-- Start the client in second cmd using the below commands 
-- D:\MyApps\DB\Derby\10.15.2.0\db-derby-10.15.2.0-bin\bin>ij.bat
--	connect ‘jdbc:derby://localhost:1527/db;create=true’;  

CREATE TABLE Employee (
   ID INT NOT NULL GENERATED ALWAYS AS IDENTITY,
   NAME VARCHAR(255),
   ROLE VARCHAR(255),
   PRIMARY KEY (Id)
);

--show tables;
--DROP TABLE derbyuser.employee;
-- show tables;