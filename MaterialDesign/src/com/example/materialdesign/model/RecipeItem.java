package com.example.materialdesign.model;


public class RecipeItem {
    private String mTitle;
    private String imgThumbnail;
    private float mRating;
    private String mCountry;
    private String mCategory;
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getThumbnail() {
        return imgThumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.imgThumbnail = thumbnail;
    }
    
    public float getRate()	{
    	return mRating;
    }
    
    public void setRate(float rating)	{
    	this.mRating=rating;
    }
    
    public String getCountry()	{
    	return mCountry;
    }
    
    public void setCountry(String country)	{
    	this.mCountry = country;	
    }
    
    public String getCategory()	{
    	return mCategory;
    }
    
    public void setCategory(String category)	{
    	this.mCategory = category;
    }
}
