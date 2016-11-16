package com.example.materialdesign.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.materialdesign.R;
import com.example.materialdesign.model.DatabaseHandler;
import com.example.materialdesign.model.RecipeItem;
import com.example.materialdesign.model.Recipe;


public class FavouriteGridAdapter extends RecyclerView.Adapter<FavouriteGridAdapter.ViewHolder>{
	List<RecipeItem> mItems;
	Context con;
	public FavouriteGridAdapter(Context context,View view) {
        super();
        con=context;
        mItems = new ArrayList<RecipeItem>();
        RecipeItem item = new RecipeItem();
        DatabaseHandler db = new DatabaseHandler(context);
        List<Recipe> List = db.getRecipeByFavourite();
        for (Recipe my : List) {
        	item = new RecipeItem();
        	item.setTitle(my.getName());
        	item.setThumbnail("final_"+my.getImage());
        	mItems.add(item);
        	((TextView)view.findViewById(R.id.tvnorecipe)).setText("");
        } 
        if(List.isEmpty())
        	((TextView)view.findViewById(R.id.tvnorecipe)).setText("No recipe");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.favourite_grid_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        RecipeItem food = mItems.get(i);
        viewHolder.title.setText(food.getTitle());
        viewHolder.imgThumbnail.setImageResource(con.getResources().getIdentifier(food.getThumbnail() , "mipmap" , con.getPackageName()));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
    
    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imgThumbnail;
        public TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (ImageView)itemView.findViewById(R.id.img_thumbnail);
            title = (TextView)itemView.findViewById(R.id.title);
            
        }
    }
}
