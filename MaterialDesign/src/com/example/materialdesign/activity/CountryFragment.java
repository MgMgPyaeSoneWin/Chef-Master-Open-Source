package com.example.materialdesign.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.materialdesign.model.DatabaseHandler;
import com.example.materialdesign.model.Country;
import com.example.materialdesign.R;
import com.example.materialdesign.activity.FragmentDrawer.ClickListener;
import com.example.materialdesign.activity.FragmentDrawer.RecyclerTouchListener;
import com.example.materialdesign.adapter.CountryAdapter;


public class CountryFragment extends Fragment {

	private List<Country> Country;
	private RecyclerView recyclerView;
    private CountryAdapter adapter;
 
    public CountryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Country = new ArrayList<Country>();
        DatabaseHandler db = new DatabaseHandler(getContext());
        List<Country> country = db.getAllCountries();
        for (Country cou : country) {
        	Country.add(new Country(cou.getID(),cou.getName(), cou.getFlag(), cou.getDescription()));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_countries, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);

        adapter = new CountryAdapter(getActivity(), Country);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
            	Intent i=new Intent("com.example.materialdesign.activity.CountryRecipeActivity");
            	i.putExtra("country", Country.get(position).getName());
            	startActivity(i);
            	
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        
        
        
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
