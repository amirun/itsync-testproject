# itsync-testproject
Test project for ITSYNC

---
#Software Dependencies
* Java 17
* Spring Boor 3.1.0
* Swagger for API DDocumentation [Open API 3]
* Flyway for DB versioning
* Docker - service should be running locally for building

---
##Build and Test

Execute any of these commands from the project root.

### Option 1: With Docker
- Step 1:
  >`mvn spring-boot:build-image -Dspring-boot.build-image.imageName=itsync/testproject`
- Step 2:
  >`docker run -d -p 8080:8080 itsync/testproject`

### Option 2: Without Docker
- Step 1:
  >`mvn spring-boot:run`


####URLs:
You can visit below URLs to test the application.
- Swagger-UI : http://localhost:8080/swagger-ui/index.html#/
- Actuator   : http://localhost:8080/actuator

###API Guide:
__POST `/books-api`__

Sample Request:
```json
{
  "name": "Sample Book",
  "author": "Amirun",
  "genre": "Mystery",
  "description": "This is a sample description",
  "volumeCount": 3,
  "type": "EBOOK"
}
```
Sample Response:
```json
{
  "id": 16,
  "name": "Sample Book",
  "author": "Amirun",
  "genre": "Mystery",
  "description": null,
  "volumeCount": 3,
  "createDate": "2023-06-06T19:26:53.280+00:00",
  "type": "EBOOK"
}
```