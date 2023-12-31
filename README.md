# Receipt Processor
This is a RESTful API app developed with the below two functionalities: 
* processing of receipts with the given receipt and by generating a receipt ID.
* calculation of the associated points with the receipt and fetch the points with the given receipt ID.

## Technologies Used
* Java 17
* Junit 5.9.3 and mockito
* Spring Boot 3.1.1
* Maven 3.9.2
* IntelliJ IDE
* Postman
* Docker(To execute the program without any dependency of other setup)

## Project Architecture
* src/main/java/com/fetch/rewards/receipt/processorchallenge/ProcessorChallengeApplication.java
  - Spring Boot App for bootstrapping and launching the application
* src/main/java/com/fetch/rewards/receipt/processorchallenge/contoller/ReceiptController.java
  - Control layer defining the API
* src/main/java/com/fetch/rewards/receipt/processorchallenge/model
  - Data model layer with Receipt and Item classes
* src/main/java/com/fetch/rewards/receipt/processorchallenge/service/ReceiptService.java
  - Service layer for calculating the points
* src/test/java/com/fetch/rewards/receipt/processorchallenge/ProcessorchallengeApplicationTests.java
  - Unit tests with tests for control and service layers

## Summary of API Specification
### Endpoint: Process Receipts
* Path: `/receipts/process`
* Method: `POST`
* Payload: Receipt JSON in the following format
* Response: JSON containing an id for the receipt.

Description:
Takes in a JSON receipt (Please see example in the example directory) which returns a JSON object with an ID generated by the code.
The ID returned is the ID that should be passed into `/receipts/{id}/points` to get the number of points the receipt was awarded.
Please note that the Data does not survive an application restart.

Example Response:
```json
{ "id": "7fb1377b-b223-49d9-a31a-5a02701dd310" }
```

### Endpoint: Get Points
* Path: `/receipts/{id}/points`
* Method: `GET`
* Response: A JSON object containing the number of points awarded.

Description:
A simple Getter endpoint that looks up the receipt by the ID and returns an object specifying the points awarded.

Example Response:
```json
{ "points": 32 }
```

## Build & Run
1. Install Docker, if not already installed.
2. Clone the repository as the below command in the terminal:
```
git clone https://github.com/palaniappanramiah/processorchallenge.git
```
3. Navigate into the project directory:
```
cd processorchallenge
```
4. Start the docker desktop and build the program using the following command:
```
docker build -t processorchallenge .
```
5. Run the docker container by the following command and leave the terminal open:
```
docker run -p 8080:8080 processorchallenge
```

Alternatively, this can be built and run using Maven

## Test
1. Open a new terminal and navigate into the project directory:
```
cd processorchallenge
```
2. Run this in the terminal within the 'processorchallenge' directory:
```
curl -X POST -H "Content-Type: application/json" -d '{
  "retailer": "Target",
  "purchaseDate": "2022-01-01",
  "purchaseTime": "13:01",
  "items": [
    {
      "shortDescription": "Mountain Dew 12PK",
      "price": "6.49"
    },{
      "shortDescription": "Emils Cheese Pizza",
      "price": "12.25"
    },{
      "shortDescription": "Knorr Creamy Chicken",
      "price": "1.26"
    },{
      "shortDescription": "Doritos Nacho Cheese",
      "price": "3.35"
    },{
      "shortDescription": "   Klarbrunn 12-PK 12 FL OZ  ",
      "price": "12.00"
    }
  ],
  "total": "35.35"
}' http://localhost:8080/receipts/process
```
3. This will return an 'id' in the terminal, copy that id and replace {id} in the following command:
```
curl -X GET http://localhost:8080/receipts/{id}/points
```
4. This should return
```
{ "points": 28 }
```

Alternatively, this can be tested using Postman

## Run the Unit Test

1. Make sure Maven is installed locally
2. Run this in the terminal within the 'processorchallenge' directory:
```
docker run -it --rm -v $PWD:$PWD -w $PWD -v /var/run/docker.sock:/var/run/docker.sock maven:3.9.0-eclipse-temurin-17 mvn test
```