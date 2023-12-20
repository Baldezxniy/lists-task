FROM eclipse-temurin:17.0.5_8-jre-focal as builder

WORKDIR extracted

ADD ./build/libs/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM eclipse-temurin:17.0.5_8-jre-focal
WORKDIR application
COPY --from=builder extracted/dependencies/ ./
COPY --from=builder extracted/spring-boot-loader/ ./
COPY --from=builder extracted/snapshot-dependencies/ ./
COPY --from=builder extracted/application/ ./

EXPOSE 8080

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]

#FROM openjdk:17-jdk
#
#RUN mkdir /app
#
#COPY app.jar /app/app.jar
#
#WORKDIR /app
#
#EXPOSE 8080
#
#ENTRYPOINT ["java", "-jar", "app.jar"]
#
#FROM gradle:8.5-jdk17 AS build
#WORKDIR /
#COPY /src /src
#COPY build.gradle settings.gradle /
#RUN gradle clean build

#FROM eclipse-temurin:17.0.5_8-jre-focal
#WORKDIR /app
#COPY --from=build /path/to/your/project/build/libs/*.jar application.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "application.jar"]


#FROM openjdk:8-jdk
#
#RUN mkdir /app
#
#COPY app.jar /app/app.jar
#
#WORKDIR /app
#
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "app.jar"]