# matches

Matches SpringBoot Service

# Instructions

## Setup
You need:

[JDK16+](https://jdk.java.net/)

[Gradle 7.0.2+](https://gradle.org/releases/)
(Alternatively you can use the gradle wrapper with `gradlew` instead of the `gradle` command)

to build and run this project.

## Building

Run `gradle clean build` in the command line interface.

Run `gradle bootBuildImage` in the command line interface.

## Testing
Run `gradle test` in the command line interface.

## Running
Run `docker-compose up` in the command line interface.

# description

## Task

Create a Restful service to display details of licensed tennis matches for a customer.

A customer can may license either an individual match or a whole tournament. Every match is part of a tournament. The
service should support multiple customers with different license agreements.

The service should return the following as JSON

An array of all matches a customer has purchased. Each element should contain:

matchId: Unique ID for the match startDate: Date and Time the match is scheduled to start playerA: Name of player A
playerB: Name of player B summary: An optional parameter called summaryType can be set to any of:
AvB - in which case return string "<playerA> vs <playerB>"
AvBTime - in which case use the start time to return string "<playerA> vs <playerB>, starts in x minutes". when start
time is in future. and "<playerA> vs <playerB>, started x minutes ago" when start time is in the past.

## Returning the test

Please provide a link to a GitHub repo containing the code, including any special instructions. We will be looking for
code that is clean and maintainable.

# Questions

* Can a customer have more than one license? Maybe... I will assume so. Perhaps 2 individual matches or 1 tournament + 1
  individual match or maybe even 2 tournaments, who knows.
* If a customer can have more than one license, would they get all the matches in a single call? Absolutely! Let's save
  on these service calls.
* Can a customer have a license from a tournament and also an individual match from the same tournament? I will assume
  that it wouldn't be possible for this happen in whatever license buying service that happens before this one. IF that
  could be possible there would be ways to solve it using hash collections, but I will assume it won't happen.
* Can I correlate "LicenseId" to "MatchId" or "TournamentId"? I will use this approach for simplicity.
* But then how can we make sure what match is from which tournament? We don't, it's not in the scope, but in reality we
  should also probably return the tournament ID or have an array of tournaments with an array of matches and group by
  tournament in the consumer.
* If I can receive multiple Licenses of different types, how I am going to pass this as a request parameter and filter
  it? I will assume the License is a String and the format is this: "LicenseType-Match/TournamentId" We'll be able to
  receive an array/list of licenses and we'll parse it and get the correct matches based on that.