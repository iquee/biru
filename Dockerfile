FROM alpine/git as source
LABEL maintainer="luizcomz@gmail.com"
WORKDIR /app
#RUN git clone https://github.com/iquee/biru.git
# clone a specific branch
RUN git clone https://github.com/iquee/biru.git && cd biru && git checkout ready-to-go

FROM maven:3.5-jdk-8-alpine as build
LABEL maintainer="luizcomz@gmail.com"
WORKDIR /app
COPY --from=source /app/biru /app 
RUN mvn install -DskipTests

FROM openjdk:8-jdk-alpine
LABEL maintainer="luizcomz@gmail.com"
VOLUME /tmp
EXPOSE 9000
COPY --from=build /app/target/biru-1.0-0.jar /app/
ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://mongo/biru", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/biru-1.0-0.jar"]