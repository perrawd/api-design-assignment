FROM maven:4.0.0-jdk-17

WORKDIR /api-design-assignment
COPY . .
RUN mvn clean install -DskipTests

CMD mvn spring-boot:run
