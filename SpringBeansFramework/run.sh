#!/bin/bash
##################### Begin of Script #############################
#Clean all the agent directory.
clean_dir_info() {
rm -rf cookies.txt
rm -rf index.html
rm -rf agent.zip
rm -rf machineagent.zip
rm -rf databaseagent.tar.gz
rm -rf /mnt/dockerimage
rm -rf /mnt/dockerimage/javaagent
rm -rf /mnt/dockerimage/machineagent
rm -rf /mnt/dockerimage/databaseagent
rm -rf /mnt/dockerimage/server
rm -rf /mnt/dockerimage/server/tomcatone
rm -rf /mnt/dockerimage/server/tomcattwo
rm -rf /mnt/dockerimage/configfile
rm -rf /mnt/wardownload
rm -rf /mnt/unzipserver
rm -rf /mnt/tempcopywar
rm -rf /mnt/database
}
#Create required directories.
create_dir() {
mkdir /mnt/dockerimage/
}
#ENV setup for JAVA, TOMCAT one and TOMCAT two.
java_setup() {
export JAVA_HOME=/opt/jdk/jdk1.8.0_05
export PATH=$JAVA_HOME/bin:$PATH
SHUTDOWN_WAIT=20
echo "Java Home is set to:": $JAVA_HOME
}
tomcat_server_configure() {
#Tomcat One and Tomcat Two folder.
mkdir /mnt/dockerimage/server
mkdir /mnt/dockerimage/server/tomcatone
mkdir /mnt/dockerimage/server/tomcattwo
TOMCAT_HOME_ONE=/mnt/dockerimage/server/tomcatone
TOMCAT_HOME_TWO=/mnt/dockerimage/server/tomcattwo
#Configuration file path
mkdir /mnt/dockerimage/configfile
CONFIG_FILE=/mnt/dockerimage/configfile
mkdir /mnt/wardownload
WAR_DOWNLOAD=/mnt/wardownload
mkdir /mnt/unzipserver
UNZIP_TAR=/mnt/unzipserver
wget https://s3.amazonaws.com/appdynamics-docker-artifacts/apache-tomcat-7.0.55/apache-tomcat-7.0.55.tar.gz -O $WAR_DOWNLOAD/apache-tomcat-7.0.55.tar.gz
tar -zxvf $WAR_DOWNLOAD/apache-tomcat-7.0.55.tar.gz -C $UNZIP_TAR
cp -r $UNZIP_TAR/apache-tomcat-7.0.55/* $TOMCAT_HOME_ONE
cp -r $UNZIP_TAR/apache-tomcat-7.0.55/* $TOMCAT_HOME_TWO
wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-sh-files/springbeans/catalina1.sh -O $CONFIG_FILE/catalina1.sh
dos2unix $CONFIG_FILE/catalina1.sh
wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-sh-files/springbeans/catalina2.sh -O $CONFIG_FILE/catalina2.sh
dos2unix $CONFIG_FILE/catalina2.sh
wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-xml-files/server2.xml -O $CONFIG_FILE/server2.xml
dos2unix $CONFIG_FILE/server2.xml
}
#Download agent from download repository.
agent_download() {
mkdir /mnt/dockerimage/javaagent
export JAVA_AGENT=/mnt/dockerimage/javaagent
cd $JAVA_AGENT
echo "****************************************************************"
echo "Please Sign In to download agent..."
echo "Email ID/UserName : "
read USER_NAME
stty -echo
echo "Password : "
read PASSWORD
stty echo
if [ "$USER_NAME" != "" ] && [ "$PASSWORD" != "" ];
then
# login download.appdynamics.com Or creat login session using wget
wget --save-cookies cookies.txt --post-data "username=$USER_NAME&password=$PASSWORD" --no-check-certificate https://login.appdynamics.com/sso/login/
# download latest agent
echo "Downloading Agent..."
wget --load-cookies cookies.txt http://download.appdynamics.com/onpremise/public/archives/3.9.1.0/AppServerAgent-3.9.1.0.zip -O agent.zip
sleep 5
echo "Done with agent download..."
sleep 2
echo "Unzipping agent..."
unzip agent.zip -d $JAVA_AGENT/
sleep 2
echo "Done with agent unzip.."
springbeans_app
else
echo "UserName or Password missing"
fi
}
#Restart MYSQL server.
mysql_start_stop() {
/etc/init.d/mysql restart;
}
#Configure catalina.sh file with Port number,Application name,Tier Name,Node Name and point to java agent
catalina_server_setup() {
cp $CONFIG_FILE/server2.xml $TOMCAT_HOME_TWO/conf/server.xml
cp $CONFIG_FILE/catalina1.sh $TOMCAT_HOME_ONE/bin/catalina.sh
cp $CONFIG_FILE/catalina2.sh $TOMCAT_HOME_TWO/bin/catalina.sh
cd $TOMCAT_HOME_ONE/bin/
sed -i "s/HOSTNAME/$INPUT_HOST_NAME/g" catalina.sh
cd $TOMCAT_HOME_TWO/bin/
sed -i "s/HOSTNAME/$INPUT_HOST_NAME/g" catalina.sh
cd $TOMCAT_HOME_ONE/bin/
sed -i "s/PORTNO/$INPUT_PORT/g" catalina.sh
cd $TOMCAT_HOME_TWO/bin/
sed -i "s/PORTNO/$INPUT_PORT/g" catalina.sh
cd $TOMCAT_HOME_ONE/bin/
sed -i "s/FRAMEWORKNAME/"$FRAMEWORK_NAME"/g" catalina.sh
cd $TOMCAT_HOME_TWO/bin/
sed -i "s/FRAMEWORKNAME/"$FRAMEWORK_NAME"/g" catalina.sh
}
#Get the pid of the running tomcat's.
tomcat_pid() {
echo `ps aux | grep org.apache.catalina.startup.Bootstrap | grep -v grep | awk '{ print $2 }'`
}
#Restart the tomcat.
tomcat_restart() {
pid=$(tomcat_pid)
if [ -n "$pid" ]
then
echo "Tomcat is already running (pid: $pid)"
echo "Killing running tomcat (pid: $pid)"
# Kill all the running tomcat process
ps xu | grep tomcat | grep -v grep | awk '{ print $2 }' | xargs kill -9
sleep 2
echo "Killed running tomcat..."
# Start tomcat one
echo "Starting tomcat 1"
$TOMCAT_HOME_ONE/bin/startup.sh
sleep 5
# Start tomcat two
echo "Starting tomcat 2"
$TOMCAT_HOME_TWO/bin/startup.sh
echo "Tomcat PID After STOP n START :" `ps aux | grep org.apache.catalina.startup.Bootstrap | grep -v grep | awk '{ print $2 }'`
sleep 2
else
# start tomcat one
echo "Starting tomcat 1"
$TOMCAT_HOME_ONE/bin/startup.sh
sleep 5
# Start tomcat two
echo "Starting tomcat 2"
$TOMCAT_HOME_TWO/bin/startup.sh
echo "Tomcat PID After RESTART :" `ps aux | grep org.apache.catalina.startup.Bootstrap | grep -v grep | awk '{ print $2 }'`
sleep 2
fi
}
#Generate load with DATA and URL as parameter
generate_load_data() {
sleep 15
echo "Please wait generating load for "$FRAMEWORK_NAME" application......."
for (( i=1; i <=20; i++ ))
do
curl -d $DATA $URL
sleep 5
done
echo "Done with load generation for "$FRAMEWORK_NAME" application........."
}
#Generate load method with URL as parameter
generate_load_url() {
sleep 15
echo "Please wait generating load for "$FRAMEWORK_NAME" application......."
for (( i=1; i <=10; i++ ))
do
curl $URL
sleep 5
done
echo "Done with load generation for "$FRAMEWORK_NAME" application........."
}
##################### Frame Work ##################################
springbeans_app() {
FRAMEWORK_NAME="SpringBeans"
URL="http://localhost:8080/SpringBeansClient/client/"
mkdir /mnt/tempcopywar
wget https://s3.amazonaws.com/appdynamics-docker-artifacts/war-files/springbeans/SpringBeansClient.war -O /mnt/tempcopywar/SpringBeansClient.war
wget https://s3.amazonaws.com/appdynamics-docker-artifacts/war-files/springbeans/SpringBeans.war -O /mnt/tempcopywar/SpringBeans.war
tomcat_server_configure
catalina_server_setup $FRAMEWORK_NAME
cp /mnt/tempcopywar/SpringBeansClient.war $TOMCAT_HOME_ONE/webapps/
cp /mnt/tempcopywar/SpringBeans.war $TOMCAT_HOME_TWO/webapps/
mkdir /mnt/database
wget https://s3.amazonaws.com/appdynamics-docker-artifacts/SQL+files/spring.sql -O /mnt/database/spring.sql
dos2unix /mnt/database/spring.sql
mysql_start_stop
mysql -uroot -proot "" < /mnt/database/spring.sql
tomcat_restart
generate_load_url $URL $FRAMEWORK_NAME
}

###############################################################################
#Action to be performed before agent download.
start() {
clean_dir_info
create_dir
java_setup
agent_download
return 0
}
# lines to read controller ip and port from command prompt
if [ "$1" != "" ] && [ "$2" != "" ];
then
INPUT_HOST_NAME="$1"
INPUT_PORT="$2"
INPUT_HOST_NAME=`echo "$1" | sed -e "s/\/*$//" `
INPUT_HOST_NAME=`echo $INPUT_HOST_NAME | sed -e 's/^https*\:\/\///' `
echo "File Name: $0"
echo "Controller IP : $1"
echo "Controller port : $2"
echo "Total Number of Parameters : $#"
echo " HOST_NAME :$INPUT_HOST_NAME"
echo " PORT :$INPUT_PORT"
start
else
echo "Controller IP or Controller Port missing"
fi
exit 0
############################## End of Script ################################ 