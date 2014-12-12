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
RUN apt-get install -y ant
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

#Download synapse engine
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/apache-synapse-2.1.0/synapse-2.1.0-bin.tar.gz
RUN tar -zxvf synapse-2.1.0-bin.tar.gz

#Download java and machine agent configured controller info file
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-xml-files/synapse/controller-info-client.xml -O /mnt/tempdircopy/controller-info-client.xml
RUN dos2unix /mnt/tempdircopy/controller-info-client.xml

RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-xml-files/synapse/controller-info-server.xml -O /mnt/tempdircopy/controller-info-server.xml
RUN dos2unix /mnt/tempdircopy/controller-info-server.xml

RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-xml-files/synapse/machine-controller-info.xml -O /mnt/tempdircopy/machine-controller-info.xml
RUN dos2unix /mnt/tempdircopy/machine-controller-info.xml

RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-properties-files/synapse/sample-server-wrapper.conf -O /mnt/tempdircopy/sample-server-wrapper.conf
RUN dos2unix /mnt/tempdircopy/sample-server-wrapper.conf

RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-sh-files/synapse/axis2server.sh -O /mnt/tempdircopy/axis2server.sh
RUN dos2unix /mnt/tempdircopy/axis2server.sh

RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-xml-files/synapse/build.xml -O /mnt/tempdircopy/build.xml 
RUN dos2unix /mnt/tempdircopy/build.xml

#Download script file 
RUN mkdir /mnt/script
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/script/synapse/run.sh -O /mnt/script/run.sh
RUN dos2unix /mnt/script/run.sh
RUN chmod +x /mnt/script/run.sh
WORKDIR /mnt/script


##################### INSTALLATION END #####################