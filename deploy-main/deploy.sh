#!/bin/bash

mkdir -p ~/.ssh

# ~/server-config 경로에 있는 git pull 실행
cd ~/server-config
git pull origin main

mkdir -p /home/ubuntu/deploy/zip
cd /home/ubuntu/deploy/zip/

docker compose down --rmi all
docker compose up -d
