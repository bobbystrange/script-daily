#!/usr/bin/env bash

function info() {
    echo -e "\033[32m$@\033[0m"
}
function warn() {
    echo -e "\033[33m$@\033[0m"
}
function error() {
    echo -e "\033[31m$@\033[0m"
}

if [[ "$1" = "--help" || "$1" = "-h" ]]; then
    info "Usage: $0 IMAGE_NAME DOCKER_DIR [DOCKERFILE_NAME=Dockerfile]"
    exit 0
fi

IMAGE_NAME=$1
DOCKER_DIR=$2
DOCKERFILE_NAME=$3

[[ -z $DOCKERFILE_NAME ]] && DOCKERFILE_NAME=Dockerfile
if [[ -z $IMAGE_NAME || -z $DOCKER_DIR ]]; then
    error "require at least two argument as IMAGE_NAME and DOCKER_DIR"
    exit 1
fi

# absolute path
DOCKER_DIR=`cd $DOCKER_DIR && pwd`
cd $DOCKER_DIR || (error "You haven no permission to cd $DOCKER_DIR" && exit)


# if no tag
if [[ -z `echo $IMAGE_NAME | grep :` ]]; then
    repository=$IMAGE_NAME
    tag="latest"
else
    repository=`echo $IMAGE_NAME | cut -d: -f1`
    tag=`echo $IMAGE_NAME | cut -d: -f2`
fi


if [[ -n `docker image ls | grep $repository | grep $tag` ]]; then
    warn "$IMAGE_NAME already exists"
    warn "Done"
    exit 1
fi

info "Building docker image $IMAGE_NAME"
docker build -f ./$DOCKERFILE_NAME -t $IMAGE_NAME .
info "Done"
