FROM gradle:7.6 as builder
WORKDIR /usr/src/app
COPY src ./src
COPY build.gradle .
COPY curl-test.sh curl-test.sh
RUN ["gradle", "bootJar"]

EXPOSE 8080

FROM openjdk:17
ARG JAR_FILE=build/libs/*.jar
COPY --from=builder /usr/src/app/${JAR_FILE} app.jar
# Check if curl, jq is installed, if not install them
#RUN microdnf update
#RUN command -v curl >/dev/null 2>&1 || { microdnf install --assumeyes curl; }
#RUN command -v jq >/dev/null 2>&1 || { microdnf install --assumeyes jq; }
#RUN microdnf clean all
ENTRYPOINT ["java", "-jar", "/app.jar"]

