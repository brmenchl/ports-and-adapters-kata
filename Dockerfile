FROM openjdk:17-jdk-alpine3.14
RUN apk --no-cache add curl
COPY build/libs/birthday-kata-0.1-all.jar birthday-kata.jar
CMD java ${JAVA_OPTS} -jar birthday-kata.jar