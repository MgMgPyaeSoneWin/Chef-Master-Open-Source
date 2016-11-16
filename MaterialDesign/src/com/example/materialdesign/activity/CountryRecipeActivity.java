package com.example.materialdesign.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;

import com.example.materialdesign.R;
import com.example.materialdesign.activity.FragmentDrawer.ClickListener;
import com.example.materialdesign.activity.FragmentDrawer.RecyclerTouchListener;
import com.example.materialdesign.adapter.CountryGridAdapter;
import com.example.materialdesign.adapter.MyCustomGridAdapter;
import com.example.materialdesign.model.Country;
import com.example.materialdesign.model.DatabaseHandler;
import com.example.materialdesign.model.MyCustomRecipe;
import com.example.materialdesign.model.Recipe;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class CountryRecipeActivity extends AppCompatActivity{
	RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
	DatabaseHandler db;
	private Toolbar mToolbar;
	private List<Recipe> recipes;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_recipe);
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
        String country="";
        Bundle extra=getIntent().getExtras();
        if(extra==null){
        	country="";
        }
        else{
        	country=extra.getString("country").toLowerCase();
        	recipes = db.getRecipeByCountry(country);
        }
        	
        mAdapter = new CountryGridAdapter(this,this.findViewById(android.R.id.content),country);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
            	Intent i=new Intent("com.example.materialdesign.activity.DetailActivity");
            	i.putExtra("name",recipes.get(position).getName());
            	startActivity(i);
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
