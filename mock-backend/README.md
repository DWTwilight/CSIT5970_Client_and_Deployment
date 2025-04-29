# Readme

## Build Docker Image

```sh
sudo docker build -t local/csit5970-mock-backend:latest .
```

## Run with Docker

```sh
sudo docker run -it -p 8080:8080 --rm local/csit5970-mock-backend:latest
```
