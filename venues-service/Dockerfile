FROM gradle:7.6 as builder
WORKDIR /usr/src/app
COPY src ./src
COPY build.gradle .
RUN ["gradle", "bootJar", "--stacktrace"]

EXPOSE 8080

FROM openjdk:17
ARG JAR_FILE=build/libs/*.jar
COPY --from=builder /usr/src/app/${JAR_FILE} app.jar
#RUN microdnf update
#RUN microdnf --assumeyes install curl jq
ENTRYPOINT ["java", "-jar", "/app.jar"]

