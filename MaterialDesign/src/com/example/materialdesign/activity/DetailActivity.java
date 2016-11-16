package com.example.materialdesign.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.materialdesign.R;
import com.example.materialdesign.model.DatabaseHandler;
import com.example.materialdesign.model.MyCustomRecipe;
import com.example.materialdesign.model.Recipe;

public class DetailActivity extends AppCompatActivity{
	TextView recipe_title;
	ImageView recipe_image,favourite_image,shared_image;
	TextView preparation_title,ingredient_title;
	ImageView outline_image;
	TextView prepare_ingredient_text;
	int favourite_value;
	private Toolbar mToolbar;
	String name;
	Recipe recipe;
	MyCustomRecipe myrecipe;
	Context context;
	String[] preparation_tokens;
	String[] ingredient_tokens;
	String data="";
	DatabaseHandler db;
	Bundle extra;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		db = new DatabaseHandler(getApplicationContext());
		mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		recipe_title=(TextView)findViewById(R.id.tv_recipe_title);
		recipe_image=(ImageView)findViewById(R.id.img_recipe);
		favourite_image=(ImageView)findViewById(R.id.img_favourite);
		shared_image=(ImageView)findViewById(R.id.img_share);
		preparation_title=(TextView)findViewById(R.id.tv_preparation);
		ingredient_title=(TextView)findViewById(R.id.tv_ingredient);
		outline_image=(ImageView)findViewById(R.id.img_outline);
		outline_image.setImageResource(R.drawable.outline_prepare);
		prepare_ingredient_text=(TextView)findViewById(R.id.prepare_ingredient);	
		extra=getIntent().getExtras();
        if(extra==null){
        	name="";
        }
        else{
        	name=extra.getString("name");
        	context=getApplicationContext();
        	if(!(extra.getBoolean("my"))){
            	//Toast.makeText(this,name, 2000).show();
            	recipe=db.getRecipeByName(name);
            	recipe_title.setText("   "+recipe.getName());
            	recipe_image.setImageResource(context.getResources().getIdentifier("final_"+recipe.getImage() , "mipmap" , context.getPackageName()));
            	if(recipe.getFavourite()==0){
        			favourite_image.setImageResource(R.drawable.favourite_unfilled);
        			favourite_value=0;
        		}
        		else {
        			favourite_image.setImageResource(R.drawable.favourite_filled);
        			favourite_value=1;
        		}
            	preparation_tokens = recipe.getPreparation().split("#");
            	ingredient_tokens = recipe.getIngredient().split("#");
            	for(int index=0;index<preparation_tokens.length;index++){
            		data=data+(index+1)+". "+preparation_tokens[index]+"\n\n";
            	}
            	prepare_ingredient_text.setText(data);
            	String category=recipe.getcategory();
            	String color="";
            	if(category.equals("breakfast")) color="#fad24f";
            	else if(category.equals("meal")) color="#fcf4b5";
            	else if(category.equals("vegetarian")) color="#56a146";
            	else if(category.equals("salad")) color="#8fc73e";
            	else if(category.equals("dessert")) color="#ce405a";
            	else if(category.equals("drink")) color="#ed6b4b";
            	else if(category.equals("bakery")) color="#fad24f";
            	else color="#fcf4b5";
            	recipe_title.setBackgroundColor(Color.parseColor(color));
        	}
        	else{
        		myrecipe=db.getOneMyCustomRecipe(name);
        		recipe_title.setText("   "+myrecipe.getName());
        		
        		recipe_image.setImageBitmap(BitmapFactory.decodeFile(myrecipe.getImage()));
        		prepare_ingredient_text.setText(myrecipe.getPreparation());
        	}
        		
        }
	}
	
	public void onChangePreparation(View view){
		
		preparation_title.setTextColor(Color.parseColor("#30211c"));
		outline_image.setImageResource(R.drawable.outline_prepare);
		data="";
		if(extra.getBoolean("my")){
			prepare_ingredient_text.setText(myrecipe.getPreparation());
		}
		else{
			for(int index=0;index<preparation_tokens.length;index++){
	    		data=data+(index+1)+". "+preparation_tokens[index]+"\n\n";
	    	}
			prepare_ingredient_text.setText(data);
		}
	}
	
	public void onChangeIngredient(View view){
		
		ingredient_title.setTextColor(Color.parseColor("#30211c"));
		outline_image.setImageResource(R.drawable.outline_indredient);
		data="";
		if(extra.getBoolean("my")){
			prepare_ingredient_text.setText(myrecipe.getIngredient());
		}
		else{
			for(int index=0;index<ingredient_tokens.length;index++){
	    		data=data+(index+1)+". "+ingredient_tokens[index]+"\n\n";
	    	}
			prepare_ingredient_text.setText(data);
		}
	}
	
	public void onChangeFavourite(View view){
		if(favourite_value==0){
			favourite_image.setImageResource(R.drawable.favourite_filled);
			favourite_value=1;
		}
		else {
			favourite_image.setImageResource(R.drawable.favourite_unfilled);
			favourite_value=0;
		}
		db.changeFavourite(recipe.getID(),favourite_value);
	}
	
	public void onChangeShared(View view){	
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        String path="";
        Uri imageUri;
        Bitmap b;
        if(!(extra.getBoolean("my"))){
        	b =BitmapFactory.decodeResource(getResources(),context.getResources().getIdentifier("final_"+recipe.getImage() , "mipmap" , context.getPackageName()));
        	
        }
        else{
             b =BitmapFactory.decodeFile(myrecipe.getImage());
        }
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        path = MediaStore.Images.Media.insertImage(getContentResolver(),b, "Title", null);
        imageUri=  Uri.parse(path);
        share.putExtra(Intent.EXTRA_STREAM, imageUri);
        startActivity(Intent.createChooser(share, "Share Image!"));
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
         switch (item.getItemId()) {
          case android.R.id.home:
          Log.e("onOptionsItemSelected", "home");
              finish();
          return false;
       }


        return super.onOptionsItemSelected(item);
    }
}
