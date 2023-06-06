FROM eclipse-temurin:17-jre-alpine
VOLUME /tmp
COPY target/*.jar app.jar
EXPOSE 8080/tcp
ENTRYPOINT ["java","-jar","/app.jar"]
