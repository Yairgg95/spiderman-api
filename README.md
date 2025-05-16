# Spiderman API

A Java Spring Boot backend providing a RESTful API for managing characters, featuring JWT authentication, image upload to AWS S3, MySQL persistence, caching, and error handling.

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

1. Clone the repository:
   ```bash
   git clone https://github.com/Yairgg95/spiderman-api.git
   ```
2. Copy the example environment file and configure your environment variables:
   ```bash
   cp .env.example .env
   ```
   Then, open the .env file and update it with your credentials.

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
   http://localhost:8080 (or the configured port in your .env).

## Authentication Endpoints

### Register User
* **URL**: `/api/auth/register`
* **Method**: `POST`
* **Authentication**: None (Public)
* **Request Body**:
```json
{  
  "username": "string",  
  "password": "string"  
}
```
* **Response**: User information without password
```json
{  
  "username": "string"  
}
```

### Login
* **URL**: `/api/auth/login`
* **Method**: `POST`
* **Authentication**: None (Public)
* **Request Body**:
```json
{  
  "username": "string",  
  "password": "string"  
}
```
* **Response**: JWT token
```json
{  
  "token": "string"  
}
```

## Character Endpoints

### Get All Characters
* **URL**: `/api/characters`
* **Method**: `GET`
* **Authentication**: None (Public)
* **Query Parameters**:
   * `orderBy` (optional): Sort order for characters (default: "createdAt", alternative: "name")
* **Response**: List of characters

### Get Character by ID
* **URL**: `/api/characters/{id}`
* **Method**: `GET`
* **Authentication**: None (Public)
* **Path Parameters**:
   * `id`: Character ID
* **Response**: Character details
* **Error Responses**:
   * `404`: Character not found

### Create Character
* **URL**: `/api/characters`
* **Method**: `POST`
* **Authentication**: Required (JWT Token)
* **Content Type**: `multipart/form-data`
* **Request Parts**:
   * `character`: JSON string with character data
   * `image` (optional): Character image file
* **Response**: Created character with ID and image URL
* **Notes**: If no image is provided, a default Spider-Man image will be used

### Update Character
* **URL**: `/api/characters/{id}`
* **Method**: `PATCH`
* **Authentication**: Required (JWT Token)
* **Content Type**: `multipart/form-data`
* **Path Parameters**:
   * `id`: Character ID
* **Request Parts**:
   * `character`: JSON string with character data to update
   * `image` (optional): New character image file
* **Response**: Updated character
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
   