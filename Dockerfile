FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY ./backend/target/*.jar app.jar
EXPOSE 8080
# ENTRYPOINT ["java","-jar","/app.jar"]