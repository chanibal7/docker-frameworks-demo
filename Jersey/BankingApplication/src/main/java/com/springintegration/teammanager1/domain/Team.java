package com.springintegration.teammanager1.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity  
@Table(name="teams")  
public class Team {  
    
    @Id  
    @GeneratedValue  
    private int id;  
    @Column  
    private String name;  
     @Column 
    private float rating;  
      
    @Override
	public String toString() {
		return "Team [id=" + id + ", name=" + name + ", rating=" + rating + "]";
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
    public float getRating() {  
        return rating;  
    }  
    public void setRating(float rating) {  
        this.rating = rating;  
    }  
  
}  