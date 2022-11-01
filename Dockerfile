FROM gradle:7.5.1-jdk17-alpine as builder
WORKDIR /home/gradle/source
COPY . .
RUN gradle assemble

FROM eclipse-temurin:17-jre-alpine
EXPOSE 8080
COPY --from=builder /home/gradle/source/build/libs/*.jar /app/
ENTRYPOINT ["java", "-jar", "/app/MealSquare-0.0.1-SNAPSHOT.jar"]