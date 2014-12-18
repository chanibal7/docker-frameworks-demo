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


# Install Oracle Java 6
RUN mkdir /mnt/tempdircopy
RUN cd /mnt/tempdircopy
RUN mkdir /opt/jdk
RUN cd /opt/jdk
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/jdk/jdk-6u45-linux-x64.bin
RUN chmod +x jdk-6u45-linux-x64.bin
RUN ./jdk-6u45-linux-x64.bin
RUN mv /jdk1.6.0_45 /opt/jdk
RUN pwd
RUN update-alternatives --install /usr/bin/java java /opt/jdk/jdk1.6.0_45/bin/java 100
RUN update-alternatives --install /usr/bin/javac javac /opt/jdk/jdk1.6.0_45/bin/javac 100
RUN update-alternatives --display java
RUN update-alternatives --display javac


# Install Tomcat Server
RUN mkdir /mnt/dockerdemo 
RUN mkdir /mnt/dockerdemo/server 
RUN mkdir /mnt/dockerdemo/server/tomcatone
RUN mkdir /mnt/dockerdemo/server/jbosstwo

RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/jboss/jboss-4.2.3.GA-jdk6.zip -O  /mnt/tempdircopy/jboss-4.2.3.GA-jdk6.zip
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/apache-tomcat-7.0.55/apache-tomcat-7.0.55.tar.gz -O  /mnt/tempdircopy/apache-tomcat-7.0.55.tar.gz
RUN unzip /mnt/tempdircopy/jboss-4.2.3.GA-jdk6.zip -d /mnt/tempdircopy
RUN tar -zxvf /mnt/tempdircopy/apache-tomcat-7.0.55.tar.gz -C /mnt/tempdircopy

RUN cp -r /mnt/tempdircopy/apache-tomcat-7.0.55/* /mnt/dockerdemo/server/tomcatone/
RUN cp -r /mnt/tempdircopy/jboss-4.2.3.GA/* /mnt/dockerdemo/server/jbosstwo/

RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-sh-files/jbossmq/catalina1.sh -O /mnt/tempdircopy/catalina1.sh
RUN dos2unix /mnt/tempdircopy/catalina1.sh

RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-sh-files/jbossmq/run.sh -O /mnt/tempdircopy/run.sh
RUN dos2unix /mnt/tempdircopy/run.sh

RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-xml-files/jbossmq/server2.xml -O /mnt/tempdircopy/server2.xml
RUN dos2unix /mnt/tempdircopy/server2.xml

RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-xml-files/jbossmq/jboss-service.xml -O /mnt/tempdircopy/jboss-service.xml
RUN dos2unix /mnt/tempdircopy/jboss-service.xml

RUN cp /mnt/tempdircopy/catalina1.sh /mnt/dockerdemo/server/tomcatone/bin/catalina.sh
RUN cp /mnt/tempdircopy/server2.xml /mnt/dockerdemo/server/tomcatone/conf/server.xml
RUN cp /mnt/tempdircopy/jboss-service.xml /mnt/dockerdemo/server/jbosstwo/server/default/conf/jboss-service.xml
RUN cp /mnt/tempdircopy/run.sh /mnt/dockerdemo/server/jbosstwo/bin/run.sh


RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/war-files/jbossmq/JbossMQ.war -O /mnt/tempdircopy/JbossMQ.war 
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/war-files/jbossmq/JbossMQClient.war -O /mnt/tempdircopy/JbossMQClient.war

RUN cp /mnt/tempdircopy/JbossMQClient.war  /mnt/dockerdemo/server/tomcatone/webapps/JbossMQClient.war
RUN cp /mnt/tempdircopy/JbossMQ.war /mnt/dockerdemo/server/jbosstwo/server/default/deploy/JbossMQ.war

#Download java and machine agent configured controller info file
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-xml-files/jbossmq/controller-info.xml -O /mnt/tempdircopy/controller-info.xml
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-xml-files/jbossmq/machine-controller-info.xml -O /mnt/tempdircopy/machine-controller-info.xml

#Download script file
RUN mkdir /mnt/script
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/script/jbossmq/run.sh -O /mnt/script/run.sh
RUN dos2unix /mnt/script/run.sh
RUN chmod +x /mnt/script/run.sh

#Download SQL file
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/SQL+files/jboss.sql -O /mnt/tempdircopy/jboss.sql
RUN dos2unix /mnt/tempdircopy/jboss.sql
##################### INSTALLATION END #####################

 