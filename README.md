# Authentication Service 

## Tech
- Java 18
- Spring Boot 3 (With Spring Security)
- Maven
- JWT
- PostgresSQL
- Docker*
  - Docker Compose*
- Swagger UI
- CSRF Protection

> '*' is require installed in environment

## API Design

|  API Name| API URL  | Permission | HTTP Method |
|---|---|---|---|
|  Signup | http://localhost:8008/api/auth/signup | All | POST |
|  Signin | http://localhost:8008/api/auth/signin | All | POST |
| Get All User | http://localhost:8008/api/user/all | Authentication Role ADMIN | GET |
| Get User | http://localhost:8008/api/user | Authentication All Roles | GET |
| Get Moderator User | http://localhost:8008/api/user/mod | Authentication Role MODERATOR | GET |
| Get CSRF Token | http://localhost:8008/api/csrf/firsttime | All | GET |

## Usage
#### Step 1 - Run web service

After clone project go to folder and use command `docker-compose up`

#### Step 2 - Test API via browser

Test the service through the URL : http://localhost:8008/api/swagger-ui/index.html

## More Step Usage

Blog Authentication & JWT : [janescience.com/auth-springsecurity-jwt](https://janescience.com/blog/auth-springsecurity-jwt)

Blog Run Spring Boot with Docker Compose : [janescience.com/springboot-docker](https://janescience.com/blog/springboot-docker)

Blog Add CSRF Protection in Spring Security : [janescience.com/csrf-spring-security](https://janescience.com/blog/csrf-spring-security)


