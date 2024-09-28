# Blogosphere
A robust backend application with - Admin-driven post creation and management, User comment system, Role-based access control and authentication via Spring Security and JSON Web Token (JWT) for secure authentication

#Spring Boot CRUD Backend Application

**Overview**

A robust CRUD backend application built with Spring Boot, featuring secure user authentication, role-based access control, and JSON Web Token (JWT) authentication.


**Key Features**

Authentication and Authorization

- User signup and login functionality
- JWT access token generation for secure authentication
- Role-based access control using Spring Security

**Post Management**

- Admin-driven post creation and management
- User commenting system
- GET, PUT, MODIFY, DELETE features for posts and comments

**Security**

- Spring Security for authentication and authorization
- JSON Web Token (JWT) for secure access

**Technologies Used**

- Spring Boot
- Spring Security
- JSON Web Token (JWT)
- RESTful APIs


**API Endpoints**

User Endpoints

- POST /api/auth/signup - Create new user
- POST /api/auth/login - Login existing user

Post Endpoints

- POST /api/posts - Create new post (Admin only)
- GET /api/posts - Get all posts
- GET /api/posts/{id} - Get post by ID
- PUT /api/posts/{id} - Update post (Admin only)
- DELETE /api/posts/{id} - Delete post (Admin only)

Comment Endpoints

- POST /api/posts/{postId}/comments - Create new comment on a particular post
- GET /api/posts/{postId}/comments - Get all comments on a particular post
- GET /api/posts/{postId}/comments/{id} - Get comment by ID on a particular post


**Setup and Installation**

1. Clone repository: git clone
2. Install dependencies: mvn clean install
3. Run application: mvn spring-boot:run 
