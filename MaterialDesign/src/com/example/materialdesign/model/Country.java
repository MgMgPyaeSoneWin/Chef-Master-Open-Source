package com.example.materialdesign.model;

public class Country {

	//private variables
	int _id;
	String _name;
	String _flag;
	String _description;
	// Empty constructor
    public Country(){
		
	}
	// constructor
	public Country(int id,String name,String flag,String description){
		this._id = id;
		this._name=name;
		this._flag=flag;
		this._description=description;
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
	
	public String getFlag(){
		return this._flag;
	}
	
	
	public void setFlag(String flag){
		this._flag = flag;
	}
	
	public String getDescription(){
		return this._description;
	}
	
	
	public void setDescription(String description){
		this._description = description;
	}
}
