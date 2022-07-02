# matches

Matches SpringBoot Service

# Instructions

## Setup

You need:

[JDK16+](https://jdk.java.net/)

[Gradle 7.2+](https://gradle.org/releases/)
(Alternatively you can use the gradle wrapper with `gradlew` instead of the `gradle` command)

to build and run this project.

The application doesn't have an insert endpoint, however there is a `DatabaseSeed` class responsible for feeding the
initial data. As default, I'm inserting 5 matches, but more can be added if needed.

## Building

Run `gradle clean build` in the command line interface.

Run `gradle bootBuildImage` in the command line interface.

## Testing

Run `gradle clean test` in the command line interface.

## Running

Run `docker-compose up` in the command line interface.

## Documentation

(default: http://localhost:8080)
server:port/swagger-ui.html

## Usage
You need to send a query parameter `licenses` that accept many arguments.
The format is this `TYPE-id`;

You can run the api from swagger or curl/Postman.

Here's an example using curl:
`curl -X 'GET' \
'http://localhost:8080/v1/match?licenses=TOURNAMENT-t1&licenses=MATCH-m5' \
-H 'accept: */*'`
