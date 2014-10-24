package com.sample.angularspringapp.beans;





public class RailwayStation {


	private int id;

	private String name;


    private Train train;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Train getTrain() {
		return train;
	}

	public void setTrain(Train trainset) {
		this.train = trainset;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
}
