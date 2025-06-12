FROM ubuntu:latest
LABEL authors="osx"

ENTRYPOINT ["top", "-b"]