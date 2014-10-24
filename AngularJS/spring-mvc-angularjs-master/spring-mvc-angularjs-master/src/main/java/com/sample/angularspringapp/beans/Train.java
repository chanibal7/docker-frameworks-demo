package com.sample.angularspringapp.beans;




public class Train {
    private int id;
    private String name;
    private int speed;
    private Boolean diesel;

    public Train() { }

    public Train(int id, String name, int speed, Boolean diesel) {
        this.id = id;
        this.name = name;
        this.speed = speed;
        this.diesel = diesel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Boolean getDiesel() {
        return diesel;
    }

    public void setDiesel(Boolean diesel) {
        this.diesel = diesel;
    }

	@Override
	public String toString() {
		return "Train [id=" + id + ", name=" + name + ", speed=" + speed
				+ ", diesel=" + diesel + "]";
	}
}
