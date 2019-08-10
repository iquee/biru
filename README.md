[![Build Status](https://travis-ci.org/iquee/spring-geo-mongo.svg?branch=master)](https://travis-ci.org/iquee/spring-geo-mongo)

Sample application, to create and get a single Restaurant. There is also a service to find the nearest Restaurant from a specific location, through a longitude and latitude and check if it delivery to a specific area

## Usage
##### Pre-requisites:
* docker and docker-compose installed<br>

	```sh
	docker-compose -f docker-compose.yml up

## Accessing the application
After application have initialized
1. Access [Index](http://localhost:9000) page
2. To see the API Documentation and test some Rest API's, access [Swagger Rest API's](http://localhost:9000/swagger-ui.html)

## Run Tests
To execute tests, it's necessary to use the docker-compose. jUnit tests are executed with a embedded MongoDB.<br>
- <em>Important: To verify the tests run successfully in a SaaS CI, access [Travis-CI](https://travis-ci.org/iquee/spring-geo-mongo)</em><br>

	```sh
	docker-compose -f docker-compose.test.yml up
