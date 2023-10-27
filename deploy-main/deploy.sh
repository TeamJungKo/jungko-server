#!/bin/bash

mkdir -p /home/ubuntu/deploy/zip
cd /home/ubuntu/deploy/zip/

docker compose down --rmi all
docker compose up -d
