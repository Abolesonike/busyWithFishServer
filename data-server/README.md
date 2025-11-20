# User Registration API

## Overview
This API provides endpoints for user registration with email verification using Redis for storing verification codes.

## Configuration
To use the email functionality, you need to configure your email settings in `application.yml`:

```yaml
spring:
  mail:
    host: smtp.gmail.com
    port: 587
    username: your_email@gmail.com
    password: your_app_password
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
  redis:
    host: localhost
    port: 6379
    password: 
    lettuce:
      pool:
        max-active: 8
        max-idle: 8
        min-idle: 0
```

For Gmail, you'll need to generate an App Password rather than using your regular password.

## Endpoints

### Send Verification Code
```
POST /api/auth/send-code
```

**Request Body:**
```json
{
  "username": "john_doe",
  "password": "secure_password",
  "email": "john@example.com",
  "phone": "+1234567890"
}
```

**Response:**
```
Verification code sent to your email
```

### Register User
```
POST /api/auth/register
```

**Request Body:**
```json
{
  "user": {
    "username": "john_doe",
    "password": "secure_password",
    "email": "john@example.com",
    "phone": "+1234567890"
  },
  "code": "123456"
}
```

**Response:**
```
User registered successfully
```

### Redis Test Endpoints
```
POST /redis/string?key=mykey&value=myvalue
GET /redis/string?key=mykey
POST /redis/string/expire?key=mykey&value=myvalue&time=60
POST /redis/hash?key=myhash&field=myfield&value=myvalue
GET /redis/hash?key=myhash&field=myfield
DELETE /redis/key?key=mykey
```

## How It Works
1. Client sends a request to `/api/auth/send-code` with user registration information
2. Server generates a 6-digit verification code and stores it in Redis with a 5-minute expiration
3. Server sends the code to the user's email
4. Client receives the code in their email
5. Client sends a request to `/api/auth/register` with the user information and verification code
6. Server verifies the code by checking Redis and, if valid, creates a new user with:
   - Auto-generated ID
   - Encrypted password using BCrypt
7. If successful, the user is registered in the system

## Security Notes
- Verification codes expire after 5 minutes
- Passwords are encrypted using BCrypt before storage
- Verification codes are one-time use only
- Redis is used for efficient storage and automatic expiration of verification codes