FROM maven:3.8.7-openjdk-18-slim AS build

COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests

FROM openjdk:18-jdk
COPY --from=build /home/app/target/auth-0.0.1-SNAPSHOT.jar /home/app/build/auth.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","-Dserver.port=8080","/home/app/build/auth.jar"]
