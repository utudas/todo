1. Service description and assumptions
   This is a simple todo service. It includes creating, getting and updating todos functionalities. Endpoints are as below:
   
   POST /todo
   This endpoint creates a todo. In the request body, ID parameter shouldn't be supplied.

   GET /todo
   This endpoint fetches list of todos which has status NOT_DONE

   GET /todo/{id}
   This endpoint fetches a todo which has id as passed in {id} parameter.

   GET /todo/status/{flag}
   This endpoint fetches all todos regardless of their statuses. Naming this endpoint in /status/{flag} format for future accomodation of different status todo fetching including all todos. 

   PUT /todo
   This endpoint updates a todo. In the request body, ID parameter is must.

2. There is a schedular to update the PAST_DUE item status. 
   It is called TodoSchedular. It runs at 12:00 AM every day.

3. Tech stack used (runtime environment, frameworks, key libraries)
   Springboot, lombok, Mockito, JUnit, h2

4. How to build the service:
   a. Clone the git repo.
   b. Navigate to the cloned folder and issue below command:
      mvn clean package

5. How to run unit tests:
   Navigate to the cloned folder and issue below command:
   mvn test

   Unit tests are written only for controller and service class
   
6. How to run the service locally:
   Navigate to the cloned folder and issue below command:
   docker-compose up

7. Sample postman tests are included. It is placed under the folder src/test/java.
   It is called ss-todo docker.postman_collection.json
   One can import it and test through postman.