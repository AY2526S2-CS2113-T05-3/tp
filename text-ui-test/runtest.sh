#!/usr/bin/env bash

# change to script directory
cd "${0%/*}"

cd ..
./gradlew clean shadowJar

cd text-ui-test

java  -jar $(find ../build/libs/ -mindepth 1 -print -quit) < input.txt > ACTUAL.TXT

cp EXPECTED.TXT EXPECTED-UNIX.TXT
dos2unix EXPECTED-UNIX.TXT ACTUAL.TXT > /dev/null 2>&1

diff EXPECTED-UNIX.TXT <(sed -E -e 's/v2.0 \| .*/v2.0 | <DATE_PLACEHOLDER>/g' \
                                -e 's/Daily quote:".*"/Daily quote:"<QUOTE_PLACEHOLDER>"/g' \
                                -e 's/(Workouts logged[[:space:]]+: )[0-9]+/\1<NUM>/g' \
                                -e 's/(Workouts done[[:space:]]+: )[0-9]+ \/ [0-9]+/\1<NUM> \/ <NUM>/g' \
                                -e 's/(Total exercises[[:space:]]+: )[0-9]+/\1<NUM>/g' \
                                -e 's/[[:space:]]*$//' \
                                ACTUAL.TXT)
if [ $? -eq 0 ]
then
    echo "Test passed!"
    exit 0
else
    echo "Test failed!"
    exit 1
fi
