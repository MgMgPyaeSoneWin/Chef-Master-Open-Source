package com.example.materialdesign.adapter;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.example.materialdesign.R;
import com.example.materialdesign.activity.MainActivity;
import com.example.materialdesign.model.Country;
import com.example.materialdesign.model.DatabaseHandler;
import com.example.materialdesign.model.MyCustomRecipe;
import com.example.materialdesign.model.RecipeItem;
public class MyCustomGridAdapter  extends RecyclerView.Adapter<MyCustomGridAdapter.ViewHolder> {

    List<RecipeItem> mItems;
    public MyCustomGridAdapter(Context context,View view) {
        super();
        mItems = new ArrayList<RecipeItem>();
        RecipeItem item = new RecipeItem();
        DatabaseHandler db = new DatabaseHandler(context);
        List<MyCustomRecipe> MyCustomList = db.getAllMyRecipe();
        for (MyCustomRecipe my : MyCustomList) {
        	item = new RecipeItem();
        	item.setTitle(my.getName());
        	String path=my.getImage();//just path
        	item.setThumbnail(path);
        	mItems.add(item);
        	((TextView)view.findViewById(R.id.tvnorecipe)).setText("");
        } 
        if(MyCustomList.isEmpty())
        	((TextView)view.findViewById(R.id.tvnorecipe)).setText("No recipe");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.my_custom_grid_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        RecipeItem food = mItems.get(i);
        viewHolder.title.setText(food.getTitle());
        try{
        	viewHolder.imgThumbnail.setImageBitmap(BitmapFactory.decodeFile(food.getThumbnail()));
				    	 
	     }catch(Exception e){
	    	 
	     }
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
