## Address Book
This is a simple address book api that performs basic operations such as adding contact, viewing address book and compare two address books to find unmatched contacts.

## Assumptions
  1. No database is used as there is no requriement for data persistency.
  2. Address Book cannot hold duplicate contact record, meaning each contact is unique based on name.
  3. User's address book when created is always empty.  
  4. There is no delete operation developed.
  5. Build server has java 1.8 runtime environment, and gradle.

## Requirement
You have been asked to develop an address book that allows a user to store (between successive runs of the program) the name and phone numbers of their friends, with the following functionality:

  1. To be able to display the list of friends and their corresponding phone numbers sorted by their name.
  2. Given another address book that may or may not contain the same friends, display the list of friends that are unique to each address book (the union of all the  relative complements).  

For example given:
	Book1 = { “Bob”, “Mary”, “Jane” }
	Book2 = { “Mary”, “John”, “Jane” }
	The friends that are unique to each address book are:
			Book1 \ Book2 = { “Bob”, “John” }

## Technologies & Tools used
  1. Java 1.8, Reactive Spring & Spring boot
  2. Testing - JUnit 5, Postman
  3. Build tool – Gradle 6.4, jacoco code coverage reporting
  4. In memory (java collection) database.
	
## Scope of the project
This application offers following rest apis

POST /addContact/{userName} - create a user.
POST /addressbook/unique/ - check a user's contacts against another addressbook
GET /getAllContacts/{userName} - get list of contacts for the specific addressbook (user)

## Out of Scope
This application does not cover following

  1. API Authentation
  2. User inteface
  3. Containerisation (Docker)
  4. Fully scallable microservices design
  5. Extensive exception handling
	
## Build
```
./gradlew/clean build
```
## Deployment
This repository is configured in heroku CICD pipeline for automatic build and deployment. Whenever the change is made into the master branch, the deployment triggers.
Following the live site url - 

https://gulmohar-example-1.herokuapp.com


## Run
```
./gradlew/buildRun
```

## Testing
- JUnit 
			The application has extensive Junit test cases that covers all required area. 
			[jacoco code coverage report](/build/reports/jacoco/test/html/index.html) 


- Rest api testing
  		Postman has been used to test various rest apis. 
			[PostMan-Project-Collection](https://github.com/jaykishanparikh/addressbook/blob/main/addressbook.postman_collection.json) 

## Future scope
- profiling based on environments (dev, test, uat, and prod)
- implementing docker based container architect
- adding database for persistent storage
- wrapping api with oauth based authentication
