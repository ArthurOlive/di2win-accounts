FROM gradle:6.8.1-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon -x test

FROM openjdk:11-jre
EXPOSE 8080
ENV JAVA_OPTS=-Dspring.profiles.active=production

RUN mkdir /app
RUN chown 1000 /app
USER 1000:1000
COPY --chown=1000:1000 --from=build /home/gradle/src/build/libs/clientservice-0.0.1-SNAPSHOT.jar /app/clientservice.jar
RUN wget -O /app/dd-java-agent.jar 'https://dtdg.co/latest-java-tracer'
ENTRYPOINT exec java -javaagent:/app/dd-java-agent.jar $JAVA_OPTS -jar /app/clientservice.jar