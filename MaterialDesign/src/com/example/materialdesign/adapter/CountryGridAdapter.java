package com.example.materialdesign.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.example.materialdesign.R;
import com.example.materialdesign.model.DatabaseHandler;
import com.example.materialdesign.model.MyCustomRecipe;
import com.example.materialdesign.model.Recipe;
import com.example.materialdesign.model.RecipeItem;
public class CountryGridAdapter extends RecyclerView.Adapter<CountryGridAdapter.ViewHolder>{
	List<RecipeItem> mItems;
	Context con;
	String country;
	public CountryGridAdapter(Context context,View view,String country_name){
		 super();
		 con=context;
		 country=country_name;
		 Log.i("Country_name",country);
		 mItems = new ArrayList<RecipeItem>();
	     RecipeItem item = new RecipeItem();
	     DatabaseHandler db = new DatabaseHandler(context);
	        List<Recipe> recipe = db.getRecipeByCountry(country);
	        for (Recipe my : recipe) {
	        	item = new RecipeItem();
	        	item.setTitle(my.getName());
	        	item.setThumbnail("final_"+my.getImage());
	        	item.setRate(my.getRating());
	        	item.setCategory(my.getcategory());
	        	mItems.add(item);
	        } 
	}
	
	 @Override
	    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
	        View v = LayoutInflater.from(viewGroup.getContext())
	                .inflate(R.layout.country_grid_layout, viewGroup, false);
	        ViewHolder viewHolder = new ViewHolder(v);
	        return viewHolder;
	    }
	 
	  @Override
	    public void onBindViewHolder(ViewHolder viewHolder, int i) {
	        RecipeItem food = mItems.get(i);
	        viewHolder.title.setText(food.getTitle());
	        viewHolder.imgThumbnail.setImageResource(con.getResources().getIdentifier(food.getThumbnail() , "mipmap" , con.getPackageName()));
	        viewHolder.rating.setRating((float) food.getRate());
	        viewHolder.category.setText(food.getCategory());
	    }

	    @Override
	    public int getItemCount() {
	        return mItems.size();
	    }

	    class ViewHolder extends RecyclerView.ViewHolder{
	        public ImageView imgThumbnail;
	        public TextView title;
	        public RatingBar rating;
	        public TextView category;

	        public ViewHolder(View itemView) {
	            super(itemView);
	            imgThumbnail = (ImageView)itemView.findViewById(R.id.img_thumbnail);
	            title = (TextView)itemView.findViewById(R.id.title);
	            rating=(RatingBar)itemView.findViewById(R.id.ratingBar);
	            category=(TextView)itemView.findViewById(R.id.category);
	        }
	    }

}
