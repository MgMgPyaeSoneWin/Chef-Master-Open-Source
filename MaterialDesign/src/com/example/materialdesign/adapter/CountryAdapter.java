package com.example.materialdesign.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import com.example.materialdesign.R;
import com.example.materialdesign.model.Country;
import com.example.materialdesign.model.NavDrawerItem;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {
    List<Country> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    public CountryAdapter(Context context, List<Country> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public CountryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.friends_items, parent, false);
        CountryViewHolder holder = new CountryViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CountryViewHolder holder, int position) {
    	holder.country_name.setText(data.get(position).getName());
    	holder.country_description.setText(data.get(position).getDescription());
    	holder.country_flag.setImageResource(context.getResources().getIdentifier(data.get(position).getFlag() , "mipmap" , context.getPackageName()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class CountryViewHolder extends RecyclerView.ViewHolder
    implements View.OnCreateContextMenuListener{
    	//CardView cardview;
        TextView country_name;
        TextView country_description;
        ImageView country_flag;
 
        CountryViewHolder(View itemView) {
            super(itemView);
            //cardview = (CardView)itemView.findViewById(R.id.cardview);
            country_name = (TextView)itemView.findViewById(R.id.country_name);
            country_description = (TextView)itemView.findViewById(R.id.country_description);
            country_flag = (ImageView)itemView.findViewById(R.id.country_flag);
            
            itemView.setOnCreateContextMenuListener(this);
        }
        
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        	
        	//menu.setHeaderTitle("Select The Action");   
        	//menu.add(0, v.getId(), 0, "Call");   
            //menu.add(0, v.getId(), 0, "SMS"); 
        }
    }
}
