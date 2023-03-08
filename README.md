# Authentication Service 

## Tech
- Java 18*
- Spring Boot 3 (With Spring Security)
- Maven*
- JWT
- PostgresSQL
- Docker* (With docker-compose)
- Swagger UI

> * is require installed in environment

## API Design

|  API Name| API URL  | Permission | HTTP Method |
|---|---|---|---|
|  Signup | http://localhost:8008/api/auth/signup | All | POST |
|  Signin | http://localhost:8008/api/auth/signin | All | POST |
| Get All User | http://localhost:8008/api/user/all | Authentication Role ADMIN | GET |
| Get User | http://localhost:8008/api/user | Authentication All Roles | GET |
| Get Moderator User | http://localhost:8008/api/user/mod | Authentication Role MODERATOR | GET |

## Usage
#### Step 1 - Run web service

After clone project go to folder and use command `./build.sh`

#### Step 2 - Test API via browser

Test the service through the URL : http://localhost:8008/api/swagger-ui.html

![](images\swagger-ui.png)

## More Step Usage

Blog > [janescience.com](https://janescience.com/blog/auth-springboot-jwt)

