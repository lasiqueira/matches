# matches
Matches SpringBoot Service

# description

## Task

Create a Restful service to display details of licensed tennis matches for a customer.

A customer can may license either an individual match or a whole tournament. Every match is part of a tournament. The service should support multiple customers with different license agreements.

The service should return the following as JSON

An array of all matches a customer has purchased. Each element should contain:

matchId: Unique ID for the match
startDate: Date and Time the match is scheduled to start
playerA: Name of player A
playerB: Name of player B
summary: An optional parameter called summaryType can be set to any of:
AvB - in which case return string "<playerA> vs <playerB>"
AvBTime - in which case use the start time to return string "<playerA> vs <playerB>, starts in x minutes". when start time is in future. and "<playerA> vs <playerB>, started x minutes ago" when start time is in the past.

## Returning the test
Please provide a link to a GitHub repo containing the code, including any special instructions. We will be looking for code that is clean and maintainable.
