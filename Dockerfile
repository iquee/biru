FROM alpine/git as source
LABEL maintainer="luizcomz@gmail.com"
WORKDIR /app
RUN git clone https://github.com/iquee/spring-geo-mongo.git
# clone a specific branch
#RUN git clone https://github.com/iquee/spring-geo-mongo.git && cd spring-geo-mongo && git checkout ready-to-go

FROM maven:3.5-jdk-8-alpine as build
LABEL maintainer="luizcomz@gmail.com"
WORKDIR /app
COPY --from=source /app/spring-geo-mongo /app 
RUN mvn install -DskipTests

FROM openjdk:8-jdk-alpine
LABEL maintainer="luizcomz@gmail.com"
VOLUME /tmp
EXPOSE 9000
COPY --from=build /app/target/spring-geo-mongo-1.0-0.jar /app/
ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://mongo/spring-geo-mongo", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/spring-geo-mongo-1.0-0.jar"]