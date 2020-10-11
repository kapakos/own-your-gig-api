FROM openjdk:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/own-your-gig-api-0.0.1-SNAPSHOT-standalone.jar /own-your-gig-api/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/own-your-gig-api/app.jar"]
