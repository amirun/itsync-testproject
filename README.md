# itsync-testproject
Test project for ITSYNC

---
# Software Dependencies
* Java 17
* Spring Boot 3.1.0
* Swagger for API Documentation [Open API 3]
* Flyway for DB versioning
* Docker - service should be running locally for build

---
# Build and Test

Execute any of these commands from the project root.

### Option 1: With Docker
- Step 1:
  >`mvn spring-boot:build-image -Dspring-boot.build-image.imageName=itsync/testproject`
- Step 2:
  >`docker run -d -p 8080:8080 itsync/testproject`

### Option 2: Without Docker
- Step 1:
  >`mvn spring-boot:run`

---
### *NOTE*: A sample data set of 15 books is pre-populated for easier testing.

---

# URLs:
You can visit below URLs to test the application.
- Swagger-UI : http://localhost:8080/swagger-ui/index.html#/
- Actuator   : http://localhost:8080/actuator

# API Guide:
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
  "description": "This is a sample description",
  "volumeCount": 3,
  "createDate": "2023-06-06T19:34:58.766+00:00",
  "type": "EBOOK"
}
```

__PUT `/books-api/update/16`__

Updating author, book type, volume count.
Sample Request:

```json
{
  "name": "Sample Book",
  "author": "Amirun Bisoyi",
  "genre": "Mystery",
  "description": "This is an updated description",
  "volumeCount": 2,
  "type": "HARDCOVER"
}
```
Sample Response:
```json
{
  "id": 16,
  "name": "Sample Book",
  "author": "Amirun Bisoyi",
  "genre": "Mystery",
  "description": "This is an updated description",
  "volumeCount": 2,
  "createDate": "2023-06-06T19:34:58.766+00:00",
  "type": "HARDCOVER"
}
```
__DELETE `/books-api/delete/16`__

Deleting previously created book.

Sample Response:
```json
true
```

__POST `/books-api/getBooks`__

- Filter based on Type = EBOOKS :

  Request:
  ```json
  { "type": "EBOOK" }
  ```
  Response:
  ```json
  [
    {
      "id": 1,
      "name": "Gilead",
      "author": "Marilynne Robinson",
      "genre": "Fiction",
      "description": "A NOVEL...like.",
      "volumeCount": 1,
      "createDate": "2023-06-06T19:33:28.413+00:00",
      "type": "EBOOK"
    },
    .
    .
    {
      "id": 12,
      ...
      "type": "EBOOK"
    },
    .
    .  
    {
      "id": 15,
      ...
      "type": "EBOOK"
    }
  ]
  ```
  
- Filter books with more than 4 volumes/series : 

  Request:
  ```json
  { "minVolume": 4 }
  ```
  Response:
  ```json
  [
    {
      "id": 3,
      "name": "Hamlet",
      "author": "William Shakespeare",
      "genre": "Drama",
      "description": "Hamlet, Prince of Denmark is faced by a ghost bearing a grim message of murder and revenge, driving the prince to the edge of madness by his struggle to understand the situation and to do his duty.",
      "volumeCount": 5,
      "createDate": "2023-06-06T19:33:28.413+00:00",
      "type": "EBOOK"
    }
  ]
  ```

- Books with particular author but not specific genre.

  Request:
  ```json
  { "author": "Tobias Wolff" }
  ```
  Response:
  ```json
  [
    {
      "id": 9,
      "name": "In Pharaohs Army",
      "author": "Tobias Wolff",
      "genre": "Biography & Autobiography",
      "description": "The ... life",
      "volumeCount": 2,
      "createDate": "2023-06-06T19:33:28.413+00:00",
      "type": "HARDCOVER"
    },
    {
      "id": 10,
      "name": "Old School",
      "author": "Tobias Wolff",
      "genre": "Authors",
      "description": "Its ... timeless.",
      "volumeCount": 3,
      "createDate": "2023-06-06T19:33:28.413+00:00",
      "type": "HARDCOVER"
    },
    {
      "id": 11,
      "name": "This boys life",
      "author": "Tobias Wolff",
      "genre": "Biography & Autobiography",
      "description": "The author ... up.",
      "volumeCount": 3,
      "createDate": "2023-06-06T19:33:28.413+00:00",
      "type": "PAPERBACK"
    }
  ]
  ```

- Filter books with more than 4 volumes/series of William Shakespeare and HARDCOVER :

  Request:
  ```json
  { 
    "author": "William Shakespeare",
    "minVolume": 2,
    "type": "HARDCOVER"
  }
  ```
  Response:
  ```json
  [
    {
      "id": 5,
      "name": "Macbeth",
      "author": "William Shakespeare",
      "genre": "Juvenile Nonfiction",
      "description": "Critical and historical notes accompany Shakespeare's drama of murder, political ambition, and the persistence of guilt.",
      "volumeCount": 3,
      "createDate": "2023-06-06T19:33:28.413+00:00",
      "type": "HARDCOVER"
    }
  ]
  ```
- Get all, no filters applied :

  Request:
  ```json
  { }
  ```
  Response:
  ```json
  [
    {
      "id": 1,
      "name": "Gilead",
      "author": "Marilynne Robinson",
      "genre": "Fiction",
      "description": "A ... readers alike.",
      "volumeCount": 1,
      "createDate": "2023-06-06T19:33:28.413+00:00",
      "type": "EBOOK"
    },
    {
      "id": 2,
      "name": "The Sonnets",
      "author": "William Shakespeare",
      "genre": "Drama",
      "description": "Pres ... etime.",
      "volumeCount": 2,
      "createDate": "2023-06-06T19:33:28.413+00:00",
      "type": "EBOOK"
    },
    ...
    {
      "id": 15,
      "name": "Riddle-master",
      "author": "Patricia A. McKillip",
      "genre": "Fiction",
      "description": "A col ... iginal.",
      "volumeCount": 3,
      "createDate": "2023-06-06T19:33:28.413+00:00",
      "type": "EBOOK"
    }
  ]
  ```
