#!/bin/bash

# 项目名称
APPLICATION="${project.artifactId}"
# 项目名称启动 jar
APPLICATION_JAR="${project.build.finalName}.jar"

# bin目录绝对路径
BIN_PATH=$(cd `dirname $0`; pwd)
# 进入bin目录
cd `dirname $0`

# 返回到上一级项目根目录路径
cd ..

# 打印项目根目录绝对路径
# `pwd` 执行系统命令并获得结果
BASE_PATH=`pwd`

# 外部配置文件绝对目录,如果是目录需要/结尾，也可以直接指定文件
# 如果指定的是目录,spring则会读取目录中的所有配置文件
CONFIG_DIR=${BASE_PATH}"/config/"

PIDS=`ps -ef | grep java | grep "$CONFIG_DIR" | awk '${print $2}'`
if [ -z "$PIDS" ]; then
    echo "ERROR: The $APPLICATION does not started!"
    exit 1
fi


echo -e "Stopping the ${APPLICATION} ...\c"
for PID in $PIDS; do
    kill $PID > /dev/null/ 2>&1
done

COUNT=0
while [ $COUNT -lt 1 ]; do
    echo -e ".\c"
    sleep 1
    COUNT=1
    for PID in $PIDS ; do
        PID_EXIST=`ps -f -p $PID | grep java`
        if [ -n "$PID_EXIST" ]; then
            COUNT=0
            break
        fi
    done
done

echo "OK!"
echo "PID: $PIDS"
