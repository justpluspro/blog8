#! /bin/bash

#======================================================================
# 项目启动 shell 脚本
# boot 目录: spring boot jar包
# config 目录: 配置文件目录
# logs 目录: 项目运行日志目录
# logs/startup.log: 记录启动日志
# logs/back 目录: 项目运行日志备份目录
# nohup 后台运行
#
# author: qwli7
# date: 2020-08-17
#======================================================================

# 项目名称
APPLICATION="${project.artifactId}"
# jar 名称
APPLICATION_JAR="${project.build.finalName}.jar"

SERVER_PORT="${server.port}"

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

# 进程ID
PIDS=`ps -f | grep java | grep "${CONFIG_DIR}" | awk '{ print $2 }'`
if [ -n "$PIDS" ]; then
    echo "ERROR: The ${APPLICATION} already started!"
    echo "PID: $PIDS"
    exit 1
fi

#if [ -n "$SERVER_PORT" ]; then
#    SERVER_PORT_COUNT=`netstat -tln | grep $SERVER_PORT | wc -l`
#    if [ $SERVER_PORT_COUNT -gt 0 ]; then
#        echo "ERROR: The $SERVER_NAME port $SERVER_PORT already used!"
#        exit 1
#    fi
#fi

# 项目日志输出绝对路径
LOG_DIR=${BASE_PATH}"/logs"
LOG_FILE="${APPLICATION}.log"
LOG_PATH="${LOG_DIR}/${LOG_FILE}"
# 日志备份目录
LOG_BAK_DIR="${LOG_DIR}/bak/"

# 项目启动日志输出绝对路径
#LOG_STARTUP_PATH="${LOG_DIR}/startup.log"

# 当前时间
NOW=`date +'%Y-%m-%m-%H-%M-%S'`
NOW_PRETTY=`date +'%Y-%m-%m %H:%M:%S'`

# 启动日志
echo -e "================================================ ${NOW_PRETTY} ================================================\n"

# 获取应用端口号
#SERVER_PORT=`sed -nr '/port: [0-9]+/ s/.*port: +([0-9]+).*/\1/p' ${CONFIG_DIR}/application.yml`
#STARTUP_LOG="${STARTUP_LOG}Occupy Port: ${SERVER_PORT}"

# 如果logs文件夹不存在,则创建文件夹
if [[ ! -d "${LOG_DIR}" ]]; then
  mkdir "${LOG_DIR}"
fi

# 如果logs/back文件夹不存在,则创建文件夹
#if [[ ! -d "${LOG_BACK_DIR}" ]]; then
#  mkdir "${LOG_BACK_DIR}"
#fi

# 如果项目运行日志存在,则重命名备份
if [[ -f "${LOG_PATH}" ]]; then
	mv ${LOG_PATH} "${LOG_BAK_DIR}/${APPLICATION}_bak_${NOW}.log"
fi

# 创建新的项目运行日志
echo "" > ${LOG_PATH}



# 如果项目启动日志不存在,则创建,否则追加
#echo "${STARTUP_LOG}" >> ${LOG_STARTUP_PATH}

#==========================================================================================
# JVM Configuration
# -Xmx256m:设置JVM最大可用内存为256m,根据项目实际情况而定，建议最小和最大设置成一样。
# -Xms256m:设置JVM初始内存。此值可以设置与-Xmx相同,以避免每次垃圾回收完成后JVM重新分配内存
# -Xmn512m:设置年轻代大小为512m。整个JVM内存大小=年轻代大小 + 年老代大小 + 持久代大小。
#          持久代一般固定大小为64m,所以增大年轻代,将会减小年老代大小。此值对系统性能影响较大,Sun官方推荐配置为整个堆的3/8
# -XX:MetaspaceSize=64m:存储class的内存大小,该值越大触发Metaspace GC的时机就越晚
# -XX:MaxMetaspaceSize=320m:限制Metaspace增长的上限，防止因为某些情况导致Metaspace无限的使用本地内存，影响到其他程序
# -XX:-OmitStackTraceInFastThrow:解决重复异常不打印堆栈信息问题
#==========================================================================================
JAVA_MEM_OPTS=""
BITS=`java -version 2>&1 | grep -i 64-bit`
if [ -n "$BITS" ]; then
    JAVA_MEM_OPTS=" -server -Xms512m -Xmx512m -Xmn128m -XX:MetaspaceSize=64m -XX:MaxMetaspaceSize=256m -XX:+HeapDumpOnOutOfMemoryError "
    else
    JAVA_MEM_OPTS="-server -Xms512m -Xmx512m -Xmn128m -XX:MetaspaceSize=64m -XX:MaxMetaspaceSize=256m -XX:+HeapDumpOnOutOfMemoryError "
fi

#=======================================================
# 将命令启动相关日志追加到日志文件
#=======================================================

# 输出项目名称
echo -e "application name: ${APPLICATION}\n"
# 输出jar包名称
echo -e "application jar name: ${APPLICATION_JAR}\n"
# 输出项目根目录
echo -e "application root path: ${BASE_PATH}\n"
# 输出项目bin路径
echo -e "application bin path: ${BIN_PATH}\n"
# 输出项目config路径
echo -e "application config path: ${CONFIG_DIR}\n"
# 打印日志路径
echo -e "application log path: ${LOG_PATH}\n"
# 打印JVM配置
echo -e "application JAVA_MEM_OPTS : ${JAVA_MEM_OPTS}\n"


# 打印启动命令
echo -e "application startup command: nohup java ${JAVA_MEM_OPTS} -Dspring.config.location=${CONFIG_DIR}application.properties -Dlogging.location=${CONFIG_DIR}logback-spring.xml > ${LOG_PATH} 2>&1 &\n"


#======================================================================
# 执行启动命令：后台启动项目,并将日志输出到项目根目录下的logs文件夹下
#======================================================================
echo -e "Starting the {$APPLICATION}....\n"
nohup java ${JAVA_MEM_OPTS} -jar ${BASE_PATH}/lib/${APPLICATION_JAR} -Dspring.config.location=${CONFIG_DIR} > ${LOG_PATH} 2>&1 &

echo -e "OK!\n"


# 进程ID
PIDS=`ps -f | grep java | grep "${CONFIG_DIR}" | awk '{ print $2 }'`

echo -e "PIDS: $PIDS"
echo -e "Startup log: ${LOG_PATH}"

tail -f ${LOG_PATH}
# 启动日志追加到启动日志文件中
#echo -e ${STARTUP_LOG} >> ${LOG_STARTUP_PATH}
# 打印启动日志
#echo -e ${STARTUP_LOG}

# 打印项目日志
#tail -f ${LOG_PATH}
