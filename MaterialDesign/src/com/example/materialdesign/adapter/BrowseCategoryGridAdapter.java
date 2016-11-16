package com.example.materialdesign.adapter;

import com.example.materialdesign.R; 
import android.support.v7.widget.RecyclerView; 
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BrowseCategoryGridAdapter  extends RecyclerView.Adapter<BrowseCategoryGridAdapter.ViewHolder> {

    List<CategoryItem> mItems;

    public BrowseCategoryGridAdapter() {
        super();
        mItems = new ArrayList<CategoryItem>();
        CategoryItem recipes = new CategoryItem();
       
        recipes.setThumbnail(R.mipmap.breakfast);
        mItems.add(recipes);

        recipes = new CategoryItem();
       
        recipes.setThumbnail(R.mipmap.meal);
        mItems.add(recipes);

        recipes = new CategoryItem();
      
        recipes.setThumbnail(R.mipmap.vegetarian);
        mItems.add(recipes);

        recipes = new CategoryItem();
        
        recipes.setThumbnail(R.mipmap.salad);
        mItems.add(recipes);

        recipes = new CategoryItem();
       
        recipes.setThumbnail(R.mipmap.dessert);
        mItems.add(recipes);

        recipes = new CategoryItem();
       
        recipes.setThumbnail(R.mipmap.drink);
        mItems.add(recipes);

        recipes = new CategoryItem();
      
        recipes.setThumbnail(R.mipmap.bakery);
        mItems.add(recipes);

        recipes = new CategoryItem();
        
        recipes.setThumbnail(R.mipmap.soup);
        mItems.add(recipes);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.grid_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        CategoryItem nature = mItems.get(i);
       
        viewHolder.imgThumbnail.setImageResource(nature.getThumbnail());
    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imgThumbnail;
        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (ImageView)itemView.findViewById(R.id.img_thumbnail);
        
        }
    }

}
