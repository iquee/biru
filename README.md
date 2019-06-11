[![Build Status](https://travis-ci.org/iquee/biru.svg?branch=master)](https://travis-ci.org/iquee/biru)

# Challenge
// TODO

# Instructions
<p>Some instructions to build, run and test this MicroService</p>
<p>Clone the repository, access the folder and run these commands in root directory to:</p>

##### Run jUnit tests: this tests will run with a embedded MongoDB. <strong>(You must have Maven installed)</strong>
> `mvn test`

##### Create Docker image
> `docker build -t iquee/biru .`

##### Run Docker image: to run this image standalone, FIRST is necessary to pull/run another image: mongo
> `docker run -d -p 27017:27017 --name mongo mongo`

##### After mongo: run command to pull/run this project image(if you have created image, Docker will use it. If not, Docker will pull from docker-hub)
> `docker run --link mongo --name biru-spring-boot -p 9000:9000 iquee/biru`

##### docker-compose: Run this command to pull automatic mongo and biru-application from Docker Hub. <strong>(You must have docker-compose installed)</strong>
> `docker-compose up`

###### Access application itself
1. After application have initialized
2. Access [Index](http://localhost:9000) page
3. To see the API Documentation and test some Rest API's: [Swagger Rest API's](http://localhost:9000/swagger-ui.html)


___



Luiz Henrique K Taira

luizhtaira@gmail.com