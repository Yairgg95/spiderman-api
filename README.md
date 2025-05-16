# Spiderman API

A Java Spring Boot backend providing a RESTful API for managing characters, featuring JWT authentication, image upload
to AWS S3, MySQL persistence, caching, and error handling.

## 🚀 Features

- **Character CRUD**: Full create, read, update, delete operations.
- **JWT Authentication**: Secure endpoints with JSON Web Tokens.
- **AWS S3 Image Upload**: Upload and manage character images.
- **MySQL Persistence**: Store data in a MySQL database.
- **Caching**: Optimize frequent queries with caching.
- **Error Handling**: Consistent and controlled error responses.
- **MVC Pattern**: Organized and scalable architecture.
- **JSON Input/Output**: Consume and expose JSON data.

## 🛠️ Technologies

- **Language:** Java 17+
- **Framework:** Spring Boot
- **Build Tool:** Maven
- **Database:** MySQL
- **Authentication:** JWT (JSON Web Tokens)
- **File Storage:** AWS S3
- **Caching:** Custom implementation

## 📦 Requirements

- Java 17 or higher
- Maven
- Docker
- AWS Account (for S3)
- MySQL instance

## ⚙️ Setup

### Run with Docker

1. Clone the repository:
   ```bash
   git clone https://github.com/Yairgg95/spiderman-api.git
   ```
2. Copy the example environment file and configure your environment variables:
   ```bash
   cp .env.example .env
   ```
   Then, open the .env file and update it with your credentials.
*  **Notes**: Use the following values in your .env file, as they are required by the Docker Compose configuration:
   ```env
   DB_HOST=mysql
   DB_PORT=3306
   ```

3. Build the project (skip tests):
   ```bash
   mvn clean package -DskipTests
   ```
4. Start Docker containers:
   ```bash
   docker-compose up --build
   ```
   This will build and run both containers (backend and database).


5. Access the API:
   Once the containers are running, the API will be available at:
   http://localhost:8080

### Run Locally Without Docker

If you prefer to run the backend locally without Docker, follow these steps:
#### For Unix-based systems (Linux/macOS)

1. Clone the repository:
   ```bash
   git clone https://github.com/Yairgg95/spiderman-api.git
   ```
2. Copy the example environment file and configure your environment variables:
   ```bash
   cp .env.example .env
   ```
   Then, open the .env file and update it with your credentials.

3. Export enviroment variables:
   ```bash
   export $(grep -v '^#' .env | xargs)
   ```

4. Build the project (skip tests):
   ```bash
   mvn clean package -DskipTests
   ```

5. Run the application:
   ```bash
   java -jar target/api-0.0.1-SNAPSHOT.jar
   ```
6. Access the API:
   Once the containers are running, the API will be available at:
   http://localhost:8080

#### Setup Instructions for Windows

1. Clone the repository:
   ```bash
   git clone https://github.com/Yairgg95/spiderman-api.git
   ```

2. Copy the example environment file and configure your environment variables:
    * Manually rename `.env.example` to `.env`
    * Open `.env` and update it with your credentials.

3. Export environment variables:
    * **Using Command Prompt (CMD)**:
      ```cmd
      for /f "usebackq tokens=1,2 delims==" %i in (.env) do @set %i=%j
      ```
    * **Using PowerShell**:
      ```powershell
      Get-Content .env | ForEach-Object { if ($_ -match '^\s*([^#][^=]+)=(.*)$') { $name = $matches[1]; $value = $matches[2]; [System.Environment]::SetEnvironmentVariable($name, $value, "Process") } }
      ```

4. Build the project (skip tests):
   ```bash
   mvn clean package -DskipTests
   ```

5. Run the application:
   ```bash
   java -jar target/api-0.0.1-SNAPSHOT.jar
   ```

6. Access the API:
   Once the containers are running, the API will be available at:
   http://localhost:8080


## Authentication Endpoints

### Register User

* **URL**: `/api/auth/register`
* **Method**: `POST`
* **Authentication**: None (Public)
* **Request Body**:

```json
{
  "username": "peter_parker",
  "password": "spider123"
}
```

* **Response**: User information without password

```json
{
  "username": "peter_parker"
}
```

### Login

* **URL**: `/api/auth/login`
* **Method**: `POST`
* **Authentication**: None (Public)
* **Request Body**:

```json
{
  "username": "peter_parker",
  "password": "spider123"
}
```

* **Response**: JWT token

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZXRlcl9wYXJrZXIiLCJpYXQiOjE2MjE1MjM2OTAsImV4cCI6MTYyMTYxMDA5MH0.example_token_signature"
}
```

## Character Endpoints

### Get All Characters

* **URL**: `/api/characters`
* **Method**: `GET`
* **Authentication**: None (Public)
* **Query Parameters**:
    * `orderBy` (optional): Sort order for characters (default: "createdAt", alternative: "name")
* **Response**:

```JSON

[
  {
    "id": 1,
    "name": "Peter Parker",
    "identifier": "spider-man-616",
    "imageURL": "https://spiderverse-images.s3.amazonaws.com/spider-man-616_profile.jpg",
    "role": "Main Hero",
    "description": "The original Spider-Man from universe 616",
    "createdAt": "2025-05-15T10:30:00",
    "updatedAt": "2025-05-15T10:30:00"
  },
  {
    "id": 2,
    "name": "Miles Morales",
    "identifier": "spider-man-1610",
    "imageURL": "https://spiderverse-images.s3.amazonaws.com/spider-man-1610_profile.jpg",
    "role": "Alternative Hero",
    "description": "Spider-Man from the Ultimate universe",
    "createdAt": "2025-05-15T11:15:00",
    "updatedAt": "2025-05-15T11:15:00"
  }
]


```

### Get Character by ID

* **URL**: `/api/characters/{id}`
* **Method**: `GET`
* **Authentication**: None (Public)
* **Path Parameters**:
    * `id`: Character ID
* **Response**:

```JSON
  {
  "id": 1,
  "name": "Peter Parker",
  "identifier": "spider-man-616",
  "imageURL": "https://spiderverse-images.s3.amazonaws.com/spider-man-616_profile.jpg",
  "role": "Main Hero",
  "description": "The original Spider-Man from universe 616",
  "createdAt": "2025-05-15T10:30:00",
  "updatedAt": "2025-05-15T10:30:00"
  }
```

* **Error Responses**:
    * `404`: Character not found

### Create Character

* **URL**: `/api/characters`
* **Method**: `POST`
* **Authentication**: Required (JWT Token)
* **Content Type**: `multipart/form-data`
* **Request Parts**:
    * `character`:
   ```JSON
   {  
  "name": "Gwen Stacy",  
  "identifier": "spider-gwen-65",  
  "role": "Alternative Hero",  
  "description": "Spider-Woman from universe 65"  
  }
  ```
    * `image` (optional): Character image file
* **Response**:
  ```JSON
  {  
  "id": 3,  
  "name": "Gwen Stacy",  
  "identifier": "spider-gwen-65",  
  "imageURL": "https://spiderverse-images.s3.amazonaws.com/spider-gwen-65_profile.jpg",  
  "role": "Alternative Hero",  
  "description": "Spider-Woman from universe 65",  
  "createdAt": "2025-05-16T09:45:00",  
  "updatedAt": null  
  }
   ```
* **Notes**: If no image is provided, a default Spider-Man image will be used

### Update Character

* **URL**: `/api/characters/{id}`
* **Method**: `PATCH`
* **Authentication**: Required (JWT Token)
* **Content Type**: `multipart/form-data`
* **Path Parameters**:
    * `id`: Character ID
* **Request Parts**:
    * `character`:
   ```JSON
  {  
  "name": "Gwen Stacy",  
  "role": "Ghost-Spider",  
  "description": "Also known as Ghost-Spider in universe 65."  
  } 
  ```
    * `image` (optional): New character image file
* **Response**:
  ```JSON
  {  
  "id": 3,  
  "name": "Gwen Stacy",  
  "identifier": "spider-gwen-65",  
  "imageURL": "https://spiderverse-images.s3.amazonaws.com/spider-gwen-65_updated.jpg",  
  "role": "Ghost-Spider",  
  "description": "Also known as Ghost-Spider in universe 65.",  
  "createdAt": "2025-05-16T09:45:00",  
  "updatedAt": "2025-05-16T10:20:00"  
  } 
  ```
* **Error Responses**:
    * `404`: Character not found

### Delete Character

* **URL**: `/api/characters/{id}`
* **Method**: `DELETE`
* **Authentication**: Required (JWT Token)
* **Path Parameters**:
    * `id`: Character ID
* **Response**: `204 No Content`
* **Error Responses**:
    * `404`: Character not found
   