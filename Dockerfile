FROM openjdk:17 as build
WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw dependency:go-offline
COPY src src
RUN ./mvnw clean package -DskipTests

FROM openjdk:17
WORKDIR /app
COPY --from=build /app/target/otel-demo-1.jar /app/otel-demo-1.jar
COPY opentelemetry-javaagent.jar /app/opentelemetry-javaagent.jar
EXPOSE 8080
ENTRYPOINT ["java", "-javaagent:/app/opentelemetry-javaagent.jar", "-Dotel.exporter.otlp.endpoint=http://192.168.1.12:4317", "-Dotel.resource.attributes=service.name=java-telemetry-api", "-Dotel.exporter.otlp.protocol=grpc", "-jar", "otel-demo-1.jar"]
