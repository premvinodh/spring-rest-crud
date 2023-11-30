<!-- 
	https://docs.github.com/en/github/writing-on-github/getting-started-with-writing-and-formatting-on-github/basic-writing-and-formatting-syntax#hiding-content-with-comments 
	https://github.com/adam-p/markdown-here/wiki/Markdown-Cheatsheet#tables
-->
# spring-rest-crud with tdd
The concept/idea was built based on https://spring.io/guides/tutorials/rest/

Run the application from command prompt
---------------------------------------
mvnw clean spring-boot:run

Commits and their associated topics 
-----------------------------------
Refer the section below on how to [get a specific commit(https://github.com/premvinodh/spring-rest-crud#how-to-get-certain-commit-from-github-project)].

| Sl.No		| Topic                                       																					| Commit Hash           						|
|:---------:|-------------------------------------------------------------------------------------------------------------------------------|:---------------------------------------------:|
| 	1.		| Spring Rest CRUD Operations on Employee																						| effce06e231701b987568c4d4b2303404b535c75		|
| 	2.		| Autowired beans and added EmployeeService																						| efb1d27967232e5f81166bb8d0dac323ce1c0596		|
| 	3.		| Added test cases for EmployeeController to EmployeeControllerTest																| bfc6ce1d10cea15e6b3157356a77550bcd5354af		|
| 	4.		| Added test cases for EmployeeService to EmployeeServiceTest																	| e478d6669b2f08aeb5b938aceeeb49ad899bc6dd		|
| 	5.		| Added test cases for repo methods used in EmployeeService to EmployeeRepositoryTest											| 9a74dc757c290901364678cf3552161433b58768		|
| 	6.		| Added swagger to the rest application																							| 4fa761154de6af998920da203f8eedf27d5795ea		|
| 	7.		| Added spring actuator 																										| 24b1930c9da383f1a8c9a94e37952d973cb61033		|
| 	8.		| Added h2 db properties to applicatoin.properties and updated LoadDatabase with comments on how to load data into db better 	| 1b3c69efd0602441bd27562af98aebcc83bc5a91		|
| 	9.		| Changed to derby from h2 db and using schema.sql and data.sql to load the database 											| 581079353144c8e562f2e944a904568fd056a1dd		|

### How to get certain commit from GitHub project
------------------------------------------------
1. First, clone the repository using git as shown below
git clone https://github.com/premvinodh/spring-rest-crud.git
That downloads the complete history of the repository, so you can switch to any version. Next, change into the newly cloned repository:

2. cd <complete_path_on_your_system>\spring-rest-crud

3. Use git checkout <COMMIT> to change to the right commit of a particular topic
git checkout effce06e231701b987568c4d4b2303404b535c75

Rest API Calls
--------------
GET --> http://localhost:8080/api/employees

GET --> http://localhost:8080/api/employees/1

POST --> http://localhost:8080/api/employees
	Body: raw --> JSON (application/json)
	
	 {
        "id": 3,
        "name": "Gandalf",
        "role": "wizard"
 	}
 	
PUT --> http://localhost:8080/api/employees/3
	Body: raw --> JSON (application/json)
	
	 {
        "id": 3,
        "name": "Gandalf",
        "role": "old wizard"
 	} 	
 	
DELETE --> http://localhost:8080/api/employees/3 	


GET with Request body (to send huge data) --> http://localhost:8080/api/employees/employee-as-payload-in-get
	Body: raw --> JSON (application/json)
	
	 {
        "id": 3,
        "name": "Gandalf",
        "role": "wizard"
 	}

Swagger
-------
Access the swagger ui @ http://localhost:8080/swagger-ui.html


Actuator
--------
Access the actuator health endpoint @ http://localhost:8080/actuator/health

Derby DB
--------
DERBY_HOME: D:\MyApps\DB\Derby\10.15.2.0\db-derby-10.15.2.0-bin

1. Start the Derby Server in first command prompt using the below command at the location %DERBY_HOME%\bin
%DERBY_HOME%\bin>startNetworkServer.bat

2. Start the Derby client in second command prompt using the below commands at the location %DERBY_HOME%\bin
Note: The database name used is EmployeeDatabase
%DERBY_HOME%\bin>ij.bat
connect 'jdbc:derby://localhost:1527/EmployeeDatabase;create=true';  

CREATE TABLE Employee (
   ID INT NOT NULL GENERATED ALWAYS AS IDENTITY,
   NAME VARCHAR(255),
   ROLE VARCHAR(255),
   PRIMARY KEY (Id)
);

3. When the application starts the second time - it fails as it tries to execute the schema.sql
In the second command prompt where the Derby client is running execute the following commands - ensure the employee table is deleted and start the application again.
show tables;
DROP TABLE derbyuser.employee;
show tables;
