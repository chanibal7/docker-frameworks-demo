#!/bin/bash

FROM ubuntu:14.10

MAINTAINER Udaya Narayanachar 

# Update the repository sources list
RUN apt-get update

################## BEGIN INSTALLATION ######################

# Install project dependencies
RUN apt-get install -y curl
RUN apt-get install -y wget
RUN apt-get install -y unzip
RUN apt-get install dos2unix
RUN apt-get update

# Install MySQL Server
RUN apt-get install -y debconf-utils
RUN echo 'mysql-server mysql-server/root_password password root' | sudo debconf-set-selections 
RUN echo 'mysql-server mysql-server/root_password_again password root' | sudo debconf-set-selections 
RUN apt-get install -y mysql-server


# Install Oracle Java 7
RUN mkdir /mnt/tempdircopy
RUN cd /mnt/tempdircopy
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/jdk-8u5-linux-x64/jdk-8u5-linux-x64.tar.gz
RUN mkdir /opt/jdk
RUN tar -zxvf jdk-8u5-linux-x64.tar.gz -C /opt/jdk
RUN update-alternatives --install /usr/bin/java java /opt/jdk/jdk1.8.0_05/bin/java 100
RUN update-alternatives --install /usr/bin/javac javac /opt/jdk/jdk1.8.0_05/bin/javac 100
RUN update-alternatives --display java
RUN update-alternatives --display javac

# Install Tomcat Server
RUN mkdir /mnt/dockerdemo 
RUN mkdir /mnt/dockerdemo/server 
RUN mkdir /mnt/dockerdemo/server/tomcat

RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/apache-tomcat-7.0.55/apache-tomcat-7.0.55.tar.gz -O  /mnt/tempdircopy/apache-tomcat-7.0.55.tar.gz
RUN tar -zxvf /mnt/tempdircopy/apache-tomcat-7.0.55.tar.gz -C /mnt/tempdircopy

RUN cp -r /mnt/tempdircopy/apache-tomcat-7.0.55/* /mnt/dockerdemo/server/tomcat

RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-sh-files/muleesb/catalina2.sh -O /mnt/tempdircopy/catalina.sh
RUN dos2unix /mnt/tempdircopy/catalina.sh
RUN cp /mnt/tempdircopy/catalina.sh /mnt/dockerdemo/server/tomcat/bin/catalina.sh

RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-xml-files/muleesb/server2.xml -O /mnt/tempdircopy/server.xml
RUN dos2unix /mnt/tempdircopy/server.xml
RUN cp /mnt/tempdircopy/server.xml /mnt/dockerdemo/server/tomcat/conf/server.xml

RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/war-files/muleesb/HelpingServlet.war -O /mnt/tempdircopy/HelpingServlet.war
RUN cp /mnt/tempdircopy/HelpingServlet.war /mnt/dockerdemo/server/tomcat/webapps/HelpingServlet.war

#Download wrapper.conf,java and machine agent configured controller info file
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-xml-files/muleesb/controller-info.xml -O /mnt/tempdircopy/controller-info.xml
RUN dos2unix /mnt/tempdircopy/controller-info.xml

RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-xml-files/muleesb/machine-controller-info.xml -O /mnt/tempdircopy/machine-controller-info.xml
RUN dos2unix /mnt/tempdircopy/machine-controller-info.xml

RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-properties-files/muleesb/wrapper.conf -O /mnt/tempdircopy/wrapper.conf
RUN dos2unix /mnt/tempdircopy/wrapper.conf

# MuleESB server and MuleESB application
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/mule-standalone-3.5.0/mule-standalone-3.5.0.tar.gz -O /mnt/tempdircopy/mule-standalone-3.5.0.tar.gz
RUN mkdir /mnt/dockerdemo/server/muleesbserver
RUN tar -zxvf /mnt/tempdircopy/mule-standalone-3.5.0.tar.gz -C /mnt/dockerdemo/server/muleesbserver
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/war-files/muleesb/mule-example.zip -O /mnt/tempdircopy/mule-example.zip
RUN cp -r /mnt/tempdircopy/mule-example.zip /mnt/dockerdemo/server/muleesbserver/mule-standalone-3.5.0/apps/mule-example.zip

#Download script file
RUN mkdir /mnt/script
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/script/muleesb/run.sh -O /mnt/script/run.sh
RUN dos2unix /mnt/script/run.sh
RUN chmod +x /mnt/script/run.sh

RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/SQL+files/muleesb.sql -O /mnt/tempdircopy/muleesb.sql
RUN dos2unix /mnt/tempdircopy/muleesb.sql
##################### INSTALLATION END #####################