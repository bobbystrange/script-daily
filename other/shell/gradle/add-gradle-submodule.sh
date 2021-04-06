#!/usr/bin/env bash

function add-gradle-submodule() {
    if [ -z $1 ]; then
        echo "please specified a sub module name!"
        return 1
    fi

    name=$1
    mkdir -p $name/src/main/java
    mkdir -p $name/src/main/resources
    touch $name/build.gradle

    echo "include '$name'" >> settings.gradle
    echo "done."
}

# don't call it via source the script
if [ $# != 0 ]; then
    add-gradle-submodule $@
fi

