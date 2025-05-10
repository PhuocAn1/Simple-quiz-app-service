# Simple-quiz-app-service
 
 A simple quiz app service build using java and Spring Boot frame work.

## QuizApp Database Configuration

This project uses MySQL as its default database. If you prefer a different database, follow the steps below to update the configuration.

### Steps to Change Database:
1. Navigate to `src/main/resources` and update the `application.properties` file to reflect your chosen database.
2. Go to `src/main/java/com/phuocan/quizapp/DAO` and review the interface files. Ensure that any native SQL queries are compatible with your selected database.
3. Test the application to verify that the new database works correctly.

### Example:
Modify `application.properties` like this:
spring.application.name=quizapp

spring.datasource.url = jdbc:database://localhost:port/database-name   
(Replace 'database' with your actual database type eg. MySQL, PostgreSQL, etc)

spring.datasource.username = your_username  
spring.datasource.password = your_password  
spring.datasource.driver-class-name = your database driver example(com.mysql.cj.jdbc.Driver)  

spring.jpa.database-platform= your database dialect example(org.hibernate.dialect.MySQLDialect)  
spring.jpa.hibernate.ddl-auto=update  
spring.jpa.show-sql=true  

