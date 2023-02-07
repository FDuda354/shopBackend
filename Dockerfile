FROM eclipse-temurin:17-jdk-alpine as builder
WORKDIR /opt/app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY ./src ./src
#COPY src/main/resources/static/data/productImages/ /app/data/productImages/
RUN ./mvnw clean package -Dmaven.test.skip
CMD ["java", "-jar", "/opt/app/*.jar"]

FROM eclipse-temurin:17-jre
WORKDIR /opt/app
COPY --from=builder /opt/app/target/*.jar /opt/app/*.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/opt/app/*.jar"]
