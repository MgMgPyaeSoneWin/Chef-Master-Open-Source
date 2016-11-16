package com.example.materialdesign.model;

public class MyCustomRecipe {
	//private variables
		int _id;
		String _title;
		String _image;
		String _ingredient;
		String _preparation;
		// Empty constructor
		public MyCustomRecipe(){
			
		}
		// constructor
		public MyCustomRecipe(int id, String title, String image,String ingredient,String preparation){
			this._id = id;
			this._title = title;
			this._image = image;
			this._ingredient=ingredient;
			this._preparation=preparation;
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

}
