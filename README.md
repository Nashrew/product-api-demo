# product-api-demo
**Product API Demo App** - This was an interview project which I am converting into to a project I can tinker with / discuss during other technical interviews

Instructions to build and run as a jar (from base directory):
  - $ ./gradlew bootJar
  - $ java -jar ./build/libs/(jarfile)

Alternatively (from base directory):
  - $ ./gradlew bootRun

To run tests (from base directory):
  - $ ./gradlew test

Note - I had to run "$ chmod 755 gradlew" in a fresh ubuntu environment after cloning the repo to avoid permission issues. This was in an AWS C9 environment so YMMV.

**Usage:**
  - /products/ - get all products
  - /products/{product-id}/ - get specific product
  - /products/book/{inventory-id}/ - book (add/create) a new product using the inventory item specified
  - Important note - the booking endpoint calls out to an inventory API. It will not function unless you are running and pointing to a valid service which will return an inventory object. I have provided a barely-skin-and-bones version of that [here](https://github.com/Nashrew/inventory-api-demo) that you can run if you want to test the functionality while actually running the application(s).

**Next steps:**
  - Improve the 'booking' method. I think I tied it to much to the description of the requirement I was given. I'd like to change it so that 'booking' (creating) a product is simply a post to /products but requires an inventory-id as part of the request body.
  - Enable Swagger for documentation. I tried adding it on 6/8 but it's blowing up.
  - Improve test coverage? I focused a good bit on wiremock and the interaction with the external inventory service. I think the coverage can be improved for service and more can be added when upgrading the repo.
  - Docker / Terraform if I I'm feeling fancy? Could be a cool opportunity to do some IAC for two different services that need to talk to each other, need to find some sources to read about that. (even with only docker, a shell script to run both services locally might be nice)

**Additional notes:**
  - My approach was based mostly on this statement: "Must provide an endpoint to “book” a product with an inventory id". I took that to mean that we have a separate service which provides inventory details that we can use to book products. I'm not 100% certain that was the intent, in a real grooming/planning situation I'd probably be asking clarifying questions and also would probably have a better understanding of the architecture to start with.
  - I am very curious how this project / these requirement might mirror some (if any) of the architecture being used by your team. 
  - Wiremock - I had not used this before this project. After learning about it and putting it to use, I see how much value it could add when developing different services in parallel. I hope my implementation of it is at least decent and I'm certainly open to feedback and advice on how to improve in this area.
  - Wiremock - only used in test cases. I am guessing there was no intent for me to mock the requests in the actual service methods. In case you wish to run the app and have a valid inventory service to hit, I threw this thing together this morning: https://github.com/Nashrew/inventory-api-demo

----

**Requirements given:**

** No DB required - use mock services for upstream systems

**General Requirements**
  - Must be submitted on an online git repository. GitHub, GitLab…etc
  - Must be written with spring-boot
  - Must be runnable via a jar or war
  - Must use wiremock to mock upstream service calls
  - Must be java (or kotlin), java 11 (reactive programming preferred, handle concurrency)
  - Should have unit tests

**Functional Requirements**
  - Must provide an endpoint to retrieve products
  - Must provide an endpoint to retrieve a single product
  - Must provide an endpoint to “book” a product with an inventory id

**Products should have the following info:**
  - Content
  - Experience details
  - Inventory
