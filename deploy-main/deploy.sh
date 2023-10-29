#!/bin/bash

mkdir -p ~/.ssh

cd ~/server-config
git pull origin main

mkdir -p /home/ubuntu/deploy/zip
cd /home/ubuntu/deploy/zip/

docker compose down --rmi all
docker compose up -d
