#!/usr/bin/env bash

function ls-to-settings() {
    if [ -z $1 ]; then dir=`pwd` ;else dir=$1; fi
    settings="$dir/settings.gradle"

    simple_name=${dir##*/}
    if [ ! -f "$settings" ]; then
        project_line="rootProject.name = '$simple_name"
        echo "adding $project_line"
        echo "$project_line\n" >> $settings

    fi

    ls "$dir" | while read f; do
        if [ -d $f ]; then
            include_line="include '$f'"
            echo "adding $include_line"
            echo "$include_line" >> $settings
        fi
    done
}

ls-to-settings $@
