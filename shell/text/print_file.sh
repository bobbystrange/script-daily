#!/usr/bin/env bash

# recurseDir dir func
function recurseDir() {
    echo "performing: recurseDir $1 $2"
    files=`ls -a $1`
    echo "files: $files"
    for f in $files; do
      echo "foreach $f"
      [[ $f = '.' || $f = '..' ]] && continue
      new_f="$1/$f"
      echo "new file: $new_f"

      if [ -f $new_f ]; then
        echo "performing: $2 $new_f"
        $2 $new_f
      elif [ -d $new_f ]; then
        echo "try to performe: recurseDir $new_f $2"
        recurseDir $new_f $2
      else
        echo "skipping $new_f"
      fi

    done
}

function grep_file_when_size_over_1M() {
    f=$1
    M_1=$((1024*1024))
    size=`ls -l $f | awk '{print $5}'`
    if [[ $size -gt $M_1 ]]; then
        printf "%4d Bytes    %s\n" $size $f
    fi
}

recurseDir $1 grep_file_when_size_over_1M
