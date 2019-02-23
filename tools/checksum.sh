#!/bin/bash

LOOKUP_PATH=$1
RESULT_FILE=$2

if [ -f $RESULT_FILE ]; then
  rm $RESULT_FILE
fi
touch $RESULT_FILE

calc_file_hash() {
  echo $(openssl md5 $1 | awk '{print $2}')
}

FILES=()
while read -r -d ''; do
    FILES+=("$REPLY")
done < <(find $LOOKUP_PATH -name 'build.gradle' -type f -print0)

# md5 of all build.gradle files
for FILE in ${FILES[@]}; do
    echo $(calc_file_hash $FILE) >> $RESULT_FILE
done

# sort to make it the same
sort $RESULT_FILE -o $RESULT_FILE