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

# Install MySQL Server
RUN apt-get install -y debconf-utils
RUN echo 'mysql-server mysql-server/root_password password root' | sudo debconf-set-selections 
RUN echo 'mysql-server mysql-server/root_password_again password root' | sudo debconf-set-selections 
RUN apt-get install -y mysql-server

# Install TomcatEE Server
RUN mkdir /mnt/dockerdemo 
RUN mkdir /mnt/dockerdemo/server 
RUN mkdir /mnt/dockerdemo/server/tomcateeone
RUN mkdir /mnt/dockerdemo/server/tomcateetwo

RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/apache-tomee-1.7.1-plus/apache-tomee-1.7.1-plus.tar.gz -O /mnt/tempdircopy/apache-tomee-1.7.1-plus.tar.gz
RUN tar -zxvf /mnt/tempdircopy/apache-tomee-1.7.1-plus.tar.gz -C /mnt/tempdircopy

RUN cp -r /mnt/tempdircopy/apache-tomee-plus-1.7.1/* /mnt/dockerdemo/server/tomcateeone
RUN cp -r /mnt/tempdircopy/apache-tomee-plus-1.7.1/* /mnt/dockerdemo/server/tomcateetwo

RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-sh-files/tomcatee/catalina1.sh -O /mnt/dockerdemo/server/tomcateeone/bin/catalina.sh
RUN dos2unix /mnt/dockerdemo/server/tomcateeone/bin/catalina.sh

RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-sh-files/tomcatee/catalina2.sh -O /mnt/dockerdemo/server/tomcateetwo/bin/catalina.sh
RUN dos2unix /mnt/dockerdemo/server/tomcateetwo/bin/catalina.sh

RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-xml-files/tomcatee/server2.xml -O /mnt/dockerdemo/server/tomcateetwo/conf/server.xml
RUN dos2unix /mnt/dockerdemo/server/tomcateetwo/conf/server.xml

#Download tomcatee distributed application
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/war-files/tomcatee/AngularSpringApp.war -O /mnt/dockerdemo/server/tomcateeone/webapps/AngularSpringApp.war
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/war-files/tomcatee/TransportService.war -O /mnt/dockerdemo/server/tomcateetwo/webapps/TransportService.war

#Download java and machine agent configured controller info file
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-xml-files/tomcatee/controller-info.xml -O /mnt/tempdircopy/controller-info.xml
RUN dos2unix /mnt/tempdircopy/controller-info.xml
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-xml-files/tomcatee/machine-controller-info.xml -O /mnt/tempdircopy/machine-controller-info.xml
RUN dos2unix /mnt/tempdircopy/machine-controller-info.xml

#Download SQL file
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/SQL+files/tomcatee.sql -O /mnt/tempdircopy/tomcatee.sql
RUN dos2unix /mnt/tempdircopy/tomcatee.sql

#Download script file 
RUN mkdir /mnt/script
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/script/tomcatee/run.sh -O /mnt/script/run.sh
RUN dos2unix /mnt/script/run.sh
RUN chmod +x /mnt/script/run.sh
WORKDIR /mnt/script


##################### INSTALLATION END #####################