#!/bin/bash
set -ex
if [ "${1:0:1}" = '-' ] || [ "${1:0:1}" = '' ]; then
  exec  /usr/local/jdk-17.0.6/bin/java -jar bitMatchOAServer-0.0.1-SNAPSHOT.jar  "$@"
fi

exec "$@"

