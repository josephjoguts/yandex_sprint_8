FROM eclipse-temurin:21-jdk-jammy as build

WORKDIR /app

COPY pom.xml .
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn package -DskipTests

FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

RUN apt-get update && \
    apt-get install -y nginx && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

COPY --from=build /app/target/ReportDemoApp-0.0.1-SNAPSHOT.jar /app/

COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 8000

CMD ["sh", "-c", "nginx && java -jar /app/ReportDemoApp-0.0.1-SNAPSHOT.jar"]
