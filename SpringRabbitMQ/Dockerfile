#!/bin/bash

FROM ubuntu:14.10

MAINTAINER Deepak 

# Update the repository sources list
RUN apt-get update

################## BEGIN INSTALLATION ######################

# Install project dependencies
RUN apt-get install -y curl
RUN apt-get install -y wget
RUN apt-get install -y unzip
RUN apt-get install dos2unix

# Install MySQL Server
RUN apt-get install -y debconf-utils
RUN echo 'mysql-server mysql-server/root_password password root' | sudo debconf-set-selections 
RUN echo 'mysql-server mysql-server/root_password_again password root' | sudo debconf-set-selections 
RUN apt-get install -y mysql-server


# Install Oracle Java 8
RUN mkdir /mnt/tempdircopy
RUN cd /mnt/tempdircopy
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/jdk-8u5-linux-x64/jdk-8u5-linux-x64.tar.gz
RUN mkdir /opt/jdk
RUN tar -zxvf jdk-8u5-linux-x64.tar.gz -C /opt/jdk
RUN update-alternatives --install /usr/bin/java java /opt/jdk/jdk1.8.0_05/bin/java 100
RUN update-alternatives --install /usr/bin/javac javac /opt/jdk/jdk1.8.0_05/bin/javac 100
RUN update-alternatives --display java
RUN update-alternatives --display javac

# Install RabbitMq server
RUN apt-get install -y rabbitmq-server
RUN echo "deb http://www.rabbitmq.com/debian/ testing main"  | sudo tee  /etc/apt/sources.list.d/rabbitmq.list > /dev/null
RUN wget http://www.rabbitmq.com/rabbitmq-signing-key-public.asc
RUN apt-key add rabbitmq-signing-key-public.asc
RUN apt-get update
RUN apt-get install rabbitmq-server -y
RUN service rabbitmq-server start
RUN rabbitmq-plugins enable rabbitmq_management
RUN service rabbitmq-server restart


# Install Tomcat Server
RUN mkdir /mnt/dockerdemo 
RUN mkdir /mnt/dockerdemo/server 
RUN mkdir /mnt/dockerdemo/server/tomcatone
RUN mkdir /mnt/dockerdemo/server/tomcattwo
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/apache-tomcat-7.0.55/apache-tomcat-7.0.55.tar.gz -O  /mnt/tempdircopy/apache-tomcat-7.0.55.tar.gz
RUN tar -zxvf /mnt/tempdircopy/apache-tomcat-7.0.55.tar.gz -C /mnt/tempdircopy
RUN cp -r /mnt/tempdircopy/apache-tomcat-7.0.55/* /mnt/dockerdemo/server/tomcatone/
RUN cp -r /mnt/tempdircopy/apache-tomcat-7.0.55/* /mnt/dockerdemo/server/tomcattwo/

RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-sh-files/springrabbitmq/catalina1.sh -O /mnt/tempdircopy/catalina1.sh
RUN dos2unix /mnt/tempdircopy/catalina1.sh
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-sh-files/springrabbitmq/catalina2.sh -O /mnt/tempdircopy/catalina2.sh
RUN dos2unix /mnt/tempdircopy/catalina2.sh

RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-xml-files/rabbitmq/server2.xml -O /mnt/tempdircopy/server2.xml
RUN dos2unix /mnt/tempdircopy/server2.xml

RUN cp /mnt/tempdircopy/server2.xml  /mnt/dockerdemo/server/tomcattwo/conf/server.xml
RUN cp /mnt/tempdircopy/catalina1.sh /mnt/dockerdemo/server/tomcatone/bin/catalina.sh
RUN cp /mnt/tempdircopy/catalina2.sh /mnt/dockerdemo/server/tomcattwo/bin/catalina.sh

RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/war-files/springrabbitmq/SpringRabbitMQ.war -O /mnt/tempdircopy/SpringRabbitMQ.war
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/war-files/springrabbitmq/RabbitMQSpringClient.war -O /mnt/tempdircopy/RabbitMQSpringClient.war

RUN cp /mnt/tempdircopy/RabbitMQSpringClient.war  /mnt/dockerdemo/server/tomcatone/webapps/RabbitMQSpringClient.war 
RUN cp /mnt/tempdircopy/SpringRabbitMQ.war /mnt/dockerdemo/server/tomcattwo/webapps/SpringRabbitMQ.war

#Download java and machine agent configured controller info file
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-xml-files/rabbitmq/controller-info.xml -O /mnt/tempdircopy/controller-info.xml
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-xml-files/rabbitmq/machine-controller-info.xml -O /mnt/tempdircopy/machine-controller-info.xml

#Download script file
RUN mkdir /mnt/script
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/script/springrabbitmq/run.sh -O /mnt/script/run.sh
RUN dos2unix /mnt/script/run.sh
RUN chmod +x /mnt/script/run.sh

#Download SQL file
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/SQL+files/springrabbitmq.sql -O /mnt/tempdircopy/springrabbitmq.sql
RUN dos2unix /mnt/tempdircopy/springrabbitmq.sql
##################### INSTALLATION END #####################

 