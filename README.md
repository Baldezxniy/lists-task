# Tasklist

This application helps you organize creating and accessing tasks for users.

You can access Swagger and see all available endpoints by visiting `http://localhost:8080/swagger-ui/index.html`

## Sequence diagram

![Sequence diagram](docs/sequence-diagram.png)

## Component diagram

![Component diagram](docs/component-diagram.png)

Main application communicates with cache (we use Redis), database (we use Postgresql), storage (we use MinIO).

## Class diagram

![Class diagram](docs/class-diagram.png)

We have two main classes - **User** and **Task**.

**User** class represents user in this application. User can login, create and update tasks.

User can have roles - `ROLE_USER` or `ROLE_ADMIN`.

**Task** class represents task in this application. Task can be created by user.

Task can have images.

## Environments

To run this application you need to create `.env` file in root directory with next environments:

- `HOST` - host of Postgresql database
- `POSTGRES_USERNAME` - username for Postgresql database
- `POSTGRES_PASSWORD` - password for Postgresql database
- `POSTGRES_LOCAL_DB` - name of Postgresql database in local
- `DOCKER_DB_CONTAINER_NAME` - name of Postgresql database docker container
- `DOCKER_DB_NAME` - name of Postgresql database in docker container
- `POSTGRES_SCHEMA` - name of Postgresql schema
- `REDIS_HOST` - host of Redis instance
- `REDIS_DOCKER_NAME` - host of Redis instance in docker container
- `REDIS_PASSWORD` - password for Redis
- `JWT_SECRET` - secret string for JWT tokens
- `JWT_ACCESS` - JWT token access time
- `JWT_REFRESH` - JWT token refresh time
- `MINIO_BUCKET` - name of bucket for MinIO
- `MINIO_URL` - URL of MinIO instance
- `MINIO_ACCESS_KEY` - access key of MinIO
- `MINIO_SECRET_KEY` - secret key of MinIO

## Guide
