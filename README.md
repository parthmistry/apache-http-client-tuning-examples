# apache-http-client-tuning-examples

## Compile and prepare application for running on Linux

### Prepare demo-client applications
1) Navigate to demo-client folder
2) Compile project and copy all dependency jars into a folder using below command
```
./gradlew clean build copyDependencies
```

### Prepare demo-web application
1) Navigate to demo-web folder
2) Edit src/main/resources/application.yml with elasticsearch server details
3) Compile project using below command
```
./gradlew clean build
```

## demo-web application server
To start web application server
```
java -jar build/libs/demo-web.jar
```

## Default HTTP Client Test
To run blocking client app
```
java -cp build/libs/demo-client.jar:dependencies/* post.parthmistry.democlient.blocking.DefaultTest demoweb-server-host demoweb-server-port
```

To run non-blocking client app
```
java -cp build/libs/demo-client.jar:dependencies/* post.parthmistry.democlient.nonblocking.DefaultTest demoweb-server-host demoweb-server-port
```

## Tuned HTTP Client Test
To run blocking client app
```
java -cp build/libs/demo-client.jar:dependencies/* post.parthmistry.democlient.blocking.TunedTest demoweb-server-host demoweb-server-port
```

To run non-blocking client app
```
java -cp build/libs/demo-client.jar:dependencies/* post.parthmistry.democlient.nonblocking.TunedTest demoweb-server-host demoweb-server-port
```

## Default Index Request Test
To run client app
```
java -cp build/libs/demo-client.jar:dependencies/* post.parthmistry.democlient.index.DefaultTest demoweb-server-host demoweb-server-port data/airline.csv
```

## Tuned Index Request Test
To run client app
```
java -cp build/libs/demo-client.jar:dependencies/* post.parthmistry.democlient.index.TunedTest demoweb-server-host demoweb-server-port data/airline.csv
```
