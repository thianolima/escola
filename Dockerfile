ARG BUILD_IMAGE=maven:3.8.5-openjdk-8
ARG RUNTIME_IMAGE=openjdk:8-jre-slim

#####################################################
###  Stage: Build                                 ###
#####################################################
FROM ${BUILD_IMAGE} as build
WORKDIR /app
COPY . .
RUN mvn clean package --file pom.xml -DskipTests -Djacoco.skip=true

#####################################################
### Stage: Run Image                              ###
#####################################################
FROM ${RUNTIME_IMAGE}
COPY --from=build app/target/*.jar app.jar
CMD ["java", "-jar", "api.jar"]
EXPOSE 8080