package com.example.materialdesign.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.materialdesign.R;
import com.example.materialdesign.activity.FragmentDrawer.ClickListener;
import com.example.materialdesign.activity.FragmentDrawer.RecyclerTouchListener;
import com.example.materialdesign.adapter.CategoryGridAdapter;
import com.example.materialdesign.adapter.CountryGridAdapter;
import com.example.materialdesign.model.DatabaseHandler;
import com.example.materialdesign.model.Recipe;

public class CategoryRecipeActivity extends AppCompatActivity{
	RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
	DatabaseHandler db;
	private Toolbar mToolbar;
	private List<Recipe> recipes;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_recipe);
        db = new DatabaseHandler(getApplicationContext());
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
 
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // The number of Columns
        mLayoutManager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        String category="";
        Bundle extra=getIntent().getExtras();
        if(extra==null){
        	category="";
        }
        else{
        	category=extra.getString("Category");
        	//Toast.makeText(this,category, 2000).show();
        	recipes = db.getRecipeByCategory(category);
        }
        	
        mAdapter = new CategoryGridAdapter(this,this.findViewById(android.R.id.content),category);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new ClickListener() {
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

    }
	public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
         switch (item.getItemId()) {
          case android.R.id.home:
          Log.e("onOptionsItemSelected", "home");
              finish();
          return false;
       }


        return super.onOptionsItemSelected(item);
    }
}
