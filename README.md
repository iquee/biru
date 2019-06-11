[![Build Status](https://travis-ci.org/iquee/biru.svg?branch=master)](https://travis-ci.org/iquee/biru)

# Challenge
<p>This application is a solution for ZX Code Challenge | backend.</p>
<p>The following technologies were used for the development:</p>
 	
	- Java 8
	- Spring boot as MVC framework: v 2.1.5.RELEASE
		- starter-data-jpa
		- starter-data-rest
		- starter-data-web
		- starter-data-security
		- starter-data-test
		- spring-data-mongodb
	- MongoDB as NoSQL databases					
	- Swagger for API documentation
	- JUnit to unity tests
	- Maven as project dependency management
	- Travis-CI as platform to run the tests
	- Github as source repository
	- Docker to run in a container 

# Instructions
<p>Some instructions to build, run and test this application</p>
<p>Clone the repository, access the folder and run these commands in root directory to:</p>

##### Run application: Using docker-compose, enter this command to pull automatic biru-application and mongo from Docker Hub. <strong>(You must have docker-compose installed)</strong>:
	> docker-compose up

##### Run jUnit tests: this tests will run with a embedded MongoDB. <strong>(You must have Maven installed)</strong>:
<em>Important 1: to run test, no instance of mongodb:27017 can be on</em>
<em>Important 2: to run test, this [json](https://github.com/ZXVentures/code-challenge/blob/master/files/pdvs.json) will be imported on startup</em>

	> mvn test

##### Create Docker image for this application:
	> docker build -t iquee/biru .

##### Run Docker image: to run this image standalone, FIRST is necessary to pull/run another image: mongo
	> docker run -d -p 27017:27017 --name mongo mongo

##### After mongo: run command to pull/run this project image(if you have created image, Docker will use it. If not, Docker will pull from docker-hub):
	> docker run --link mongo --name biru-spring-boot -p 9000:9000 iquee/biru


# Accessing the application
1. After application have initialized
2. Access [Index](http://localhost:9000) page
3. To see the API Documentation and test some Rest API's, access [Swagger Rest API's](http://localhost:9000/swagger-ui.html)

___


Luiz Henrique K Taira
luizhtaira@gmail.com