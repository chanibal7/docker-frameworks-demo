package com.appdynamics;

import javax.ejb.Local;

@Local
public interface HelloWorldLocal {
	public String sayHello();
}
