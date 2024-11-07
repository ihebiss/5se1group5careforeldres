FROM openjdk:17-alpine
WORKDIR /5SE1G5DEVOPSBACKEND
RUN ls -la
COPY target/carecareforEldres-1.0.0.jar /usr/local/lib/5SE1G5DEVOPSBACKEND.jar
EXPOSE 8080
USER root
ENTRYPOINT ["java", "-jar", "/usr/local/lib/5SE1G5DEVOPSBACKEND.jar"]

