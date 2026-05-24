# 1. Build Step
FROM maven:3.9.14-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .
COPY sonar-project.properties .
RUN mvn -B -q -e -DskipTests dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests

# 2. Test Step
FROM builder AS tester

RUN mvn verify

# 3. Sonar Scan Step
FROM sonarsource/sonar-scanner-cli:12.1.0.3233_8.0.1 AS sonar

WORKDIR /app

COPY --from=tester /app /app

ARG SONAR_HOST_URL
ARG SONAR_TOKEN
ENV SONAR_HOST_URL=${SONAR_HOST_URL}
ENV SONAR_TOKEN=${SONAR_TOKEN}

RUN sonar-scanner

# 4. Build Step
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

COPY --from=sonar /app/target/*.jar api.jar
EXPOSE 8080
ENV JAVA_OPTS=""

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar api.jar" ]