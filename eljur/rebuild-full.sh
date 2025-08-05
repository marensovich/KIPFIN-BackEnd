#!/bin/bash

docker-compose down --volumes --remove-orphans

rm -rf docker/docker-data/mariadb/*
rm -rf docker/docker-data/redis/*

docker-compose up -d --build