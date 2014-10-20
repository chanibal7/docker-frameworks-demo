Basic Information:
	Dockerfile is used to generate docker image and Docker image used to create container for image.
	We are running script in side container to generate load and plotting application flow on appdynamics controller.

Please find mentioned below steps to generate load and plotting application flow on appdynamics controller:
1.Download Dockerfile
2. docker build -t="appdynamics/<application name>:<tagname>" <Docker filepath>
3. docker images
4. docker run -i -t <latest generated image by docker file> /bin/bash
6.In side container:
  ->cd /mnt/script/
  ->./run.sh ec2-54-204-218-30.compute-1.amazonaws.com 80

