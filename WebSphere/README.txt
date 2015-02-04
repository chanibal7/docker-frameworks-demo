Docker File :
	Docker file is used to generate docker image for WebSphere Server support.
	This image is built with containing necessary artefacts to run WebSphere container.
  
Script File :
	run.sh file is used to generate load for WebSphere Server application.
	This script file is loaded under container as part of the docker image created.   
  

Steps to build docker image and Generate load for WebSphere application:
	1. Download Dockerfile from (url:https://github.com/Appdynamics/docker-frameworks-demo/tree/master/WebSphere)
	2. Build the docker image by running the command :docker build -t="appdynamics/<application name>:<tagname>" <Docker filepath>
	3. Build the container by running the command: docker run -i -t <latest generated image by docker file(image ID)> /bin/bash
	4. Follow the below steps to install WebSphere Server with in a running container:
	   ->cd /bin
       ->unlink sh
	   ->ln -s /bin/bash sh  
       ->cd /mnt/tempdircopy/wassetup/WAS
       ->./install -is:javaconsole -options /mnt/tempdircopy/response.text -silent
	5. Override server.xml(path:/opt/IBM/WebSphere/AppServer/profiles/appsvr01/config/cells/...<dynamic name>../nodes/...<dynamic name>.../servers/server1/server.xml) from   /mnt/tempdir/server.xml.
	6. Update run.sh with server.xml path.
	7. Navigate to the path /mnt/script to run the script file "run.sh" which generate load for WebSphere server application
	  -> pass arguments while running run.sh file as mentioned below
	  -> ./run.sh <controller ip> <port number> ,eg:-./run.sh ec2-54-204-218-30.compute-1.amazonaws.com 80
	  

	