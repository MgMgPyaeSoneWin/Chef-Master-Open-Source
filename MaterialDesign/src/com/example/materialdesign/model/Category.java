package com.example.materialdesign.model;

public class Category { 
	
	//private variables
	int _id;
	String _name;
	// Empty constructor
    public Category(){
		
	}
    
	
	public Category(int id,String name){
		this._id = id;
		this._name=name;
	}
	
	public int getID(){
		return this._id;
	}
	
	
	public void setID(int id){
		this._id = id;
	}
	
	
	public String getName(){
		return this._name;
	}
	
	
	public void setName(String name){
		this._name = name;
	}
}
