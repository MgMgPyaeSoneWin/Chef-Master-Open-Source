package com.example.materialdesign.model;

public class Recipe {
	
	//private variables
	int _id;
	String _title;
	String _image;
	String _ingredient;
	String _preparation;
	String _country_flag;
	String _category;
	Float _rating;
	Integer _favourite;
	// Empty constructor
	public Recipe(){
		
	}
	// constructor
	public Recipe(int id, String title, String image,String ingredient,String preparation,String countryflag,String category,Float rating,Integer favourite){
		this._id = id;
		this._title = title;
		this._image = image;
		this._ingredient=ingredient;
		this._preparation=preparation;
		this._country_flag=countryflag;
		this._category=category;
		this._rating=rating;
		this._favourite=favourite;
	}
	
	public int getID(){
		return this._id;
	}
	
	
	public void setID(int id){
		this._id = id;
	}
	
	
	public String getName(){
		return this._title;
	}
	
	
	public void setName(String title){
		this._title = title;
	}
	
	
	public String getImage(){
		return this._image;
	}
	
	
	public void setImage(String image){
		this._image = image;
	}
	
	public String getIngredient(){
		return this._ingredient;
	}
	
	public void setIngredient(String ingredient){
		this._ingredient = ingredient;
	}
	
	public String getPreparation(){
		return this._preparation;
	}
	
	public void setPreparation(String preparation){
		this._preparation = preparation;
	}
	
	public String getcountryflag(){
		return this._country_flag;
	}
	
	public void setcountryflag(String countryflag){
		this._country_flag = countryflag;
	}
	
	public String getcategory(){
		return this._category;
	}
	
	public void setcategory(String category){
		this._category = category;
	}
	
	public Float getRating(){
		return this._rating;
	}
	
	public void setRating(Float rating){
		this._rating = rating;
	}
	
	public Integer getFavourite(){
		return this._favourite;
	}
	
	public void setFavourite(Integer favourite){
		this._favourite = favourite;
	}
}
