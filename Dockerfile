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

# Install MySQL Server
RUN apt-get install -y debconf-utils
RUN echo 'mysql-server mysql-server/root_password password root' | sudo debconf-set-selections 
RUN echo 'mysql-server mysql-server/root_password_again password root' | sudo debconf-set-selections 
RUN apt-get update
RUN apt-get -y upgrade
RUN apt-get install -y mysql-server

# Install MongoDB
RUN apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 7F0CEB10
RUN echo 'deb http://downloads-distro.mongodb.org/repo/ubuntu-upstart dist 10gen' | sudo tee /etc/apt/sources.list.d/mongodb.list
RUN apt-get update
RUN apt-get install -y mongodb-org

# Install Oracle Java 7
RUN apt-get update
RUN apt-get install -y python-software-properties
RUN apt-get install -y software-properties-common
RUN add-apt-repository -y ppa:webupd8team/java
RUN apt-get update && apt-get -y upgrade 
RUN echo 'oracle-java7-installer shared/accepted-oracle-license-v1-1 select true' | sudo /usr/bin/debconf-set-selections
RUN apt-get install -y oracle-java8-installer && apt-get clean
RUN update-alternatives --display java 
ENV JAVA_HOME /usr/lib/jvm/java-8-oracle

RUN mkdir /mnt/script
#RUN wget http://ec2-54-204-218-30.compute-1.amazonaws.com:8080/examples/servlets/dockerconfigfiles/run.sh -O /mnt/script/run.sh
COPY /run.sh /mnt/script/
RUN dos2unix /mnt/script/run.sh
RUN chmod u+x /mnt/script/run.sh
RUN ln -s /mnt/script/run.sh /run.sh
RUN chmod u+x /run.sh
##################### INSTALLATION END #####################

 
