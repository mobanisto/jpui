#!/bin/bash

set -e

DIR=$(dirname $0)

pushd "$DIR" > /dev/null
./gradlew clean installDist setupScripts
popd

"$DIR"/build/setup/install.sh
"$DIR"/build/setup/post-install.sh
