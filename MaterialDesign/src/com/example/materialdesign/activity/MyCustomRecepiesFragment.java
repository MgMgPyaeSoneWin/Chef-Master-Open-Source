package com.example.materialdesign.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.example.materialdesign.R;
import com.example.materialdesign.activity.FragmentDrawer.ClickListener;
import com.example.materialdesign.activity.FragmentDrawer.RecyclerTouchListener;
import com.example.materialdesign.adapter.MyCustomGridAdapter;
import com.example.materialdesign.model.Country;
import com.example.materialdesign.model.DatabaseHandler;
import com.example.materialdesign.model.MyCustomRecipe;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyCustomRecepiesFragment extends Fragment {
	public RecyclerView mRecyclerView;
	RecyclerView.LayoutManager mLayoutManager;
	public RecyclerView.Adapter mAdapter;
    Bundle bundle;
	public View rootView;
	private List<MyCustomRecipe> myRecipe;
	DatabaseHandler db;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		bundle=savedInstanceState;
		rootView = inflater.inflate(R.layout.fragment_my_custom_recipe,
				container, false);
		// Calling the RecyclerView
		mRecyclerView = (RecyclerView) rootView
				.findViewById(R.id.recycler_view);
		mRecyclerView.setHasFixedSize(true);

		// The number of Columns
		mLayoutManager = new GridLayoutManager(getContext(), 2);
		mRecyclerView.setLayoutManager(mLayoutManager);

		mAdapter = new MyCustomGridAdapter(getContext(), rootView);
		mRecyclerView.setAdapter(mAdapter);
		db = new DatabaseHandler(getContext());
		myRecipe = db.getAllMyRecipe();
		mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(
				getActivity(), mRecyclerView, new ClickListener() {
					@Override
					public void onClick(View view, int position) {
						Intent i=new Intent("com.example.materialdesign.activity.DetailActivity");
		            	i.putExtra("name",myRecipe.get(position).getName());
		            	i.putExtra("my",true);
		            	startActivity(i);
					}

					@Override
					public void onLongClick(View view, int position) {
						// Toast.makeText(getActivity(),
						// "Selected Country : "+myRecipe.get(position).getName(),
						// Toast.LENGTH_SHORT).show();
						final String name = myRecipe.get(position).getName();
						final Dialog dialog = new Dialog(getContext());
						dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
						dialog.setContentView(R.layout.edit_delete);
						dialog.show();
						Button btnedit = (Button) dialog
								.findViewById(R.id.btnedit);
						btnedit.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								MyCustomRecipe myRecipe=db.getOneMyCustomRecipe(name);
								db.close();
								Integer recipeId=myRecipe.getID();
								String recipeTitle=myRecipe.getName();
								String recipeImage=myRecipe.getImage();
								String recipeIngredient=myRecipe.getIngredient();
								String recipePreparation=myRecipe.getPreparation();
								Intent i=new Intent("com.example.materialdesign.activity.Add_Edit_RecipeActivity");
								i.putExtra("recipeId",recipeId);
								i.putExtra("recipeTitle",recipeTitle);
								i.putExtra("recipeImage", recipeImage);
								i.putExtra("recipeIngredient", recipeIngredient);
								i.putExtra("recipePreparation", recipePreparation);
								startActivity(i);
								dialog.dismiss();
							}
						});
						Button btndelete = (Button) dialog.findViewById(R.id.btndelete);
						btndelete.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								MyCustomRecipe oneRecipe=db.getOneMyCustomRecipe(name);
								String path=oneRecipe.getImage();
								Boolean delete = db.deleteMyCustomRecipe(name);
								LayoutInflater inflater=getLayoutInflater(bundle);
						    	View customToastroot =inflater.inflate(R.layout.mycustom_toast, null);
								TextView txtView=(TextView) customToastroot.findViewById(R.id.toastTextView);
								Toast customtoast=new Toast(getContext());

								customtoast.setView(customToastroot);
								customtoast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL,0,-300);
								customtoast.setDuration(Toast.LENGTH_SHORT);
								txtView.setText((delete)?"Deleted successfully":"Sorry.Fail to delete");
				    			customtoast.show();
								dialog.dismiss();
								myRecipe = db.getAllMyRecipe();
								mAdapter = new MyCustomGridAdapter(getContext(), rootView);
								mRecyclerView.setAdapter(mAdapter);
							}
						});

					}
				}));
		return rootView;
	}

	@Override
	public void onResume() {
		Log.i("RESUME : ", "WORK");
		myRecipe = db.getAllMyRecipe();
		mAdapter = new MyCustomGridAdapter(getContext(), rootView);
		mRecyclerView.setAdapter(mAdapter);
		super.onResume();
	}

}
