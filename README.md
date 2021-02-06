## About

#### Implementation of security mechanism using Spring Security with custom AuthenticationProvider and Filter for REST API based on random persistent tokens as session ids.

## Requirements

#### Application
- Java 8
- Maven

#### Database
- MySQL (8.0+) 

## Start locally
```
mvn clean install
java <env variables: -DvariableName='value'> -jar <jar location>
```

## Environment variables:

- DB_URL (_default_: 'jdbc:mysql://localhost:3306/spring_security_db') - _db url_
- DB_USERNAME (_default_: 'root') - _db username_
- DB_PASSWORD (_default_: 'root') - _db password_
