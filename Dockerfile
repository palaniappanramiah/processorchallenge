FROM openjdk:17
COPY ./ ./
RUN  ./mvnw clean package
ENTRYPOINT ["java","-jar","target/processorchallenge-0.0.1-SNAPSHOT.jar"]