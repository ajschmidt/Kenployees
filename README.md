# Kenployees
An example of a RESTful service packaged in a Docker container

### User Guide ###

1. Clone the repo or download the zip file to a workstation with Docker installed on it.
2. Navigate to the root of the project
3. Run: ```docker-compose up --build -d``` or use the **Alternative Build**
4. With a web browser got to http://localhost:8080/swagger-ui.html#/
5. Use the swagger ui to learn the API of the employee service
6. With a web browser or REST client go to http://localhost:8080/v1/employees/
7. At the login prompt user = 'admin', password = 'secret'
8. Use the API as described

## Alternative Build ##
1. Install Java 8
2. Install Maven 2
3. navigate to the `kenmployee` subdirectory ```cd kenployee```
4. ```java -jar target/kenployee-1.0-SNAPSHOT.jar```
5.follow steps 4-8 under **User Guide** 

