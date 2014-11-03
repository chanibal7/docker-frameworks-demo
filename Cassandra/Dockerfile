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
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-sh-files/cassandra/sources.list -O /mnt/tempdircopy/sources.list
RUN cp /mnt/tempdircopy/sources.list /etc/apt/sources.list
RUN dos2unix /etc/apt/sources.list
RUN apt-get update
RUN gpg --keyserver pgp.mit.edu --recv-keys 4BD736A82B5C1B00
RUN gpg --export --armor 4BD736A82B5C1B00 | sudo apt-key add -
RUN gpg --keyserver pgp.mit.edu --recv-keys 2B5C1B00
RUN gpg --export --armor 2B5C1B00 | sudo apt-key add -
RUN sudo apt-get update
RUN apt-cache showpkg cassandra
RUN sudo apt-get install cassandra
RUN sudo service cassandra start


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

# Install Tomcat Server
RUN mkdir /mnt/dockerdemo 
RUN mkdir /mnt/dockerdemo/server 
RUN mkdir /mnt/dockerdemo/server/tomcatone
RUN mkdir /mnt/dockerdemo/server/tomcattwo
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/apache-tomcat-7.0.55/apache-tomcat-7.0.55.tar.gz -O  /mnt/tempdircopy/apache-tomcat-7.0.55.tar.gz
RUN tar -zxvf /mnt/tempdircopy/apache-tomcat-7.0.55.tar.gz -C /mnt/tempdircopy
RUN cp -r /mnt/tempdircopy/apache-tomcat-7.0.55/* /mnt/dockerdemo/server/tomcatone/
RUN cp -r /mnt/tempdircopy/apache-tomcat-7.0.55/* /mnt/dockerdemo/server/tomcattwo/

RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-sh-files/cassandra/catalina1.sh -O /mnt/tempdircopy/catalina1.sh
RUN dos2unix /mnt/tempdircopy/catalina1.sh
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-sh-files/cassandra/catalina2.sh -O /mnt/tempdircopy/catalina2.sh
RUN dos2unix /mnt/tempdircopy/catalina2.sh

RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-xml-files/cassandra/server2.xml -O /mnt/tempdircopy/server2.xml
RUN dos2unix /mnt/tempdircopy/server2.xml

RUN cp /mnt/tempdircopy/server2.xml  /mnt/dockerdemo/server/tomcattwo/conf/server.xml
RUN cp /mnt/tempdircopy/catalina1.sh /mnt/dockerdemo/server/tomcatone/bin/catalina.sh
RUN cp /mnt/tempdircopy/catalina2.sh /mnt/dockerdemo/server/tomcattwo/bin/catalina.sh

RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/war-files/cassandra/cassandraWebservice.war -O /mnt/tempdircopy/cassandraWebservice.war
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/war-files/cassandra/cassandraWebserviceClient.war -O /mnt/tempdircopy/cassandraWebserviceClient.war

RUN cp /mnt/tempdircopy/cassandraWebserviceClient.war  /mnt/dockerdemo/server/tomcatone/webapps/cassandraWebserviceClient.war
RUN cp /mnt/tempdircopy/cassandraWebservice.war /mnt/dockerdemo/server/tomcattwo/webapps/cassandraWebservice.war

#Download java and machine agent configured controller info file
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-xml-files/cassandra/controller-info.xml -O /mnt/tempdircopy/controller-info.xml
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/Config-xml-files/cassandra/machine-controller-info.xml -O /mnt/tempdircopy/machine-controller-info.xml

#Download script file
RUN mkdir /mnt/script
RUN wget https://s3.amazonaws.com/appdynamics-docker-artifacts/script/cassandra/run.sh -O /mnt/script/run.sh
RUN dos2unix /mnt/script/run.sh
RUN chmod +x /mnt/script/run.sh
##################### INSTALLATION END #####################

 