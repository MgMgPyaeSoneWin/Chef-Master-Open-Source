package com.example.materialdesign.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.protocol.RequestExpectContinue;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "Food_DB";

	// Recipes table name
	private static final String TABLE_RECIPE = "Recipe";
    
	//Country table name
	private static final String TABLE_COUNTRY = "Country";
	
	//Category table name
	private static final String TABLE_CATEGORY="Category";
	
	//My custom recipe table name
	private static final String TABLE_MY_RECIPE="My_Custom_Recipe";
	
	// Recipes Table Columns names
	private static final String KEY_RECIPE_ID = "recipe_id";
	private static final String KEY_RECIPE_NAME = "title";
	private static final String KEY_RECIPE_IMAGE = "image";
	private static final String KEY_RECIPE_INGREDIENT = "ingredient";
	private static final String KEY_RECIPE_PREPARATION = "preparation";
	private static final String KEY_RECIPE_COUNTRY_FLAG = "country_flag";
	private static final String KEY_RECIPE_CATEGORY = "category";
	private static final String KEY_RECIPE_RATING="rating";
	private static final String KEY_RECIPE_FAVOURITE="favourite";
	
	//Category Table Columns names
	private static final String KEY_CATEGORY_ID="category_id";
	private static final String KEY_CATEGORY_NAME="name";
	
	//Country Table Columns names
	private static final String KEY_COUNTRY_ID="country_id";
	private static final String KEY_COUNTRY_NAME="name";
	private static final String KEY_COUNTRY_FLAG="flag";
	private static final String KEY_COUNTRY_DESCRIPTION="description";
	
	//My custom recipe Table Columns names
	private static final String KEY_MY_RECIPE_ID = "my_custom_recipe_id";
	private static final String KEY_MY_RECIPE_NAME = "title";
	private static final String KEY_MY_RECIPE_IMAGE = "image";
	private static final String KEY_MY_RECIPE_INGREDIENT = "ingredient";
	private static final String KEY_MY_RECIPE_PREPARATION = "preparation";	
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_RECIPES_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_RECIPE + "("
				+ KEY_RECIPE_ID + " INTEGER PRIMARY KEY," + KEY_RECIPE_NAME + " TEXT,"
				+ KEY_RECIPE_IMAGE + " TEXT," + KEY_RECIPE_INGREDIENT + " TEXT,"
				+ KEY_RECIPE_PREPARATION + " TEXT," + KEY_RECIPE_COUNTRY_FLAG + " TEXT," + KEY_RECIPE_CATEGORY + " TEXT," + KEY_RECIPE_RATING + " FLOAT"  + KEY_RECIPE_FAVOURITE + " INTEGER"+")";
		db.execSQL(CREATE_RECIPES_TABLE);
		
		String CREATE_MY_RECIPES_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_MY_RECIPE + "("
				+ KEY_MY_RECIPE_ID + " INTEGER PRIMARY KEY," + KEY_MY_RECIPE_NAME + " TEXT,"
				+ KEY_MY_RECIPE_IMAGE + " TEXT," + KEY_MY_RECIPE_INGREDIENT + " TEXT,"
				+ KEY_MY_RECIPE_PREPARATION + " TEXT" + ")";
		db.execSQL(CREATE_MY_RECIPES_TABLE);
		
		String CREATE_CATEGORY_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CATEGORY + "("
				+ KEY_CATEGORY_ID + " INTEGER PRIMARY KEY," + KEY_CATEGORY_NAME + " TEXT" + ")";
		db.execSQL(CREATE_CATEGORY_TABLE);
		
		String CREATE_COUNTRY_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_COUNTRY + "("
				+ KEY_COUNTRY_ID + " INTEGER PRIMARY KEY," + KEY_COUNTRY_NAME + " TEXT,"
				+ KEY_COUNTRY_FLAG + " TEXT," + KEY_COUNTRY_DESCRIPTION + " TEXT" + ")";
		db.execSQL(CREATE_COUNTRY_TABLE); 
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older tables if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNTRY);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MY_RECIPE);
		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */
	
	// Getting All Recipes
	public List<Recipe> getAllRecipe() {
		List<Recipe> recipeList = new ArrayList<Recipe>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_RECIPE;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Recipe recipe = new Recipe();
				recipe.setID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_RECIPE_ID))));
				recipe.setName(cursor.getString(1));
				recipe.setImage(cursor.getString(2));
				recipe.setIngredient(cursor.getString(3));
				recipe.setPreparation(cursor.getString(4));
				recipe.setcountryflag(cursor.getString(5));
				recipe.setcategory(cursor.getString(6));
				recipe.setRating(cursor.getFloat(7));
				recipe.setFavourite(cursor.getInt(8));
				// Adding recipe to list
				recipeList.add(recipe);
			} while (cursor.moveToNext());
		}

		// return recipe list
		return recipeList;
	}
	
	//Geting  All  country Recipes
	public List<Recipe> getRecipeByCountry(String country) {
		SQLiteDatabase db = this.getReadableDatabase();
		List<Recipe> recipeList = new ArrayList<Recipe>();
		Cursor cursor = db.query(TABLE_RECIPE, new String[] { KEY_RECIPE_ID,KEY_RECIPE_NAME,
				KEY_RECIPE_IMAGE,KEY_RECIPE_INGREDIENT,KEY_RECIPE_PREPARATION,KEY_RECIPE_COUNTRY_FLAG,KEY_RECIPE_CATEGORY,KEY_RECIPE_RATING,KEY_RECIPE_FAVOURITE}, KEY_RECIPE_COUNTRY_FLAG + "=?",
				new String[] { country }, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				Recipe recipe = new Recipe();
				recipe.setID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_RECIPE_ID))));
				recipe.setName(cursor.getString(1));
				recipe.setImage(cursor.getString(2));
				recipe.setIngredient(cursor.getString(3));
				recipe.setPreparation(cursor.getString(4));
				recipe.setcountryflag(cursor.getString(5));
				recipe.setcategory(cursor.getString(6));
				recipe.setRating(cursor.getFloat(7));
				recipe.setFavourite(cursor.getInt(8));
				// Adding recipe to list
				recipeList.add(recipe);
			} while (cursor.moveToNext());
		}
		// return list
		return recipeList;
	}
	
	//Geting  All  category Recipes
	public List<Recipe> getRecipeByCategory(String category) {
		SQLiteDatabase db = this.getReadableDatabase();
		List<Recipe> recipeList = new ArrayList<Recipe>();
		Cursor cursor = db.query(TABLE_RECIPE, new String[] { KEY_RECIPE_ID,KEY_RECIPE_NAME,
				KEY_RECIPE_IMAGE,KEY_RECIPE_INGREDIENT,KEY_RECIPE_PREPARATION,KEY_RECIPE_COUNTRY_FLAG,KEY_RECIPE_CATEGORY,KEY_RECIPE_RATING,KEY_RECIPE_FAVOURITE}, KEY_RECIPE_CATEGORY + "=?",
				new String[] { category }, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				Recipe recipe = new Recipe();
				recipe.setID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_RECIPE_ID))));
				recipe.setName(cursor.getString(1));
				recipe.setImage(cursor.getString(2));
				recipe.setIngredient(cursor.getString(3));
				recipe.setPreparation(cursor.getString(4));
				recipe.setcountryflag(cursor.getString(5));
				recipe.setcategory(cursor.getString(6));
				recipe.setRating(cursor.getFloat(7));
				recipe.setFavourite(cursor.getInt(8));
				// Adding recipe to list
				recipeList.add(recipe);
			} while (cursor.moveToNext());
		}
		// return list
		return recipeList;
	}
	
	//Getting A Recipe By Name
	public Recipe getRecipeByName(String name) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_RECIPE, new String[] { KEY_RECIPE_ID,KEY_RECIPE_NAME,
				KEY_RECIPE_IMAGE,KEY_RECIPE_INGREDIENT,KEY_RECIPE_PREPARATION,KEY_RECIPE_CATEGORY,KEY_RECIPE_FAVOURITE}, KEY_RECIPE_NAME + "=?",
				new String[] { name }, null, null, null, null);
		Recipe recipe = new Recipe();
		if (cursor.moveToFirst()) {
				recipe.setID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_RECIPE_ID))));
				recipe.setName(cursor.getString(1));
				recipe.setImage(cursor.getString(2));
				recipe.setIngredient(cursor.getString(3));
				recipe.setPreparation(cursor.getString(4));
				recipe.setcategory(cursor.getString(5));
				recipe.setFavourite(cursor.getInt(6));
		}
		return recipe;
	}
	
	//Change favourite or not on the recipe
	public void changeFavourite(int id, int favourite_value) {
        SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_RECIPE_FAVOURITE, favourite_value); 
        db.update(TABLE_RECIPE,values,KEY_RECIPE_ID + "=?",new String[] { id+"" });
		
	}
	//Geting  All  Favourtie Recipes
	public List<Recipe> getRecipeByFavourite() {
		SQLiteDatabase db = this.getReadableDatabase();
		List<Recipe> recipeList = new ArrayList<Recipe>();
		Cursor cursor = db.query(TABLE_RECIPE, new String[] { KEY_RECIPE_ID,KEY_RECIPE_NAME,
				KEY_RECIPE_IMAGE,KEY_RECIPE_INGREDIENT,KEY_RECIPE_PREPARATION,KEY_RECIPE_COUNTRY_FLAG,KEY_RECIPE_CATEGORY,KEY_RECIPE_RATING,KEY_RECIPE_FAVOURITE}, KEY_RECIPE_FAVOURITE + "=?",
				new String[] { 1+"" }, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				Recipe recipe = new Recipe();
				recipe.setID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_RECIPE_ID))));
				recipe.setName(cursor.getString(1));
				recipe.setImage(cursor.getString(2));
				recipe.setIngredient(cursor.getString(3));
				recipe.setPreparation(cursor.getString(4));
				recipe.setcountryflag(cursor.getString(5));
				recipe.setcategory(cursor.getString(6));
				recipe.setRating(cursor.getFloat(7));
				recipe.setFavourite(cursor.getInt(8));
				// Adding recipe to list
				recipeList.add(recipe);
			} while (cursor.moveToNext());
		}
		// return list
		return recipeList;
	}
	
	// Getting All My Recipes
		public List<MyCustomRecipe> getAllMyRecipe() {
			List<MyCustomRecipe> recipeList = new ArrayList<MyCustomRecipe>();
			// Select All Query
			String selectQuery = "SELECT  * FROM " + TABLE_MY_RECIPE;

			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					MyCustomRecipe recipe = new MyCustomRecipe();
					recipe.setID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_MY_RECIPE_ID))));
					recipe.setName(cursor.getString(1));
					recipe.setImage(cursor.getString(2));
					recipe.setIngredient(cursor.getString(3));
					recipe.setPreparation(cursor.getString(4));
					// Adding recipe to list
					recipeList.add(recipe);
				} while (cursor.moveToNext());
			}

			// return recipe list
			return recipeList;
		}
		
			
    //Add my custom recipe
	public	void addMy(MyCustomRecipe recipe) {
			SQLiteDatabase db = this.getWritableDatabase();

			ContentValues values = new ContentValues();
			values.put(KEY_MY_RECIPE_NAME, recipe.getName()); 
			values.put(KEY_MY_RECIPE_IMAGE, recipe.getImage());
			values.put(KEY_MY_RECIPE_PREPARATION, recipe.getPreparation());
			values.put(KEY_MY_RECIPE_INGREDIENT, recipe.getIngredient()); 
			// Inserting Row
			db.insert(TABLE_MY_RECIPE, null, values);
			db.close(); // Closing database connection
	}
    
	//Update my custom recipe
	public void updateMy(MyCustomRecipe recipe,Integer id) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_MY_RECIPE_NAME, recipe.getName()); 
		values.put(KEY_MY_RECIPE_IMAGE, recipe.getImage());
		values.put(KEY_MY_RECIPE_PREPARATION, recipe.getPreparation());
		values.put(KEY_MY_RECIPE_INGREDIENT, recipe.getIngredient()); 
        db.update(TABLE_MY_RECIPE,values,KEY_MY_RECIPE_ID + "=?",new String[] { id+"" });
		
	} 
	//Geting  A  MyCustomRecipe
	public MyCustomRecipe getOneMyCustomRecipe(String name) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_MY_RECIPE, new String[] { KEY_MY_RECIPE_ID,KEY_MY_RECIPE_NAME,
				KEY_MY_RECIPE_IMAGE,KEY_MY_RECIPE_INGREDIENT,KEY_MY_RECIPE_PREPARATION}, KEY_MY_RECIPE_NAME + "=?",
				new String[] { name }, null, null, null, null);
		MyCustomRecipe recipe = new MyCustomRecipe();
		if (cursor.moveToFirst()) {
				recipe.setID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_MY_RECIPE_ID))));
				recipe.setName(cursor.getString(1));
				recipe.setImage(cursor.getString(2));
				recipe.setIngredient(cursor.getString(3));
				recipe.setPreparation(cursor.getString(4));
		}
		return recipe;
	}
	
	public boolean deleteMyCustomRecipe(String name) 
	{
		SQLiteDatabase db = this.getWritableDatabase();
		return db.delete(TABLE_MY_RECIPE, KEY_MY_RECIPE_NAME + "=?",new String[] { name }) > 0;
	    
	}
	
	
	// Getting All Countries
	public List<Country> getAllCountries() {
		List<Country> countryList = new ArrayList<Country>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_COUNTRY;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Country country = new Country();
				country.setID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_COUNTRY_ID))));
				country.setName(cursor.getString(1));
				country.setFlag(cursor.getString(2));
				country.setDescription(cursor.getString(3));
				
				// Adding country to list
				countryList.add(country);
			} while (cursor.moveToNext());
		}

		// return country list
		return countryList;
	}
	// Getting All Categories
	public List<Category> getAllCategories() {
		List<Category> categoryList = new ArrayList<Category>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Category category = new Category();
				category.setID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_CATEGORY_ID))));
				category.setName(cursor.getString(1));
				
				// Adding category to list
			    categoryList.add(category);
			} while (cursor.moveToNext());
		}

		// return category list
		return categoryList;
	}
}
