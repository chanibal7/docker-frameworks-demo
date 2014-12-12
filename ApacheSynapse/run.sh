#!/bin/bash

#Setup java home
java_setup() {
  export JAVA_HOME=/opt/jdk/jdk1.8.0_05
  export PATH=$JAVA_HOME/bin:$PATH
  SHUTDOWN_WAIT=20
  echo "Java Home is set to:": $JAVA_HOME
}

#Clean directories
clean_dir_info() {
  cd /mnt/dockerdemo
  rm -rf cookies.txt
  rm -rf index.html
  rm -rf agent.zip
  rm -rf machineagent.zip
  rm -rf databaseagent.tar.gz
  rm -rf /mnt/dockerdemo/javaagentone
  rm -rf /mnt/dockerdemo/javaagenttwo
  rm -rf /mnt/dockerdemo/machineagent
  rm -rf /mnt/dockerdemo/databaseagent
}

#Download and configure java agent
agent_download() {
  cd /mnt/dockerdemo/
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
  wget --save-cookies cookies.txt  --post-data "username=$USER_NAME&password=$PASSWORD" --no-check-certificate https://login.appdynamics.com/sso/login/
  # download latest agent
  echo "Downloading Agent..."
  wget --load-cookies cookies.txt http://download.appdynamics.com/onpremise/public/archives/3.9.1.0/AppServerAgent-3.9.1.0.zip -O agent.zip
  sleep 5
  echo "Done with agent download..."
  sleep 2
  echo "Unzipping agent..."
  
  mkdir /mnt/dockerdemo/javaagentone
  mkdir /mnt/dockerdemo/javaagenttwo
  
  export AGENT_DIR_ONE=/mnt/dockerdemo/javaagentone
  export AGENT_DIR_TWO=/mnt/dockerdemo/javaagenttwo
  
  unzip agent.zip -d $AGENT_DIR_ONE/
  unzip agent.zip -d $AGENT_DIR_TWO/
  
  cp /mnt/tempdircopy/controller-info-client.xml $AGENT_DIR_ONE/conf/controller-info.xml
  cd $AGENT_DIR_ONE/conf/
  sed -i "s/HOST_NAME/$INPUT_HOST_NAME/g" controller-info.xml
  sed -i "s/PORT_NO/$INPUT_PORT/g" controller-info.xml
  
  cp /mnt/tempdircopy/controller-info-server.xml $AGENT_DIR_TWO/conf/controller-info.xml
  cd $AGENT_DIR_TWO/conf/
  sed -i "s/HOST_NAME/$INPUT_HOST_NAME/g" controller-info.xml
  sed -i "s/PORT_NO/$INPUT_PORT/g" controller-info.xml
  
  sleep 2
  echo "Done with agent unzip.."
  machine_agent_download
  database_agent_download
  after_agent_download
else
  echo "UserName or Password missing"
 fi
}

#Download and configure machine agent
machine_agent_download() { 
  read -p "Would you like to download machine agent? <y/N> " prompt
 if [[ $prompt == "y" || $prompt == "Y" || $prompt == "yes" || $prompt == "Yes" ]]
  then
   mkdir /mnt/dockerdemo/machineagent
   cd /mnt/dockerdemo/machineagent
   export MACHINE_AGENT_DIR=/mnt/dockerdemo/machineagent
   wget --save-cookies cookies.txt  --post-data "username=$USER_NAME&password=$PASSWORD" --no-check-certificate https://login.appdynamics.com/sso/login/
   echo "Downloading Machine Agent..."
   wget --load-cookies cookies.txt http://download.appdynamics.com/onpremise/public/archives/3.9.1.0/MachineAgent-3.9.1.0.zip -O machineagent.zip
   echo "Done with machine agent download..."
   sleep 2
   echo "Unzipping machine agent..."
   unzip machineagent.zip -d $MACHINE_AGENT_DIR/
   echo "Done with machine agent unzip..."
   cp /mnt/tempdircopy/machine-controller-info.xml $MACHINE_AGENT_DIR/conf/controller-info.xml
   cd $MACHINE_AGENT_DIR/conf
   sed -i "s/HOSTNAME/$INPUT_HOST_NAME/g" controller-info.xml
   sed -i "s/PORTNO/$INPUT_PORT/g" controller-info.xml
   cd /mnt/dockerdemo/machineagent
   nohup java -jar machineagent.jar &
 else
  return 
 fi
} 

#Download database agent
database_agent_download() {
  read -p "Would you like to download DataBase agent? <y/N> " prompt
 if [[ $prompt == "y" || $prompt == "Y" || $prompt == "yes" || $prompt == "Yes" ]]
  then
    cd /mnt/dockerdemo/
    wget --save-cookies cookies.txt  --post-data "username=$USER_NAME&password=$PASSWORD" --no-check-certificate https://login.appdynamics.com/sso/login/
    echo "Downloading Database Agent..."
    wget --load-cookies cookies.txt http://download.appdynamics.com/onpremise/public/latest/AppD-Database-Linux64.tar.gz -O databaseagent.tar.gz
    echo "Done with dbagent download..."
    sleep 2
   echo "Extracting  database agent..."
   mkdir /mnt/dockerdemo/databaseagent
   export DATABASE_AGENT_DIR=/mnt/dockerdemo/databaseagent
   tar -xvzf databaseagent.tar.gz -C $DATABASE_AGENT_DIR
   echo "Done with Database agent extract..."
 else
  return 
 fi
 }

#Run synapse engine
Synapse_restart() {
ps xu | grep synapse-2.1.0 | grep -v grep | awk '{ print $2 }' | xargs kill -9
cd /synapse-2.1.0/
cp /mnt/tempdircopy/sample-server-wrapper.conf /synapse-2.1.0/repository/conf/sample-server-wrapper.conf
cp /mnt/tempdircopy/build.xml /synapse-2.1.0/samples/axis2Client/build.xml
cp /mnt/tempdircopy/axis2server.sh /synapse-2.1.0/samples/axis2Server/axis2server.sh

cd /synapse-2.1.0/samples/axis2Server
nohup ./axis2server.sh &

cd /synapse-2.1.0/bin
nohup ./synapse.sh -sample 0 &

cd /synapse-2.1.0/samples/axis2Client
ant stockquote -Daddurl=http://localhost:9000/services/SimpleStockQuoteService -Dtrpurl=http://localhost:8280 -Dmode=quote -Dsymbol=IBM
}

#Method calls after javaagent download
after_agent_download() {
  Synapse_restart
}

#Initial method execution 
start() {
  clean_dir_info
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

