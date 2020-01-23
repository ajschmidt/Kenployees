#! /bin/sh
./stop.sh
docker container run -d -it --name build-start -p 8080:8080 --rm -v "$(pwd)"/kenployee:/kenployee maven-dev
docker container logs -f build-start
