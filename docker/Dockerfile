FROM openjdk:17
WORKDIR /opt
COPY *.jar .
ENTRYPOINT exec java -Xms512M -Xmx1G -Dspring.profiles.active=$PROFILE -jar *.jar
