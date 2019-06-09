# Instructions

Some instructions to build, run and test this spring-boot application

Clone the repository run this commands in root directory to:

###### run the application
`mvn spring-boot:run`
> Access [Index](http://localhost:9000) page

> To see the API Documentation and Try: [Swagger Rest API's](http://localhost:9000/swagger-ui.html)

* First build/run may take some minutes to download all dependencies
* The application is set to run with a in-memory database, H2. There is a option to change to MySQL in application.properties
* There is also a Docker image to test this application

###### run jUnit tests
`mvn test`

###### run jMeter. Result file will be available in: /biru/src/test/jMeter/results/xxxxxxxx.csv 
`mvn jmeter:jmeter`
* to run JMeter test, before is necessary run the application: `mvn spring-boot:run`

###### run all tests(jUnit & jMeter) and build the .jar
`mvn clean install`

###### create Docker image
`docker build -t iquee/biru:1.0.0 .`

###### pull image from Docker Hub
`docker pull iquee/biru:1.0.0`

###### run Docker image
`docker run -p 9000:9000 iquee/biru:1.0.0`