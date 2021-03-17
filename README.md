#Web

###Requirements
* Java 11
* MongoDB ( can be run with `docker run -d --rm -p 27017:27017 --name mongodb bitnami/mongodb:latest`)
* Postgres ( can be run with `docker run -d --rm -it --name pg -e "POSTGRES_PASSWORD=postgres" -p 5432:5432 postgres:12`)

###Setup
1. Run backend (via IDE or `./gradlew run`)

###Work
* backend will be available at `http://localhost:8080`
