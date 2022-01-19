**Project authors:**
- Egert Rumjantsev and Kristjan Kattus

**Tech stack:**
- Java - programming language
- Gradle - build system
- JUnit - testing framework
- Lombok - library to minimize boilerplate code
- RESTassured - testing 
- Jackson - mapping
- Jersey client - client to interact with OWM


**Instructions:**
- Download from git
- Unzip
- Open terminal
- Navigate to unpacked directory
- When in unpacked directory, navigate to weatherapp. (cd weatherapp)
- Now you are ready to test and run the application.

Running the application will write weather reports to weatherapp/src/main/resources/response
To ask weather reports for cities you have to write them to weatherapp/src/main/resources/request/data.json file
You can see an example of three cities there. One of them is fake and is for the purpose of testing but all of them may be changed as long as the format stays the same(Json, cities separated by commas) 

**Testing:**
- ./gradlew test - LINUX, MACOS
- gradlew test - WINDOWS


**Running:**
- ./gradlew run - LINUX, MACOS
- gradlew run - WINDOWS
