FROM gradle:8.5-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle weight-service:build -x test --no-daemon

FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/weight-service/build/libs/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"] 