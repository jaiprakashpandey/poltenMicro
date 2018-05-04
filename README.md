## Vehicle Status report

This application is developed to provide the status of the vehicles.
Will be used by company named to track the current connection status of each vehicles.
application has 2 part one microservice application & angular 
support from backend which can be communicated by both fronth end status application
and also the individual vehicles updating their current informations 


## Main application class file name
 MicroServiceApplication.java

## Resources used for application
service.yaml
testService.yaml for tests

## basic infrastructure used for developments & need to build and start the API:
OS 64 bit
JAVA 1.8(jdk1.8.0_91)
Maven 3.0.5
Junit 4.8.2
Power Mockito 1.4.9
Dropwizard 1.3.1 includes supports for embedded jetty , jersy for rest, hibernate migrate..etc
H2 embedded DB.
Angular 5


## To build the application:
 mvn clean install

## Test cases
There are unit & integration tests written into the application which tests microservice functions while build to find closest restaurents with long & close distances.

## To start the application to serve use below commands(change according to your local or server paths):

1. setup DB
C:\jai\ang\alten\target>java -jar vehicle-status-1.0-SNAPSHOT.jar db migrate C:\jai\ang\alten\src\main\resources\myservice.yaml

2. start backend microservice
C:\jai\ang\alten\target>java -jar vehicle-status-1.0-SNAPSHOT.jar server C:\jai\ang\alten\src\main\resources\myservice.yaml

3. start SPA or frond end angular applictaion
1. ng serve

## urls to access the applications
NOTE:First time the status page will show blank status 
There is a button to migrate existing or setup some test vehicle data to test the application.
front end application url: https://localhost:4200/vehicles
backend microservice urls: 
https://host:8080/status --> for all vehicles status
https://host:8080/status/migrate --> to setup some test vehicles into applciation
https://host:8080/status/clear --> To clear test data from application


## sample server startup and end points tested OK
INFO  [2018-05-04 13:42:57,455] io.dropwizard.jersey.DropwizardResourceConfig: The following paths were found for the configured resources:

    GET     /status (statusAppl.restapi.VehiclesMicroServiceResource)
    GET     /status/clear (statusAppl.restapi.VehiclesMicroServiceResource)
    GET     /status/migrate (statusAppl.restapi.VehiclesMicroServiceResource)
    POST    /status/update (statusAppl.restapi.VehiclesMicroServiceResource)

INFO  [2018-05-04 13:42:57,549] org.eclipse.jetty.server.AbstractConnector: Started admin@10d9c41{HTTP/1.1,[http/1.1]}{0.0.0.0:8081}
INFO  [2018-05-04 13:42:57,549] org.eclipse.jetty.server.Server: Started @6469ms


## Added HEALTH CHECKS -- with Unhealthy applictaion example
This microservice contains a basic health check to check the preconditions metrices in PRODUCTION ENV if the conditions exist to get expected result
Health check file: StatusServiceHealthCheck.java,MigrateHealthCheck,ClearTestDataHealthCheck.java
This applciation is built on keeping in mind to provide metrics on Admin Port 8081.
if we hit the admin port when applictaion is UP and running we can see all metric and application healths at present.

example below:

hit in browser the admin URL with port 8081
http://localhost:8081/

Result:

Operational Menu
Metrics
Ping
Threads
Healthcheck

click above healthcheck link to see example when one of conditions are withdrawn and build application to test this functionality!


## Limitations & Assumptions
1. In memory database H2 is used.
2. Backend Microservice secuirty features are not implemented.
3. Also CORS setting has been enabled in the server side.
4. Simulation of vehicles sending status from angular UI to service calls every minute.
5. This is assumed that both the SPA and microservice will be deployed on same server not for testing in first version.
    So There are hard coded host names for localhost direct url for microservice in SPA integration service layer, will be improved in next version to have the 
6. Full screen functionality is not bydefault at present as there are not real vehicles at present 
  & User first time need to do Admin setting and then can click full screen for the constant reporting from the screen.
	






