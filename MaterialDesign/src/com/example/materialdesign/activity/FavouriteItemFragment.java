package com.example.materialdesign.activity;

import java.util.List;

import com.example.materialdesign.R;
import com.example.materialdesign.activity.FragmentDrawer.ClickListener;
import com.example.materialdesign.activity.FragmentDrawer.RecyclerTouchListener;
import com.example.materialdesign.adapter.FavouriteGridAdapter;
import com.example.materialdesign.adapter.MyCustomGridAdapter;
import com.example.materialdesign.model.DatabaseHandler;
import com.example.materialdesign.model.Recipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FavouriteItemFragment extends Fragment{
	public RecyclerView mRecyclerView;
	RecyclerView.LayoutManager mLayoutManager;
	public RecyclerView.Adapter mAdapter;

	public View rootView;
	private List<Recipe> recipes;
	DatabaseHandler db;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        
    }
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_favourite,
				container, false);
		// Calling the RecyclerView
		mRecyclerView = (RecyclerView) rootView
				.findViewById(R.id.recycler_view);
		mRecyclerView.setHasFixedSize(true);

		// The number of Columns
		mLayoutManager = new GridLayoutManager(getContext(), 2);
		mRecyclerView.setLayoutManager(mLayoutManager);

		mAdapter = new FavouriteGridAdapter(getContext(), rootView);
		mRecyclerView.setAdapter(mAdapter);
		db = new DatabaseHandler(getContext());
		recipes = db.getRecipeByFavourite();
		mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
            	Intent i=new Intent("com.example.materialdesign.activity.DetailActivity");
            	i.putExtra("name",recipes.get(position).getName());
            	startActivity(i);
            	//Toast.makeText(getActivity(), "Selected Country : "+Country.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onLongClick(View view, int position) {

            }
        }));
		return rootView;
    }
	
	@Override
	public void onResume() {
		Log.i("RESUME : ", "WORK");
		db = new DatabaseHandler(getContext());
		recipes = db.getRecipeByFavourite();
		mAdapter = new FavouriteGridAdapter(getContext(), rootView);
		mRecyclerView.setAdapter(mAdapter);
		super.onResume();
	}
}
