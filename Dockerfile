#FROM eclipse-temurin:17.0.5_8-jre-focal as builder
#
#WORKDIR extracted
#
#ADD ./build/libs/task-list-0.0.1-SNAPSHOT.jar app.jar
#RUN java -Djarmode=layertools -jar app.jar extract
#
#FROM eclipse-temurin:17.0.5_8-jre-focal
#WORKDIR application
#COPY --from=builder extracted/dependencies/ ./
#COPY --from=builder extracted/spring-boot-loader/ ./
#COPY --from=builder extracted/snapshot-dependencies/ ./
#COPY --from=builder extracted/application/ ./
#
#EXPOSE 8080
#
#ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]

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

FROM gradle:8.5.0-jdk17 AS build
WORKDIR /home/gradle/project
COPY . .
RUN chmod +x ./gradlew   # Добавляем эту строку для предоставления разрешений на выполнение
RUN ./gradlew build

FROM openjdk:17-jdk-slim
COPY --from=build /home/gradle/project/build/libs/*.jar application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]