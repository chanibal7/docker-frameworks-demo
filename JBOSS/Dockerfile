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


# Install Oracle Java 7
RUN mkdir /mnt/tempdircopy
RUN cd /mnt/tempdircopy
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/jdk-7u51-linux-x64/jdk-7u51-linux-x64.tar.gz
RUN mkdir /opt/jdk
RUN tar -zxvf jdk-7u51-linux-x64.tar.gz -C /opt/jdk
RUN update-alternatives --install /usr/bin/java java /opt/jdk/jdk1.7.0_51/bin/java 100
RUN update-alternatives --install /usr/bin/javac javac /opt/jdk/jdk1.7.0_51/bin/javac 100
RUN update-alternatives --display java
RUN update-alternatives --display javac


# Install Tomcat and JBOSS Server
RUN mkdir /mnt/dockerdemo 
RUN mkdir /mnt/dockerdemo/server 
RUN mkdir /mnt/dockerdemo/server/jbossone
RUN mkdir /mnt/dockerdemo/server/tomcattwo

RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/jboss/jboss-as-7.1.1.Final.tar.gz -O  /mnt/tempdircopy/jboss-as-7.1.1.Final.tar.gz
RUN tar -zxvf /mnt/tempdircopy/jboss-as-7.1.1.Final.tar.gz -C /mnt/tempdircopy
RUN cp -r /mnt/tempdircopy/jboss-as-7.1.1.Final/* /mnt/dockerdemo/server/jbossone/

RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/apache-tomcat-7.0.55/apache-tomcat-7.0.55.tar.gz -O  /mnt/tempdircopy/apache-tomcat-7.0.55.tar.gz
RUN tar -zxvf /mnt/tempdircopy/apache-tomcat-7.0.55.tar.gz -C /mnt/tempdircopy
RUN cp -r /mnt/tempdircopy/apache-tomcat-7.0.55/* /mnt/dockerdemo/server/tomcattwo/

RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-sh-files/jboss/standalone.sh  -O /mnt/tempdircopy/standalone.sh
RUN dos2unix /mnt/tempdircopy/standalone.sh

RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-sh-files/jboss/standalone.conf -O /mnt/tempdircopy/standalone.conf
RUN dos2unix /mnt/tempdircopy/standalone.conf

RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-sh-files/jboss/catalina2.sh -O /mnt/tempdircopy/catalina2.sh
RUN dos2unix /mnt/tempdircopy/catalina2.sh

RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-xml-files/jboss/server2.xml -O /mnt/tempdircopy/server2.xml
RUN dos2unix /mnt/tempdircopy/server2.xml

RUN cp /mnt/tempdircopy/server2.xml  /mnt/dockerdemo/server/tomcattwo/conf/server.xml
RUN cp /mnt/tempdircopy/standalone.sh /mnt/dockerdemo/server/jbossone/bin/standalone.sh
RUN cp /mnt/tempdircopy/standalone.conf /mnt/dockerdemo/server/jbossone/bin/standalone.conf
RUN cp /mnt/tempdircopy/catalina2.sh /mnt/dockerdemo/server/tomcattwo/bin/catalina.sh

RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/war-files/jboss/JbossSample.war -O /mnt/tempdircopy/JbossSample.war
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/war-files/jboss/JbossClient.war -O /mnt/tempdircopy/JbossClient.war 

RUN cp /mnt/tempdircopy/JbossClient.war  /mnt/dockerdemo/server/jbossone/standalone/deployments/JbossClient.war 
RUN cp /mnt/tempdircopy/JbossSample.war /mnt/dockerdemo/server/tomcattwo/webapps/JbossSample.war

#Download java and machine agent configured controller info file
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-xml-files/jboss/controller-info.xml -O /mnt/tempdircopy/controller-info.xml
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-xml-files/jboss/machine-controller-info.xml -O /mnt/tempdircopy/machine-controller-info.xml

#Download script file
RUN mkdir /mnt/script
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/script/jboss/run.sh -O /mnt/script/run.sh
RUN dos2unix /mnt/script/run.sh
RUN chmod +x /mnt/script/run.sh

#Download SQL file
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/SQL+files/jboss.sql -O /mnt/tempdircopy/jboss.sql
RUN dos2unix /mnt/tempdircopy/jboss.sql
##################### INSTALLATION END #####################

 