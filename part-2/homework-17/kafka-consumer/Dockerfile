FROM maven:3.8.7-openjdk-18-slim AS builder
WORKDIR /workspace/build

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src src

RUN mvn clean install -DskipTests -B
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM eclipse-temurin:18-jre-alpine
RUN apk add bash
WORKDIR /workspace/app
COPY --from=builder /workspace/build/target/*.jar /workspace/app/*.jar
ENV JAVA_OPTS ""
CMD ["bash", "-c", "java ${JAVA_OPTS} -jar /workspace/app/*.jar"]