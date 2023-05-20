
FROM --platform=linux/amd64 maven:3.9-amazoncorretto-17-debian AS build
WORKDIR /build
COPY . .
RUN mvn clean package

FROM --platform=linux/amd64 openjdk:17-alpine AS final
EXPOSE 8080
WORKDIR /app
COPY --from=build /build/target/strichliste-backend.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
