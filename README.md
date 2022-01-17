# Workflow Manager
Application that allows creating tasks for workers and managing execution of the tasks
## Full endpoints documentation
- [Swagger remote](https://workflow-manager-server.herokuapp.com/workflow-manager-api/swagger-ui/)
- [Swagger local](http://localhost:8080/workflow-manager-api/swagger-ui/)
## Login Details:
Credentials for pre-created users
- Coordinator:
  - Username: coordinator1
  - Password: coordinator1
- Manager:
  - Username: manager1
  - Password: manager1
- Worker:
  - Username: worker1
  - Password: worker1
## Using hosted solution:
1. To wake up service open [link](https://workflow-manager-server.herokuapp.com/workflow-manager-api/swagger-ui/) and wait till page loads
2. Later app will be available at [link](https://workflow-manager-server.herokuapp.com/workflow-manager-api/swagger-ui/)
## Launching project locally:
1. Download and install Java (at lease 1.8) and set JAVA_HOME
2. Download and install Postgres (with password 12345) and start at port 1234
3. Download sources
4. Open command line in root folder
5. Execute `mvnw spring-boot:run`
6. App will be available at [link](http://localhost:8080/workflow-manager-api/swagger-ui/)


