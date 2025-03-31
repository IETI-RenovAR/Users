#!/bin/bash

sudo docker stop users
git pull
sudo docker rm users
sudo docker build -t users .
sudo docker run -d -p 8080:8080 --name users users
