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
export JAVA_HOME=/usr/lib/jvm/java-8-oracle
export PATH=$JAVA_HOME/bin:$PATH

SHUTDOWN_WAIT=20
echo "Java Home is set to:": $JAVA_HOME
}

framework_call() {
#User prompted for particular framework selection
clear
echo "#################################"
echo "  Supported Framework by Docker  "
echo "#################################"
echo "1.AngularJS"
echo "2.Servlet"
echo "3.Grails"
echo "4.Hibernate"
echo "5.Spring"
echo "6.MongoDB"
echo "7.ActiveMQ"
echo "8.JSF"
echo "9.MySql"
echo "10.RabbitMQ"
echo "11.PostgresSQL"
echo "#########################"

echo "Input particular framework number :"
read FRAME_WORK_ID

re='^[0-9]+$'
if [[  "$FRAME_WORK_ID" =~ $re ]] ;
then

case "$FRAME_WORK_ID" in
1) echo "AngularJS Framework"
    angularjs_app
   ;;
2) echo "Servlet Service"
    servlet_app
   ;;
3) echo "Grails Framework"
    grails_app
   ;;
4) echo "Hibernate Framework"
    hibernate_app
   ;;
5) echo "Spring Support"
    springbeans_app
   ;;
6) echo "MongoDB support"
	mongodb_app
	;;
7) echo "ActiveMQ"
    activemq_app
   ;; 
8) echo "JSF"
    jsf_app
   ;; 
9) echo "MySQL"
    mysql_app
   ;;
10) echo "RabbitMQ"
    rabbitmq_app
   ;;
11) echo "PostgresSQL"
	postgressql_app
   ;;
*) echo "Not a valid framework support"  
   ;;
esac  
 else
  echo "Please select any one of the listed framework support"
 fi
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

  wget http://archive.apache.org/dist/tomcat/tomcat-7/v7.0.55/bin/apache-tomcat-7.0.55.tar.gz -O  $WAR_DOWNLOAD/apache-tomcat-7.0.55.tar.gz
  
  tar -zxvf $WAR_DOWNLOAD/apache-tomcat-7.0.55.tar.gz -C $UNZIP_TAR
  cp -r $UNZIP_TAR/apache-tomcat-7.0.55/* $TOMCAT_HOME_ONE
  cp -r $UNZIP_TAR/apache-tomcat-7.0.55/* $TOMCAT_HOME_TWO
  
 wget http://ec2-54-204-218-30.compute-1.amazonaws.com:8080/examples/servlets/dockerconfigfiles/catalina1.sh -O $CONFIG_FILE/catalina1.sh
 dos2unix $CONFIG_FILE/catalina1.sh
 wget http://ec2-54-204-218-30.compute-1.amazonaws.com:8080/examples/servlets/dockerconfigfiles/catalina2.sh -O $CONFIG_FILE/catalina2.sh
 dos2unix $CONFIG_FILE/catalina2.sh
 wget http://ec2-54-204-218-30.compute-1.amazonaws.com:8080/examples/servlets/dockerconfigfiles/server2.xml -O $CONFIG_FILE/server2.xml
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
  wget --save-cookies cookies.txt  --post-data "username=$USER_NAME&password=$PASSWORD" --no-check-certificate https://login.appdynamics.com/sso/login/
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
  framework_call
else
  echo "UserName or Password missing"
 fi
}

#Restart MYSQL server.
mysql_start_stop() {
  /etc/init.d/mysql restart;
}

#Restart RabbitMQ server.
rabbit_start_stop() {
sudo service rabbitmq-server restart
}

#Restart MOngoDB server.
mongodb_start_stop() {
  sudo chown -R mongodb:mongodb /var/lib/mongodb/.
  sudo chown -R mongodb:mongodb /var/log/mongodb.log
  mongod --fork --dbpath /var/lib/mongodb/ --smallfiles --logpath /var/log/mongodb.log --logappend
}

#Restart PostgresSQL server.
postgressql_start_stop() {
  /etc/init.d/postgresql restart;
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
    echo "Tomcat PID After STOP n START :"  `ps aux | grep org.apache.catalina.startup.Bootstrap | grep -v grep | awk '{ print $2 }'`
        sleep 2
  else
    # start tomcat one
    echo "Starting tomcat 1"
    $TOMCAT_HOME_ONE/bin/startup.sh
        sleep 5
    # Start tomcat two
    echo "Starting tomcat 2"
    $TOMCAT_HOME_TWO/bin/startup.sh
    echo "Tomcat PID After RESTART :"  `ps aux | grep org.apache.catalina.startup.Bootstrap | grep -v grep | awk '{ print $2 }'`
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
 angularjs_app() {
  FRAMEWORK_NAME="Angularjs"
  URL="http://localhost:8080/AngularSpringApp/cars/addCar/honda"
  mkdir /mnt/tempcopywar
  wget http://ec2-54-204-218-30.compute-1.amazonaws.com:8080/examples/servlets/AngularSpringApp.war -O  /mnt/tempcopywar/AngularSpringApp.war
  wget http://ec2-54-204-218-30.compute-1.amazonaws.com:8080/examples/servlets/TransportService.war -O  /mnt/tempcopywar/TransportService.war
  tomcat_server_configure
  catalina_server_setup $FRAMEWORK_NAME
  cp /mnt/tempcopywar/AngularSpringApp.war  $TOMCAT_HOME_ONE/webapps/ 
  cp /mnt/tempcopywar/TransportService.war  $TOMCAT_HOME_TWO/webapps/
  mkdir /mnt/database
  wget http://ec2-54-204-218-30.compute-1.amazonaws.com:8080/examples/servlets/dockerconfigfiles/angularjs.sql -O /mnt/database/angularjs.sql
  dos2unix /mnt/database/angularjs.sql
  mysql_start_stop
  mysql -uroot -proot "" < /mnt/database/angularjs.sql
  tomcat_restart
  generate_load_url $URL $FRAMEWORK_NAME
 }
  
 servlet_app() {
  FRAMEWORK_NAME="Servlet"
  DATA="emailId=xyz%40gmail.com&subject=Apple+WebObjects+Framework&content=dsaf&button1=+Send+"
  URL="http://localhost:8080/BasicSample/inputSubmit.mailservlet"
  mkdir /mnt/tempcopywar
  wget http://ec2-54-204-218-30.compute-1.amazonaws.com:8080/examples/servlets/BasicSample.war -O /mnt/tempcopywar/BasicSample.war
  wget http://ec2-54-204-218-30.compute-1.amazonaws.com:8080/examples/servlets/EmployerInfoService.war -O /mnt/tempcopywar/EmployerInfoService.war
  tomcat_server_configure
  catalina_server_setup $FRAMEWORK_NAME
  cp /mnt/tempcopywar/BasicSample.war $TOMCAT_HOME_ONE/webapps/
  cp /mnt/tempcopywar/EmployerInfoService.war $TOMCAT_HOME_TWO/webapps/
  mkdir /mnt/database
  wget http://ec2-54-204-218-30.compute-1.amazonaws.com:8080/examples/servlets/dockerconfigfiles/servlet.sql -O /mnt/database/servlet.sql
  dos2unix /mnt/database/servlet.sql
  mysql_start_stop
  mysql -uroot -proot "" < /mnt/database/servlet.sql
  tomcat_restart
  generate_load_data $DATA $URL $FRAMEWORK_NAME
 }
  
 grails_app() {
  FRAMEWORK_NAME="Grails"
  URL="http://localhost:8080/GrailsWebserviceClient/client/"
  mkdir /mnt/tempcopywar
  wget http://ec2-54-204-218-30.compute-1.amazonaws.com:8080/examples/servlets/GrailsWebserviceClient.war -O /mnt/tempcopywar/GrailsWebserviceClient.war
  wget http://ec2-54-204-218-30.compute-1.amazonaws.com:8080/examples/servlets/GrailsWebservices.war -O /mnt/tempcopywar/GrailsWebservices.war
  tomcat_server_configure
  catalina_server_setup $FRAMEWORK_NAME
  cp /mnt/tempcopywar/GrailsWebserviceClient.war $TOMCAT_HOME_ONE/webapps/
  cp /mnt/tempcopywar/GrailsWebservices.war $TOMCAT_HOME_TWO/webapps/
  mkdir /mnt/database
  wget http://ec2-54-204-218-30.compute-1.amazonaws.com:8080/examples/servlets/dockerconfigfiles/grails.sql -O /mnt/database/grails.sql
  dos2unix /mnt/database/grails.sql
  mysql_start_stop
  mysql -uroot -proot "" < /mnt/database/grails.sql
  tomcat_restart
  generate_load_url $URL $FRAMEWORK_NAME
 }
  
 hibernate_app() {
  FRAMEWORK_NAME="Hibernate"
  URL="http://localhost:8080/hibernateWebserviceClient/client/"
  mkdir /mnt/tempcopywar
  wget http://ec2-54-204-218-30.compute-1.amazonaws.com:8080/examples/servlets/hibernateWebserviceClient.war -O /mnt/tempcopywar/hibernateWebserviceClient.war
  wget http://ec2-54-204-218-30.compute-1.amazonaws.com:8080/examples/servlets/hibernateWebservice.war -O /mnt/tempcopywar/hibernateWebservice.war
  tomcat_server_configure
  catalina_server_setup $FRAMEWORK_NAME
  cp /mnt/tempcopywar/hibernateWebserviceClient.war $TOMCAT_HOME_ONE/webapps/
  cp /mnt/tempcopywar/hibernateWebservice.war $TOMCAT_HOME_TWO/webapps/
  mkdir /mnt/database
  wget http://ec2-54-204-218-30.compute-1.amazonaws.com:8080/examples/servlets/dockerconfigfiles/hibernate.sql -O /mnt/database/hibernate.sql
  dos2unix /mnt/database/hibernate.sql
  mysql_start_stop
  mysql -uroot -proot "" < /mnt/database/hibernate.sql
  tomcat_restart
  generate_load_url $URL $FRAMEWORK_NAME
 }
 
 springbeans_app() {
  FRAMEWORK_NAME="SpringBeans"
  URL="http://localhost:8080/SpringBeansClient/client/"
  mkdir /mnt/tempcopywar
  wget http://ec2-54-204-218-30.compute-1.amazonaws.com:8080/examples/servlets/SpringBeansClient.war -O /mnt/tempcopywar/SpringBeansClient.war
  wget http://ec2-54-204-218-30.compute-1.amazonaws.com:8080/examples/servlets/SpringBeans.war -O /mnt/tempcopywar/SpringBeans.war
  tomcat_server_configure
  catalina_server_setup $FRAMEWORK_NAME
  cp /mnt/tempcopywar/SpringBeansClient.war $TOMCAT_HOME_ONE/webapps/
  cp /mnt/tempcopywar/SpringBeans.war $TOMCAT_HOME_TWO/webapps/
  mkdir /mnt/database
  wget http://ec2-54-204-218-30.compute-1.amazonaws.com:8080/examples/servlets/dockerconfigfiles/spring.sql -O /mnt/database/spring.sql
  dos2unix /mnt/database/spring.sql
  mysql_start_stop
  mysql -uroot -proot "" < /mnt/database/spring.sql
  tomcat_restart
  generate_load_url $URL $FRAMEWORK_NAME
  }
  
 mongodb_app() {
  FRAMEWORK_NAME="MongoDB"
  URL="http://localhost:8080/MongoDBClient/client/"
  mkdir /mnt/tempcopywar
  wget http://ec2-54-204-218-30.compute-1.amazonaws.com:8080/examples/servlets/MongoDBClient.war -O  /mnt/tempcopywar/MongoDBClient.war
  wget http://ec2-54-204-218-30.compute-1.amazonaws.com:8080/examples/servlets/MongoDBService.war -O  /mnt/tempcopywar/MongoDBService.war
  tomcat_server_configure
  catalina_server_setup $FRAMEWORK_NAME
  cp /mnt/tempcopywar/MongoDBClient.war  $TOMCAT_HOME_ONE/webapps/ 
  cp /mnt/tempcopywar/MongoDBService.war  $TOMCAT_HOME_TWO/webapps/
  mkdir /mnt/database
  wget http://ec2-54-204-218-30.compute-1.amazonaws.com:8080/examples/servlets/dockerconfigfiles/mongodb.sql -O /mnt/database/mongodb.sql
  dos2unix /mnt/database/mongodb.sql
  mysql_start_stop
  mysql -uroot -proot "" < /mnt/database/mongodb.sql
  mongodb_start_stop
  tomcat_restart
  generate_load_url $URL $FRAMEWORK_NAME
 }
 
 activemq_app() {
  FRAMEWORK_NAME="ActiveMQ"
  URL="http://localhost:8080/activemqClient/client/"
  mkdir /mnt/tempcopywar
  wget http://ec2-54-204-218-30.compute-1.amazonaws.com:8080/examples/servlets/activemqClient.war -O /mnt/tempcopywar/activemqClient.war
  wget http://ec2-54-204-218-30.compute-1.amazonaws.com:8080/examples/servlets/activemq.war -O /mnt/tempcopywar/activemq.war
  tomcat_server_configure
  catalina_server_setup $FRAMEWORK_NAME
  cp /mnt/tempcopywar/activemqClient.war $TOMCAT_HOME_ONE/webapps/
  cp /mnt/tempcopywar/activemq.war $TOMCAT_HOME_TWO/webapps/
  mkdir /mnt/database
  wget http://ec2-54-204-218-30.compute-1.amazonaws.com:8080/examples/servlets/dockerconfigfiles/activemq.sql -O /mnt/database/activemq.sql
  dos2unix /mnt/database/activemq.sql
  mysql_start_stop
  mysql -uroot -proot "" < /mnt/database/activemq.sql
  tomcat_restart
  generate_load_url $URL $FRAMEWORK_NAME
  }
  
 jsf_app() {
  FRAMEWORK_NAME="JSF"
  URL="http://localhost:8080/JSFClient/client/"
  mkdir /mnt/tempcopywar
  wget http://ec2-54-204-218-30.compute-1.amazonaws.com:8080/examples/servlets/JSFClient.war -O /mnt/tempcopywar/JSFClient.war
  wget http://ec2-54-204-218-30.compute-1.amazonaws.com:8080/examples/servlets/JavaServerFaces.war -O /mnt/tempcopywar/JavaServerFaces.war
  tomcat_server_configure
  catalina_server_setup $FRAMEWORK_NAME
  cp /mnt/tempcopywar/JSFClient.war $TOMCAT_HOME_ONE/webapps/
  cp /mnt/tempcopywar/JavaServerFaces.war $TOMCAT_HOME_TWO/webapps/
  mkdir /mnt/database
  wget http://ec2-54-204-218-30.compute-1.amazonaws.com:8080/examples/servlets/dockerconfigfiles/grails.sql -O /mnt/database/jsf.sql
  dos2unix /mnt/database/jsf.sql
  mysql_start_stop
  mysql -uroot -proot "" < /mnt/database/jsf.sql
  tomcat_restart
  generate_load_url $URL $FRAMEWORK_NAME
 }
 
 mysql_app() {
  FRAMEWORK_NAME="MySQL"
  URL="http://localhost:8080/MySQLClient/client/"
  mkdir /mnt/tempcopywar
  wget http://ec2-54-204-218-30.compute-1.amazonaws.com:8080/examples/servlets/MySQLClient.war -O  /mnt/tempcopywar/MySQLClient.war
  wget http://ec2-54-204-218-30.compute-1.amazonaws.com:8080/examples/servlets/MySQLService.war -O  /mnt/tempcopywar/MySQLService.war
  tomcat_server_configure
  catalina_server_setup $FRAMEWORK_NAME
  cp /mnt/tempcopywar/MySQLClient.war  $TOMCAT_HOME_ONE/webapps/ 
  cp /mnt/tempcopywar/MySQLService.war  $TOMCAT_HOME_TWO/webapps/
  mkdir /mnt/database
  mysql_start_stop
  tomcat_restart
  generate_load_url $URL $FRAMEWORK_NAME
 }
 
 rabbitmq_app() {
  FRAMEWORK_NAME="RabbitMQ"
  URL="http://localhost:8080/RabbitMQClient/client"
  mkdir /mnt/tempcopywar
  wget http://ec2-54-204-218-30.compute-1.amazonaws.com:8080/examples/servlets/RabbitMQClient.war -O /mnt/tempcopywar/RabbitMQClient.war
  wget http://ec2-54-204-218-30.compute-1.amazonaws.com:8080/examples/servlets/RabbitMQ.war -O /mnt/tempcopywar/RabbitMQ.war
  tomcat_server_configure
  catalina_server_setup $FRAMEWORK_NAME
  cp /mnt/tempcopywar/activemqClient.war $TOMCAT_HOME_ONE/webapps/
  cp /mnt/tempcopywar/RabbitMQ.war $TOMCAT_HOME_TWO/webapps/
  mkdir /mnt/database
  wget http://ec2-54-204-218-30.compute-1.amazonaws.com:8080/examples/servlets/dockerconfigfiles/rabbit.sql -O /mnt/database/rabbit.sql
  dos2unix /mnt/database/rabbit.sql
  mysql_start_stop
  rabbit_start_stop
  mysql -uroot -proot "" < /mnt/database/rabbit.sql
  tomcat_restart
  generate_load_url $URL $FRAMEWORK_NAME
  }
  
 postgressql_app() {
  FRAMEWORK_NAME="PostgresSQL"
  URL="http://localhost:8080/PostgresSQLClient/client/"
  mkdir /mnt/tempcopywar
  wget http://ec2-54-204-218-30.compute-1.amazonaws.com:8080/examples/servlets/PostgresSQLClient.war -O  /mnt/tempcopywar/PostgresSQLClient.war
  wget http://ec2-54-204-218-30.compute-1.amazonaws.com:8080/examples/servlets/PostgresSQLService.war -O  /mnt/tempcopywar/PostgresSQLService.war
  tomcat_server_configure
  catalina_server_setup $FRAMEWORK_NAME
  cp /mnt/tempcopywar/PostgresSQLClient.war  $TOMCAT_HOME_ONE/webapps/ 
  cp /mnt/tempcopywar/PostgresSQLService.war  $TOMCAT_HOME_TWO/webapps/
  mkdir /mnt/database
  postgressql_start_stop
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

