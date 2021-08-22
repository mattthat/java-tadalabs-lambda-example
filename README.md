# example-java-lambda

### setting up dynamo
```
$ docker pull amazon/dynamodb-local 
$ docker run -p 8000:8000 amazon/dynamodb-local -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -sharedDb
$ aws dynamodb create-table --cli-input-json file://src/main/resources/ddl/message-processing.json --endpoint-url http://localhost:8000
```

### local runtime
```
$ docker pull lambci/lambda
$ ./gradlew clean build
$ docker run --net=host -i -e AWS_REGION=local -e environment=dev --rm -v "$PWD/build/docker":/var/task lambci/lambda:java8 org.tadalabs.lambda.example.MessageSequencerLambda '{"type": "START_SESSION", session: "some-session-key"}'
$ docker run --net=host -i -e AWS_REGION=local -e environment=dev --rm -v "$PWD/build/docker":/var/task lambci/lambda:java8 org.tadalabs.lambda.example.MessageSequencerLambda '{"type": "REPORT"}'
$ docker run --net=host -i -e AWS_REGION=local -e environment=dev --rm -v "$PWD/build/docker":/var/task lambci/lambda:java8 org.tadalabs.lambda.example.MessageSequencerLambda '{"type": "SEQUENCE"}'
```
