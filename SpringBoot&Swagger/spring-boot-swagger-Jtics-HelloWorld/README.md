README
======

Minimal Spring Boot based RESTful 'Hello World' example, including Swagger (using Springfox) and Docker
(Also see https://spring.io/guides/gs/rest-service/)

Requirements
------------

* JDK: 1.8
* Maven version: 3.3.3 (Please double check Maven .m2/settings.xml)
* Either Tomcat: 8 (please build.sh)
* or Docker (please see below)

Build and deploy
----------------

sh build.sh

Browser
-------

http://127.0.0.1:8080/hello-world-webapp-1.0.0-SNAPSHOT/

Docker
------

* Build webapp as jar file (see pom.xml)
* Start Docker
* Build image: docker build -t spring-boot-hello-world .
* Show images: docker images
** Remove image: docker rmi -f IMAGE ID
* Run image: docker run -p 8080:8080 spring-boot-hello-world
* Show docker processes: docker ps
* Stop specific docker process: docker stop CONTAINER ID
* Request in browser: http://127.0.0.1:8080/swagger-ui.html

Specification and Testing
-------------------------

https://www.yulup.com/en/projects/fe937e1d-3bb4-4012-a963-04848bd955ba/index.html

IntelliJ IDEA
-------------
* Start IntelliJ
* File -> New -> Project from Version Control -> Git
** https://github.com/wyona/spring-boot-swagger-Jtics-HelloWorld
*** https://github.com/wyona/spring-boot-swagger-Jtics-HelloWorld.git
* Set JDK (see Version above)
* Reimport All Maven Projects
* Run clean/install
* Startup Server
* http://127.0.0.1:8080
