package com.example.materialdesign.activity;

import com.example.materialdesign.R;
import com.example.materialdesign.activity.FragmentDrawer.ClickListener;
import com.example.materialdesign.activity.FragmentDrawer.RecyclerTouchListener;
import com.example.materialdesign.adapter.BrowseCategoryGridAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class BrowseCategoriesFragment extends Fragment{
	
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        
    }
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_browsecategories, container, false);
        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // The number of Columns
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new BrowseCategoryGridAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
            	String category="";
            	switch(position){
                 	case 0:
                 		category="breakfast";
                 		break;
                 	case 1:
                 		category="meal";
                 	    break;
                 	case 2:
                 		category="vegetarian";
                 		break;
                 	case 3:
                 		category="salad";
                 		break;	
                 	case 4:
                 		category="dessert";
                 		break;
                 	case 5:
                 		category="drink";
                 		break;	
                 	case 6:
                 		category="bakery";
                 		break;	
                 	case 7:
                 		category="soup";
                 		break;	
            	}
            	Intent i=new Intent("com.example.materialdesign.activity.CategoryRecipeActivity");
            	i.putExtra("Category",category);
            	startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        return rootView;
    }
}
