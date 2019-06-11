[![Build Status](https://travis-ci.org/iquee/biru.svg?branch=master)](https://travis-ci.org/iquee/biru)

# Challenge
<p>This application is a solution for ZX Code Challenge | Backend.</p>
<p>The following technologies were used for the development:</p>
 	
* Java 8
* Spring boot as MVC framework: v 2.1.5.RELEASE
	- starter-data-jpa
	- starter-data-rest
	- starter-data-web
	- starter-data-security
	- starter-data-test
	- spring-data-mongodb
* MongoDB as NoSQL databases					
* Swagger for API documentation
* JUnit to unity tests
* Maven as project dependency management
* Travis-CI as platform to run the tests
* Github as source repository
* Docker to run in a container 

## Usage
There are two ways to run the application.<br>
* by docker-compose
* by Docker images standalone

##### Prerequisites:
* docker and docker-compose installed<br>

First, clone the repository, access the folder and execute these commands in root directory to:

1. <strong>by docker-compose</strong>: enter this command to pull automatic biru-springboot and mongo from Docker Hub. 
	```sh
	docker-compose -f docker-compose.yml up
    ```

2. <strong>by Docker images standalone</strong>: execute these command in this order:
	```sh
	docker run -d -p 27017:27017 --name mongo mongo
    ```    
    ```sh
	docker build -t iquee/biru .
    ```	
    ```sh
	docker run --link mongo --name biru-spring-boot -p 9000:9000 iquee/biru
    ```
    <em>If you have created image, Docker will use it. If not, Docker will pull from Docker Hub.</em>


## Accessing the application
After application have initialized
1. Access [Index](http://localhost:9000) page
2. To see the API Documentation and test some Rest API's, access [Swagger Rest API's](http://localhost:9000/swagger-ui.html)


## Run Tests
To execute tests, it's necessary to use the docker-compose. jUnit tests are executed with a embedded MongoDB.<br>
- <em>Important 1: to run test, this [json](https://github.com/ZXVentures/code-challenge/blob/master/files/pdvs.json) will be imported on startup</em>
- <em>Important 2: To verify the tests run successfully in a SaaS CI, access [Travis-CI](https://travis-ci.org/iquee/biru)</em><br>

	```sh
	docker-compose -f docker-compose.test.yml up
    ```

## Contact

Luiz Henrique K Taira - luizhtaira@gmail.com<br>
Project Link: [https://github.com/iquee/biru](https://github.com/iquee/biru)<br>
LinkedIn: [https://www.linkedin.com/in/lhktaira/](https://www.linkedin.com/in/lhktaira/)