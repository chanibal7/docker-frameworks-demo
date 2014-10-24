package com.sample.domain;

import java.io.Serializable;

public class Book implements Serializable{
    
    private static final long serialVersionUID = 6297385302078200511L;
     
    private String name;
       private int id;
   
     
    public Book(String nm, int i){
        this.name=nm;
        this.id=i;
   
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
 
   
 
 
    public void setId(int id) {
        this.id = id;
    }
 
 
    
    public String getName() {
        return name;
    }
 
   
 
    public int getId() {
        return id;
    }
 
     
    @Override
    public String toString(){
        return "Name="+this.name+", Id="+this.id;
    }
}