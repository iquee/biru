[![Build Status](https://travis-ci.org/iquee/biru.svg?branch=master)](https://travis-ci.org/iquee/biru)

# Instructions

Some instructions to build, run and test this microservice

Clone the repository, access the folder and run this commands in root directory to:

###### run the application
`mvn spring-boot:run`
> Access [Index](http://localhost:9000) page

> To see the API Documentation and Try: [Swagger Rest API's](http://localhost:9000/swagger-ui.html)

* First build/run may take some minutes to download all dependencies
* There is also a Docker image to test this application

###### run jUnit tests
`mvn test`

###### create Docker image
`docker build -t iquee/biru .`

###### run Docker image
- to run this image standalone, FIRST is necessary to pull & run another image: mongo
`docker run -d -p 27017:27017 --name mongo mongo`

- after, run command to pull/run this project image(if you have created image, Docker will use it. If not, Docker will pull from docker-hub)
`docker run --link mongo --name biru-spring-boot -p 9000:9000 iquee/biru`

###### run docker-compose
- to pull automatic mongo and biru-application from Docker Hub, run this command(You must have docker-compose installed)
`docker-compose up`