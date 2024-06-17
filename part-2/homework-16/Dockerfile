FROM eclipse-temurin:17-jdk-alpine as builder
WORKDIR /workspace/build

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw -B -DskipTests clean package
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM eclipse-temurin:17-jre-alpine
WORKDIR /workspace/app
COPY --from=builder /workspace/build/target/*.jar /workspace/app/*.jar
ENTRYPOINT ["java", "-jar", "/workspace/app/*.jar"]