# InvoiceMe App REST API
Backend InvoiceMe App.<br />
App created to issue invoices.
Additionally, it enables:
* management of issued invoices
* product management
* searching for buyer data by NIP (connection with the Ministry of Finance API)
* checking EUR, USD and CHF exchange rates. (connection to the API of the National Bank of Poland)

See also Frontend InvoiceMe App [https://github.com/PawelM-code/Frontend-Invoice-Me](https://github.com/PawelM-code/Frontend-Invoice-Me) 

## Api Documentation - Swagger
[https://immense-hollows-30003.herokuapp.com/swagger-ui.html](https://immense-hollows-30003.herokuapp.com/swagger-ui.html)

## Demo running on Heroku
On Heroku you can find a deployed version. [https://immense-hollows-30003.herokuapp.com](https://immense-hollows-30003.herokuapp.com)

## Local installation
1. Clone this repo to your local machine using [https://github.com/PawelM-code/Invoice-creator-REST-API.git](https://github.com/PawelM-code/Invoice-creator-REST-API.git)
2. Create MySQL database and give the privilege to the user (example in application.properties)
3. In build.gradle remove dependency: compile 'org.postgresql:postgresql'
4. In build.gradle add dependency: compile 'MySQL MySQL-connector-java'
5. Run app

## Technologies
Project is created with:
* Java
* Gradle
* Hibernate
* Spring
* Spring-boot
* Lombok
* Junit
* Mockito
* Swagger
* Tomcat
* JDBC
* Slf4j