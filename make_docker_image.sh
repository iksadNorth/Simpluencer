#!/bin/bash

DOCKER_IMAGE_NAME=iksadnorth/simpluencer-was:1.0.0

./gradlew bootjar
docker build -t $DOCKER_IMAGE_NAME .
docker push $DOCKER_IMAGE_NAME
