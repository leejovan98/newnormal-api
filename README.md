# NewNormal API 

## About

NewNormal API is an Events Booking System where users can create their own account to create and host events or join events they are interested in.

## Pre Requisites:

The NewNormal API is based on Java and the client system is required to have JVM installed before the tool can run on the system. Additionally, the following application is required to be installed:
* MySQL Workbench 
* MySQL Server

## Configuration

The following details will need to be edited in the application.properties file:

1. The file can be found at newnormal-api/src/main/resources/application.properties
2. Under JDBC DataSource configuration, please change the following:
    * MySQL URL(Change Port Number): The port that MySQL Server uses. Typically 8889 for MAC and 3306 for Windows 
    * MySQL username: The username used to log into MySQL Server. Typical username used is root.
    * MySQL password: The password used to log into MySQL Server. 

The following Schema needs to be created using MySQL Workbench:

1. Create a new query and run `create schema new_normal;` 

## Running the API

Excute `mvnw clean install` if it is your first time running.

To run the API execute `mvnw spring-boot:run`

After running the API, be sure to run NewNormal-WebApp as well for the frontend interface.

You can access the homepage at `http://localhost:8080/login`

The API can be stopped by issuing a keyboard interrupt (CTRL-C).
