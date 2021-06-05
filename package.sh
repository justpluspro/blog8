#!/bin/bash

#======================================================================
# mvn package script
# default local profile
#
# author: qwli7
# date: 2020-08-12
#======================================================================

PROFILE=$1
if [ -z "$PROFILE" ]; then
    PROFILE=local
fi
echo "profile: ${PROFILE}"
mvn clen package -P${PROFILE} -DskipTests
echo "profile: ${PROFILE}"