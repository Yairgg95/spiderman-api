-- Create the database
CREATE DATABASE IF NOT EXISTS spiderverse_db
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
USE spiderverse_db;

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- Create spiderman_characters table
CREATE TABLE IF NOT EXISTS spiderman_characters (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    identifier VARCHAR(255) NOT NULL UNIQUE,
    image_url VARCHAR(255),
    role ENUM('hero', 'villain', 'neutral') NOT NULL,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
);

-- Add indexes for better performance
CREATE INDEX idx_character_name ON spiderman_characters(name);
CREATE INDEX idx_character_created_at ON spiderman_characters(created_at);
