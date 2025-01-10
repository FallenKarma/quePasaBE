# Backend Project - Spring Boot Application

This repository contains the backend for quePasa, a private chat messaging application. It is built with **Spring Boot** and runs on **Java 21**. The application handles authentication, authorization, and communication with the frontend through REST APIs.

The backend uses a PostgreSQL to store data, and starting the application automatically runs the required services via **Docker Compose**.

## Features

- **Authentication & Authorization:** Manages JWT-based login and registration.
- **REST APIs:** Seamless communication with the frontend application.
- **Spring Boot Framework:** Fast, secure, and scalable backend.
- **Docker Integration:** Easy deployment with Docker Compose.

---

## Prerequisites

### 1. Install Docker Desktop
To run the application, you'll need Docker installed on your machine. Follow these steps:

1. Download **Docker Desktop** for your operating system:
   - [Docker Desktop for Windows](https://www.docker.com/products/docker-desktop/)
   - [Docker Desktop for macOS](https://www.docker.com/products/docker-desktop/)
   - [Docker Desktop for Linux](https://docs.docker.com/desktop/install/linux-install/)

2. Install Docker Desktop and start it.

3. Verify the installation by running the following command in your terminal:
   ```bash
   docker --version
### 2. Install Java 21
Ensure you have Java 21 installed. You can download it from [Oracle JDK](https://www.oracle.com/java/technologies/downloads/). Verify the installation by running:
   ```bash
            java -version
   ```
## Getting started
Follow these steps to set up and run the application:
1. Clone the repository and change directory to the new folder:
   ```bash
   git clone https://github.com/Analisi-i-Arquitectura-de-Software/projecte-02-back.git
   cd projecte-02-back
2. Build the application using Maven:
   ```bash
   mvn clean install
3. Once the build is successful, run the application:
    ```bash
   mvn spring-boot:run
5. The application will start, the server will be available at: http://localhost:8080
   
### API Endpoints
## Authentication Endpoints
   - POST /auth/register : Register a new user.
   - POST /auth/login : Log in an existing user.
   - POST /auth/refresh : Refresh the access token.
## Message Endpoints
   - GET /api/messages/{chatId}/messages : Get messages list for a given chat.
   - POST /api/messages/{chatId}/messages : Send messages inside a given chat.
## Chat Endpoints
   - GET /api/chats : Get unread messages from all chats.
   - POST /api/chats : Create a new chat.
---

## System Design Diagrams

Below is an overview of the system's design, covering the database model, use case diagram, and C4 model. Each section provides a visual representation of the architecture and functionality.


### **Database Model**
The database model illustrates the structure of the application's data and the relationships between entities. This diagram serves as a blueprint for the database schema, helping to understand how data is organized and accessed.

<div align="center">
  <img src="https://github.com/user-attachments/assets/7de8ddfd-f024-46ed-b1b1-61c826172efc" alt="Database Model">
</div>

### **Use Case Diagram**
The use case diagram outlines the key interactions between users and the system. It highlights the primary functionalities and shows the roles of different actors, making it easier to grasp the overall workflow.

Each use case corresponds to one of the previously described endpoints:  
**Registrarse** → `/auth/register`  
**Iniciar sesión** → `/auth/login`  

**Ver sus chats** → `/api/chats` (method GET)  
**Crear un chat** → `/api/chats` (method POST)  

**Leer los mensajes de un chat** → `/api/messages/{chatId}/messages` (method GET)  
**Enviar mensaje a un chat** → `/api/messages/{chatId}/messages` (method POST)  

<div align="center">
  <img src="https://github.com/user-attachments/assets/1517f37a-d36a-4699-80df-60d0714ce298" alt="Use Case Diagram">
</div>

## **C4 Model**

The C4 model provides a structured way to visualize the system's architecture across multiple levels of abstraction. Below are the context, container, and component diagrams:

### **Context**
The context diagram presents a high-level view of the system and its interactions with external entities (users, services, or other systems). It answers "what does this system do?" and "who interacts with it?".

<div align="center">
  <img src="https://github.com/user-attachments/assets/66828cf8-f3cc-4cb5-bad7-c28cac3de747" alt="Context Diagram">
</div>

### **Containers**
The container diagram dives deeper, showcasing the major components (e.g., applications, databases, and services) and how they communicate. This view helps understand the system's infrastructure and deployment.

<div align="center">
  <img src="https://github.com/user-attachments/assets/2bcb549b-81ff-4222-a02a-dfe9f672722d" alt="Container Diagram">
</div>

### **Components**
The component diagram focuses on the internal structure of a specific container, detailing the key modules and their interactions. It provides insight into how specific functionalities are implemented.

<div align="center">
  <img src="https://github.com/user-attachments/assets/15304270-2572-49a6-aef6-de8ed40833fb" alt="Component Diagram">
</div>

These diagrams collectively provide a comprehensive view of the system's design, aiding both development and documentation efforts.


   
   
