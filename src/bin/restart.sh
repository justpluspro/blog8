#!/bin/bash

#==================================================================
# 项目重启 shell 脚本
# 先调用 shutdown.sh 停服务
# 然后调用 startup.sh 启动服务
#
# author: qwli7
# date: 2020-08-12
#==================================================================

# 项目名称
APPLICATION="${project.artifactId}"
# 项目 jar 包名称
APPLICATION_JAR="${project.build.finalName}.jar"

# 停服
echo "Stopping the ${APPLICATION} application..."
sh shutdown.sh

sleep 2

# 启动服务
echo "Restarting the ${APPLICATION} application..."
sh startup.sh