FROM java:8
ADD target/calendar_blocking.jar calendar_blocking.jar
ENTRYPOINT ["java","-jar","calendar_blocking.jar"]
