## RestFull WebService Using SpringBoot and Swagger Framewok for Smallworld Session

Using open source frameworks like SpringBoot and Swagger this repository demonstrates how to communicate information with a Smallworld session and subsequently Smallworld VMDS database.

This respository consists of two distinct parts, one is the magik code which will be launched at the smallworld session ( server ) and the standalone SpringBoot application with an elegant user interface which has been provided with the open source Swagger framework( see below image).

<img src="https://github.com/Aramideh/RestSwTics/tree/master/raw/tester.jpg" alt="Image of Application" width="252" height="448">

### Getting Started

* Run the Hello-Jtics.magik at the Smallworld session. after compiling the magik class, run the code below to start the tics server

```
MagikSF> hello_jtics.start()
```

you will see the message *****"TICS server started"*****  , now if you see this, you are good to go. the default tics server port is *****3040***** , you can change it in the magik file.

* for the SpringBoot standalone application , go to the SpingBoot&Swagger folder and edit *****execute_spring_boot.bat***** file, the java path should be corrected in this file, this application works with *****java 1.8***** . after pointing the bat file to the correct path of java 1.8, just double click and run it, webServer will automatically launch.
you can view the web methods in *****http://localhost:9090/swagger-ui.html***** , if you want to change the port, just change it in the bat file. it's that simple with java and you still sticked to magik!
currently only the GET method have been included in this repository, but with a little bit of search you can include POST and DELETE methods either or you can chill out untill i have some free time to include them.


### Authors

* [**Sadeq Aramideh**](https://github.com/Aramideh)

### Acknowledgments
* [SpringBoot Framework](https://spring.io/projects/spring-boot)
* [Swagger Framework](https://swagger.io/)
* [Smallworld GE](https://www.gegridsolutions.com/geospatial/catalog/smallworld_core.htm)



