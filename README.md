<!-- 
	https://docs.github.com/en/github/writing-on-github/getting-started-with-writing-and-formatting-on-github/basic-writing-and-formatting-syntax#hiding-content-with-comments 
	https://github.com/adam-p/markdown-here/wiki/Markdown-Cheatsheet#tables
-->
# spring-rest-crud
The concept/idea was built based on https://spring.io/guides/tutorials/rest/

Run the application from command prompt
---------------------------------------
mvnw clean spring-boot:run

Commits and their associated topics 
-----------------------------------
Refer the section below on how to [get a specific commit(https://github.com/premvinodh/spring-rest-crud#how-to-get-certain-commit-from-github-project)].

| Sl.No		| Topic                                       				| Commit Hash           								|
|:---------:|-----------------------------------------------------------|:-----------------------------------------------------:|
| 	1.		| Spring Rest CRUD Operations on Employee					| effce06e231701b987568c4d4b2303404b535c75				|


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