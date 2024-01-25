1. Technical Exercise.

2. Project Description
   This API create and manage orders.
   Items can be ordered, and orders are automatically fulfilled as soon as the item stock allows it.
   So, with this Api we can create, read, update and delete all the object that we can create.
   
3. Prerequisites.
   1. JDK 8
   2. Spring Boot
   3. Spring JPA
   4. Jave EE
   5. Hibernate
   6. MyQSL
   7. IntelliJ / Eclipse
   8. Postman

4. How to Install and Run the Project
   To install, run and see functionalities working in this project is necessary the following:
   1. Git clone https://github.com/MarioN2121/ordersystem.git
   2. File -> import existing maven project
   3. mvn clean install to build the project
   4. Start the application as a Spring Boot Application
   5. Postman(GET,POST,PUT,DELETE)
      5.1. List, Create, Update & Delete Object.

5. Sample JSON on Postman with POST method:
      1. user
         {
            "name": "user.test@gmail.com",
            "email": "user test"
         }
   
      2. item
         {
            "name": "Laptop Asus"
         }
      3. ...

OBS: localhost:8084/technical-exercise/stockmovements/listartodos
The url above is to run the application locally.


Author: Mário Novais