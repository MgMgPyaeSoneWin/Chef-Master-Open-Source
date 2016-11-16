package com.example.materialdesign.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.materialdesign.R;
import com.example.materialdesign.model.DatabaseHandler;
import com.example.materialdesign.model.Recipe;
import com.example.materialdesign.model.RecipeItem;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.RemoteViewsService.RemoteViewsFactory;

/**
 * If you are familiar with Adapter of ListView,this is the same as adapter
 * with few changes
 * 
 */
public class ListProvider implements RemoteViewsFactory {
	private ArrayList<ListItem> listItemList = new ArrayList<ListItem>();
	private Context context = null;
	private int appWidgetId;

	public ListProvider(Context context, Intent intent) {
		this.context = context;
		appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
				AppWidgetManager.INVALID_APPWIDGET_ID);

		populateListItem();
	}

	private void populateListItem() {
		DatabaseHandler db = new DatabaseHandler(context);
		List<Recipe> List = db.getRecipeByFavourite();
        for (Recipe my : List) {
        	ListItem listItem = new ListItem();
        	listItem.title =my.getName();
        	listItem.rating ="Rating of food is "+my.getRating();
        	listItem.image=context.getResources().getIdentifier("final_"+my.getImage() , "mipmap" , context.getPackageName());
        	listItemList.add(listItem);
        } 
    }

	@Override
	public int getCount() {
		return listItemList.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/*
	 *Similar to getView of Adapter where instead of View
	 *we return RemoteViews 
	 * 
	 */
	@Override
	public RemoteViews getViewAt(int position) {
		final RemoteViews remoteView = new RemoteViews(
				context.getPackageName(), R.layout.list_row);
		ListItem listItem = listItemList.get(position);
		remoteView.setImageViewResource(R.id.imageView, listItem.image);
		remoteView.setTextViewText(R.id.title, listItem.title);
		remoteView.setTextViewText(R.id.rating, listItem.rating);

		return remoteView;
	}
	

	@Override
	public RemoteViews getLoadingView() {
		return null;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public void onCreate() {
	}

	@Override
	public void onDataSetChanged() {
	}

	@Override
	public void onDestroy() {
	}

}
