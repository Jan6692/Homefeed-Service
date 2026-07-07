# Homefeed-Service
This repository contains a homefeed service for a mobile app.

# Starting and Running the application
To run the application simply clone the repository, open it with IntelliJ (or your preferred IDE) and Click on run HomeFeedServiceApplication.\
The application will set up and initialize a h2 database which will be filled with a few sample entries at startup. 
A single GET endpoint will be brought up for retrieving the HomeFeedModule:
- GET localhost:8080/endpoints/homefeed
- the endpoint needs a parameter "userId" which has to be an integer or long value - e.g. localhost:8080/endpoints/homefeed?userId=1
- the database is filled with following user ids: 1, 2, 3, 4, 5, 6 (6 is an example that will cause an internal exception 
as the user has selected a language which is not available)
- all users have different languages and will get back the HomeFeed in a different language
- Incorrect ids or user id format will result in error messages with equivalent http response code

# Design Decisions
## Entities & Database
As database a simple h2 database was used as it is a quick and easy way for sample projects. I thought of possible 
tables/entities that could be useful to get customer and business data for building the feed and decided to have:
- Greeting
- Product
- SaleBanner
- User

Greeting and SaleBanner may not be the same for every customer depending on language, time, season or ad promotion. So 
different variations may exist in a database getting changed by business or marketing employees. On top a user/customer 
table is needed for sure to keep the customer data as well as a product table keeping product information like title, 
price, description, etc.\
For correctly accessing this data an interface layer between database/entities and application services is needed for 
which the repository package was introduced. If additional complexity is needed for data ingestion or retrieval other
than very basic SQL queries an additional DAO layer to connect repository interface with services may be useful - here I 
skipped it in interest of time.

## Feed Modules
As the modules are being build based on customer needs and other circumstances they should not be stored in a database 
like entities. They will be built dynamically retrieving data from different sources. Moreover they are used as 
DTOs and serialized using jackson json annotations. The same applies for HomeFeedResponse class which I moved into a 
separate module as there may be different types of responses in a more complex real life scenario.

## Service
The service module contains different services that are used for doing all the work that is needed between data retrieval 
and returning the feed modules. HomeFeedService gathers all the necessary information for the feed modules and builds them.
PreferencePredictionService was mocked in this sample, but it may be used to use the preferences of a specific user to 
make a prediction which product should be teasered. The services are there to not overload the HTTP methods of the 
REST Controller.

## REST Controller
HomeFeedController provides the REST interface for the user and calls the corresponding services to retrieve the data 
that the REST interface user is requesting.

## Exceptions
To be able to have unified responses for different error scenarions a global exception handler was introduced along 
with some custom exceptions that can be used each time a user id is invalid or a language is not available. This way 
these scenarios are handled in the same manner no matter where they occure and provide a clear feedback to the REST 
endpoint user.

# Next Steps
As next steps there are the following points that I would like to tackle:
- Complete JUnits - some JUnits are not completed yet. We will need more test coverage here for sure. Also database 
access queries may be worth being tested (especially when complexity increases for a real life homefeed)
- Logging: Logging should be enhanced (e.g. debug logging) even though I already added a basic logging.
- PreferencePredictionService: Currently the service is just randomly choosing products for a customer. This should be 
enhanced by making decisions based on customer preferences.
- Additional REST endpoints - The RESTController for sure may need some additional endpoints e.g. to retrieve specific
modules only and not each time a complete feed. Moreover additional information may be needed to be requested by the UI
or user.
- Database entity complexity - As of now the entities are really simple. Here some improvements may be useful to increase 
the information stored and connect entities via primary keys.
- FeedModules enhancements - The modules only contain the few sample properties mentioned in the task description. Here 
a lot more could be added. Also specific modules could be based on time, country, ad promotions, etc. Of course also the
core HomeFeedResponse should get more complexity and additional properties.
- Performance and Resilience Tests (in UAT environment)
- Implement User authentification

# How to run in a cloud/kubernetes environment
To run this application in a cloud kubernetes environment a few steps are needed to prepare:
- Set up a database that contains the entity information e.g. in cloud
- Set up a CI/CD mechanism to build and containerize + deploy the application image
- Move logic related configurations into properties.yaml (e.g. everything needed for functional on the fly customization)
- Move kubernetes related hardware configurations into a separate config map
- Externalize secrets into a secret manager (e.g. database access credentials)
- Plan hardware (CPU, memory, pods) and set up a deployment file
- Introduce health checks
- Set up a monitoring tool (e.g. New Relic / Splunk) to be able to monitor the application and implement alerts