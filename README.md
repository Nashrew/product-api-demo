# product-api-demo
Product API Demo App (Interview Project)

Instructions to build and run as a jar (from base directory):
  - $ ./gradlew bootJar
  - $ java -jar ./build/libs/(jarfile)

Alternatively (from base directory):
  - $ ./gradlew bootRun

To run tests (from base directory):
  - $ ./gradlew test

Note - I had to run "$ chmod 755 gradlew" in a fresh ubuntu environment after cloning the repo to avoid permission issues. This was in an AWS C9 environment so YMMV.

Usage:
  - /products/ - get all products
  - /products/{product-id}/ - get specific product
  - /products/book/{inventory-id}/ - book (add/create) a new product using the inventory item specified
  - Important note - the booking endpoint calls out to an inventory API. It will not function unless you are running and pointing to a valid service which will return an inventory object. I have provided a barely-skin-and-bones version of that [here](https://github.com/Nashrew/inventory-api-demo) that you can run if you want to test the functionality while actually running the application(s).

Next steps:
  - Enable Swagger for documentation. I've tried adding it this morning but it's blowing up.
  - Switch from basic manual repo class to actual CRUD repository and actual database. I'd start with H2 to get JPA working and then move to something hosted (RDS or something like that).
  - Improve test coverage. I focused a good bit on wiremock and the interaction with the external inventory service. I think the coverage can be improved for service and more can be added when upgrading the repo.
  - More research on concurrency in Spring - I feel like I'm missing something here based on this stated requirement - "reactive programming preferred, handle concurrency".

Additional notes:
  - My approach was based mostly on this statement: "Must provide an endpoint to “book” a product with an inventory id". I took that to mean that we have a separate service which provides inventory details that we can use to book products. I'm not 100% certain that was the intent, in a real grooming/planning situation I'd probably be asking clarifying questions and also would probably have a better understanding of the architecture to start with.
  - I am very curious how this project / these requirement might mirror some (if any) of the architecture being used by your team. 
  - I don't like how basic my repository class is, but left it in that state since no DB use was stated in the requirements.
  - Wiremock - I had not used this before this project. After learning about it and putting it to use, I see how much value it could add when developing different services in parallel. I hope my implementation of it is at least decent and I'm certainly open to feedback and advice on how to improve in this area.
  - Wiremock - only used in test cases. I am guessing there was no intent for me to mock the requests in the actual service methods. In case you wish to run the app and have a valid inventory service to hit, I threw this thing together this morning: https://github.com/Nashrew/inventory-api-demo
  - Overall I enjoyed this challenge. I learned a few things (wiremock, spring flux - which I want to read more about) and trying to interpret the requirements was a good thought exercise. I'm sure it isn't perfect and may not even be at all what you were looking for, but I hope we get the opportunity to discuss the project and what may be missing that you were looking for (I feel I'm missing something you were looking for when it comes to concurrency).
  - I had lofty thoughts on making one or more branches to do thinks like proper database / JPA usage and dockerization, but didn't have the time and wanted to get this out this morning.
  - Need to do some reading and research around reactive programming.
