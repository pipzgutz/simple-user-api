# Simple User API
This is a simple user API that manages users, it can do CRUD operations (Create, Retrieve, Update and Delete) users.
New users can receive email notifications about their registration.

# Developers Notes
To access the swagger UI just go to http://localhost:8080/swagger

# Setting Up Mailtrap

This project uses mail trap to test sending emails.

1. Create an account in https://mailtrap.io
2. After logging in, from the Home page click on Email Testing and check the details on how to integrate using SMTP.
3. Copy the username and password and create environment the variables MAILTRAP_USERNAME and MAILTRAP_PASSWORD respectively.

## Building and Running the Project in Docker

Before running the Docker commands, make sure to create an executable jar file

`
mvn clean package
`

Run the following command

`
docker build -t simpleuserapi:1.0 .
`

Creating a Docker container and running it and exposing the port

`
docker run --name simpleuserapi -p 8080:8080 simpleuserapi:1.0   
`

Do not forget to delete the container if it's not needed anymore

`
docker rm -f simpleuserapi
`

### Alternative Way of Running
Alternatively, you can run it using the docker-compose.yml provided.

After building the Docker image run the following command:

`
docker-compose up -d
`

To stop and delete the container run the following command:

`
docker-compose down
`
