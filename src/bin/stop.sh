#!/bin/bash

# 项目名称
APPLICATION="${project.artifactId}"
# 项目名称启动 jar
APPLICATION_JAR="${project.build.finalName}.jar"

# 通过项目名称找到 pid，然后执行 kill -9 pid
PID=$(ps -ef | grep "${APPLICATION_JAR}" | grep -v grep | awk '{ print $2}')

if [[ -z "$PID" ]]
then
    echo ${APPLICATION} is already stopped
else
    echo kill ${PID}
    kill -9 ${PID}
    echo ${APPLICATION} stopped successfully
fi
