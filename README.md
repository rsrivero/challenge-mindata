# challenge-mindata


### Ver documentacion de la Api

Swagger: http://localhost:8080/swagger-ui.html


### Build image

`docker build -t challenge-mindata:1.0 .`

`docker run -d -p 8080:8080 -t challenge-mindata:1.0`


### Info adicional
GET http://localhost:8080/v1/hero?page=0&size=1&sort=name,ASC&search=name:*h*

Se uso **spring-search** que usa **Specifications** de Spring-Boot: https://github.com/sipios/spring-search
