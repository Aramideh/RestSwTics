FROM tomcat:8.5-alpine
MAINTAINER spring-boot-hello-world.wyona.org
VOLUME /tmp
RUN rm -rf /usr/local/tomcat/webapps/ROOT
COPY target/hello-world-webapp-1.0.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

#FROM frolvlad/alpine-oraclejdk8:slim
#MAINTAINER spring-boot-hello-world.wyona.org
#VOLUME /tmp
#ADD target/hello-world-webapp-1.0.0-SNAPSHOT.jar app.jar
#RUN sh -c 'touch /app.jar'
#ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
#EXPOSE 8080
