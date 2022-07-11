Spring Guides
-------------
https://spring.io/guides/tutorials/rest/


Run the application from command prompt
---------------------------------------
mvnw clean spring-boot:run



Rest API Calls
--------------
GET --> http://localhost:8080/employees

GET --> http://localhost:8080/employees/1

POST --> http://localhost:8080/employees
	Body: raw --> JSON (application/json)
	
	 {
        "id": 3,
        "name": "Gandalf",
        "role": "wizard"
 	}
 	
PUT --> http://localhost:8080/employees/3
	Body: raw --> JSON (application/json)
	
	 {
        "id": 3,
        "name": "Gandalf",
        "role": "old wizard"
 	} 	
 	
DELETE --> http://localhost:8080/employees/3 	