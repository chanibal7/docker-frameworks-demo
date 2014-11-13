Docker File :
	Docker file is used to generate docker image for OpenMQ server support.
	This image is built with containing necessary artifacts to run OpenMQ container.
	 
Script File :
	run.sh file is used to generate load for OpenMQ server.
    This script file is loaded under container as part of the docker image created.   
	 

Steps to build docker image and Generate load for OpenMQ server:
1. Download Dockerfile from url:https://github.com/Appdynamics/docker-frameworks-demo/tree/master/OpenMQ
2. Build the docker image by running the command :docker build -t="appdynamics/<application name>:<tagname>" <Docker filepath>
3. Build the container by running the command: docker run -i -t <latest generated image by docker file(image ID)> /bin/bash
4. Navigate to /mnt/server/MessageQueue4_5/mq/bin and edit the file "imqbrokerd" by updating JVM argument size to "-Xss256k" to avoid stack trace memory error.

		# Specify default arguments to the JVM here
		_def_jvm_args="-Xms192m -Xmx192m -Xss256k"

5. Navigate to the path /mnt/script to run the script file "run.sh" which generate load for OpenMQ application
  -> pass arguments while running run.sh file as mentioned below
  -> ./run.sh <controller ip> <port number>
     eg:-./run.sh ec2-54-204-218-30.compute-1.amazonaws.com 80
