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
RUN mkdir /mnt/dockerdemo/server/tomcatone
RUN mkdir /mnt/dockerdemo/server/tomcattwo

RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/apache-tomcat-7.0.55/apache-tomcat-7.0.55.tar.gz -O  /mnt/tempdircopy/apache-tomcat-7.0.55.tar.gz
RUN tar -zxvf /mnt/tempdircopy/apache-tomcat-7.0.55.tar.gz -C /mnt/tempdircopy

RUN cp -r /mnt/tempdircopy/apache-tomcat-7.0.55/* /mnt/dockerdemo/server/tomcatone
RUN cp -r /mnt/tempdircopy/apache-tomcat-7.0.55/* /mnt/dockerdemo/server/tomcattwo

RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-sh-files/xfire/catalina2.sh -O /mnt/dockerdemo/server/tomcatone/bin/catalina.sh
RUN dos2unix /mnt/dockerdemo/server/tomcatone/bin/catalina.sh

RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-sh-files/xfire/catalina1.sh -O /mnt/dockerdemo/server/tomcattwo/bin/catalina.sh
RUN dos2unix /mnt/dockerdemo/server/tomcattwo/bin/catalina.sh

RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-xml-files/xfire/server2.xml -O /mnt/dockerdemo/server/tomcattwo/conf/server.xml
RUN dos2unix /mnt/dockerdemo/server/tomcattwo/conf/server.xml

RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/war-files/xfire/websvc.war -O /mnt/dockerdemo/server/tomcatone/webapps/websvc.war

RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/war-files/xfire/HelpingServlet.war -O /mnt/dockerdemo/server/tomcattwo/webapps/HelpingServlet.war

#Download java and machine agent configured controller info file
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-xml-files/xfire/controller-info.xml -O /mnt/tempdircopy/controller-info.xml
RUN dos2unix /mnt/tempdircopy/controller-info.xml
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-xml-files/xfire/machine-controller-info.xml -O /mnt/tempdircopy/machine-controller-info.xml
RUN dos2unix /mnt/tempdircopy/machine-controller-info.xml

#Download SQL file
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/SQL+files/xfire.sql -O /mnt/tempdircopy/xfire.sql
RUN dos2unix /mnt/tempdircopy/xfire.sql

#Download script file
RUN mkdir /mnt/script
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/script/xfire/run.sh -O /mnt/script/run.sh
RUN dos2unix /mnt/script/run.sh
RUN chmod +x /mnt/script/run.sh
WORKDIR /mnt/script

##################### INSTALLATION END #####################