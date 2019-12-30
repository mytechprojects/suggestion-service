# Coveo Coding Challenge - Suggestions Service

## How to Run
### Pre-requisites
 - JAVA 8 or higher 
### Packaging
The application has been packaged as a fully contained jar file.  The executable distribution is called suggestion-service.jar.  The source code along with tests is provided in a separate package. The source project can be imported as a maven project in any Java IDE (such as eclipse). 
### Usage
Assume the jar file is in a folder named "work"
```sh
$ cd work
$ java -jar suggestion-service.jar    ----- Will start the service on port 8080
``` 

### API Reference
I have used Swagger to document the REST APIs.  I have also used springfox-swaggerui to include
the swagger UI that makes it easy to view the REST APIs and even send requests

```sh
http://localhost:8080/swagger-ui.html
``` 

## Solution 
The solution has been implemented using Springboot on Java 8.  My focus was to keep the solution simple, readable and easily extensible.  Apart from this Readme, i have comments in the source code.

### Approach



### Assumptions



### Future enhancements


#### Online Markdown Editor
See [Markdown Editor](https://dillinger.io/)