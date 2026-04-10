 Club Service - English School Platform
 Overview

The Club Service is a microservice built with Spring Boot for managing student clubs within the English School Platform.
It provides functionalities for creating clubs, managing members, organizing activities, and tracking engagement.

 Features
 Club Management
Create, update, and delete clubs
Manage club information (name, description, category)
Assign club leaders or administrators
Membership Management
Join / leave a club
Manage members list
Role management (Member, Admin, President)
Member engagement tracking
Activities & Events Integration
Link clubs with events
Track participation of club members in events
Installation & Setup
Clone the repository
git clone https://github.com/<your-username>/club_service.git
 Navigate to the project
cd club_service
 Configure the database

Update application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/your_db
spring.datasource.username=root
spring.datasource.password=
 Run the application
mvn spring-boot:run
