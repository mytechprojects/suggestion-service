# Coveo Coding Challenge - Suggestions Service

## Usage
### Pre-requisites
 - JAVA 8 or higher 
 - Apache Maven 3.3.9 or higher
### Development
The service has been developed on Spring Boot v2.2.2.RELEASE, Spring v5.2.2.RELEASE platform.  Clone the repository and import the project into any Java IDE as an existing maven project
### Build and Packaging
Change into the cloned repository folder and build the project
```sh
$ cd suggestion-service
$ mvn clean package
``` 
maven package command will build a self-contained executable jar file called suggestion-service.jar in the target folder.  This jar can be deployed into any container to run the web project.

For Development, it is recommended to run inside mvn itself using the below command
**mvn spring-boot:run**

### Configuration
By default the web application starts on port 8080.  I have checked-in the cities500.txt input file into the project.  By default, the index build uses this input file.
A much larger input file can be provided as a command line parameter (geodata.file) specifying the absolute file path. 
**mvn spring-boot:run -Dspring-boot.run.arguments="--geodata.file=/tmp/myfile.txt"**
I have tried with the biggest file provided by geonames.  (allCountries.txt which is a 1.4G file).  The current index is a full in-memory datastructure.  For optimized index creation and to avoid out of memory errors, please set the following JVM parameters
**-Xms2G -Xmx4G  -XX:+UseParNewGC**
The entire command can be summarized as
**mvn spring-boot:run -Dspring-boot.run.arguments="--geodata.file=/Users/mrajasekha/tmp/allCountries.txt" -Dspring-boot.run.jvmArguments="-Xms2G -Xmx4G  -XX:+UseParNewGC"**

The whole index creation process for the largest file took about 1.7 minutes average with the above mentioned JVM performance tuning.
### API Reference
I have used Swagger to document the REST APIs.  I have also used springfox-swaggerui to include
the swagger UI that makes it easy to view the REST APIs and even send requests

```sh
http://localhost:8080/swagger-ui.html
``` 

## Solution Design approach
The solution has been implemented using Springboot on Java 8.  The solution has three key components:
1. Index Builder: Responsible for building the core index datastructure
2. Scoring Engine: Handles custom scoring algorithm
3. Web Layer: Exposes the suggestion endpoint as a REST API


### Feature Summary
1.Protected REST endpoint: To demonstrate API level security, i have introduced a header (X-API-KEY).  This header can be used to hook into a authentication layer. For the demo purpose, the only valid X-API-KEY will be SUGGESTION-SERVICE-APIKEY (specifed in the Constants file)
2. Personalization hook: Incoming request can be enriched by adding attributes to the RequestContext. The UserRequest class can be used for this.
3. Index Build on startup:  I have leveraged the Spring event model to ensure that the index is built on the service startup.  Since the service cannot function without a valid index datastructure, any errors in this process will cause a System exit. 
4. Index maintenance hook:  In a production scenario, there could be situations that demand index maintenance.  During such instances, the index ready boolean can be utilized to either return a temporary response or Out of Service kind of response
5. Pluggable Scoring Engine:  I have implemented a simple scoring engine that depends on the query match first followed by geo-graphic proximity.  We can obviously think of much more complicated scoring algorithms.  The interface plugging approach will allow for easier integration with any other scoring engine.
### Assumptions
  - In-depth data validation
#### Online Markdown Editor
See [Markdown Editor](https://dillinger.io/)