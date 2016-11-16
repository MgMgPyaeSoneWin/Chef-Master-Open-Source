package com.example.materialdesign.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle; 
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.materialdesign.model.DatabaseHandler;
import com.example.materialdesign.model.Recipe;
import com.example.materialdesign.model.RecipeItem;
import com.example.materialdesign.R;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private static String TAG = MainActivity.class.getSimpleName();
    private PendingIntent pendingIntent;
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    Boolean isHome=true;
    Boolean isMyCustomRecipe=false;
    int request_code=1;
    SharedPreferences prf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Copy database from asserts and it only run for first time 
        if(!checkDataBase()){
        	File chefMaster=new File("/storage/emulated/0/chef master/");
        	chefMaster.mkdirs();
        	copyDataBase();
        }
        
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        displayView(0);
        Calendar calendar = Calendar.getInstance();

		
		//set notification for date --> 8th January 2015 at 9:06:00 PM
		//calendar.set(Calendar.MONTH, 0);
		//calendar.set(Calendar.YEAR, 2015);
		//calendar.set(Calendar.DAY_OF_MONTH, 8);
		String timeStamp = new SimpleDateFormat("HH").format(new Date());
		long time = Integer.parseInt(timeStamp);
		if (time < 10)
		{
		calendar.set(Calendar.HOUR_OF_DAY, 8);
		}
		else if (time < 13)
		{
		calendar.set(Calendar.HOUR_OF_DAY, 12);			
		}
		else 
		{
			calendar.set(Calendar.HOUR_OF_DAY, 18);
		}
		//calendar.set(Calendar.MINUTE, 06);
		//calendar.set(Calendar.SECOND, 0);
		//calendar.set(Calendar.AM_PM,Calendar.PM);

		Intent myIntent = new Intent(MainActivity.this, MyReceiver.class);
		pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent,0);

		AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
		//alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() ,5*1000 , pendingIntent);
		 alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24*60*60*1000 , pendingIntent);


        
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(isMyCustomRecipe){
        	getMenuInflater().inflate(R.menu.add, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.add) {
        	Intent intent = new Intent(getApplicationContext(),Add_Edit_RecipeActivity.class);
			startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }
      
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	
    	if(KeyEvent.KEYCODE_BACK == keyCode)
    	{ 
    		if(isHome){
    			final Dialog dialog = new Dialog(MainActivity.this);
    			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        		dialog.setContentView(R.layout.custom_dialog);
        		
        		TextView text = (TextView) dialog.findViewById(R.id.textDialog);
        		ImageView image = (ImageView) dialog.findViewById(R.id.imageDialog);
        		text.setText("Are your sure to exit?");
        		image.setImageResource(R.drawable.master_icon);
        		dialog.show();
        		Button yesButton = (Button) dialog.findViewById(R.id.yesButton);
                   // if decline button is clicked, close the custom dialog
                   yesButton.setOnClickListener(new OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           // Close dialog
                           //dialog.dismiss();
                    	   finish();
                       }
                });
                Button noButton = (Button) dialog.findViewById(R.id.noButton);
                   // if decline button is clicked, close the custom dialog
                   noButton.setOnClickListener(new OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           // Close dialog
                           dialog.dismiss();
                    	   //finish();
                       }
                });
    		}
    		else{
    			displayView(0);
    			isMyCustomRecipe=false;
    			invalidateOptionsMenu();
    			return true;
    		}
    		
    	}
    	
    	return super.onKeyDown(keyCode, event);
    }

    
    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
           case 0:
               fragment=new HomeFragment(); 
        	   title = getString(R.string.title_home);
        	   isHome=true;
        	   isMyCustomRecipe=false;
               break;
           case 1:
        	   fragment=new TodayMenuFragment(); 
        	   title = getString(R.string.title_todaymenu);
        	   isHome=false;
        	   isMyCustomRecipe=false;
        	   break;
           case 2:
        	   fragment=new MyCustomRecepiesFragment(); 
        	   title = getString(R.string.title_mycustomrecipe);
        	   isHome=false;
        	   isMyCustomRecipe=true;
        	   invalidateOptionsMenu();
        	   break;
           case 3:
        	   fragment=new BrowseCategoriesFragment(); 
        	   title = getString(R.string.title_browsecategory);
        	   isHome=false;
        	   isMyCustomRecipe=false;
        	   invalidateOptionsMenu();
        	   break;
           case 4:
        	   fragment=new FavouriteItemFragment(); 
        	   title = getString(R.string.title_favouriteitem);
        	   isHome=false;
        	   isMyCustomRecipe=false;
        	   break;
           case 5:
        	   fragment = new CountryFragment();
               title = getString(R.string.title_country);
               isHome=false;
               isMyCustomRecipe=false;
               break;
           case 6:       	   
        	   final Dialog dialog = new Dialog(MainActivity.this);
   			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
       		dialog.setContentView(R.layout.appdialog);
       		
       		TextView text = (TextView) dialog.findViewById(R.id.textDialog);
       		ImageView image = (ImageView) dialog.findViewById(R.id.imageDialog);
       		text.setText("Oct,2015\nSamsaung App Training Institute\n\nChef Master\nDeveloped by Team - 2\n1) Mg Myat Thu \n2) Mg Nanda Thant Sin \n3) Mg Pyae Sone Win");
       		image.setImageResource(R.drawable.master_icon);
       		dialog.show();
       		Button yesButton = (Button) dialog.findViewById(R.id.okButton);
                  // if decline button is clicked, close the custom dialog
                  yesButton.setOnClickListener(new OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          // Close dialog
                          dialog.dismiss();
                   	   //finish();
                      }
               });
               Button shareButton = (Button) dialog.findViewById(R.id.shareButton);
                  // if decline button is clicked, close the custom dialog
                  shareButton.setOnClickListener(new OnClickListener() {
                      @Override
                      public void onClick(View v) {
                    	Intent share = new Intent(android.content.Intent.ACTION_SEND);
                  		share.setType("text/plain");
                  		share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                  		// Add data to the intent, the receiving app will decide
                  		// what to do with it.
                  		share.putExtra(Intent.EXTRA_SUBJECT, "Chef Master");
                  		share.putExtra(Intent.EXTRA_TEXT, "http://www.chefmaster.host22.com");

                  		startActivity(Intent.createChooser(share, "Share link!"));
                  		dialog.dismiss();
                      }
               });
        	   isHome=false;
        	   isMyCustomRecipe=false;
        	   return;
           case 7:
               finish(); 
        	   break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }
    
    //Handles button click from today's menu 
    public void chooseRandom(View view){
    	String timeStamp = new SimpleDateFormat("dd").format(new Date());
    	//Toast.makeText(getApplicationContext(),timeStamp, 5000).show();
    	prf=getSharedPreferences("random",MODE_PRIVATE);
		SharedPreferences.Editor edit=prf.edit();
		String date=prf.getString("date","0");//0 date for first time
    	String category="";
    	LayoutInflater inflater=getLayoutInflater();
    	View customToastroot =inflater.inflate(R.layout.mycustom_toast, null);
		TextView txtView=(TextView) customToastroot.findViewById(R.id.toastTextView);
		Toast customtoast=new Toast(getApplicationContext());

		customtoast.setView(customToastroot);
		customtoast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL,0,-300);
		customtoast.setDuration(Toast.LENGTH_SHORT);
    	switch(view.getId()){
    	case R.id.breakfast:
    		category="breakfast";
    		if(date.equals(timeStamp) && !(prf.getString("breakfast","").equals(""))){
    			/*Toast.makeText(getApplicationContext(),"Breakfast is already random for this day!", 2000).show();*/
    			txtView.setText("Breakfast has already updated for today");
    			customtoast.show();
    			callActivity(prf.getString("breakfast",""));
    			return;
    		}
    		break;
    	case R.id.lunch:
    		category="vegetarian";
    		if(date.equals(timeStamp) && !(prf.getString("lunch","").equals(""))){
    			txtView.setText("Lunch has already updated for today");
    			customtoast.show();
    			callActivity(prf.getString("lunch",""));
    			return;
    		}
    		
    		break;
    	case R.id.dinner:
    		category="meal";
    		if(date.equals(timeStamp) && !(prf.getString("dinner","").equals(""))){
    			txtView.setText("Dinner has already updated for today");
    			customtoast.show();
    			callActivity(prf.getString("dinner",""));
    			return;
    		}
    		break;
    	default:
    		break;	
    	}	
    	if(!date.equals(timeStamp)){
    		edit.putString("breakfast", "");
    		edit.putString("lunch", "");
    		edit.putString("dinner", "");
    	}
    	DatabaseHandler db = new DatabaseHandler(getApplicationContext());
    	List<Recipe> recipe = db.getRecipeByCategory(category);
    	int index=(int) (Math.random() * recipe.size());
    	callActivity(recipe.get(index).getName());
		edit.putString((category.equals("breakfast")?"breakfast":((category.equals("vegetarian")?"lunch":"dinner"))),recipe.get(index).getName());
    	edit.putString("date",timeStamp);
		edit.commit();
    }
    
	private void callActivity(String name) {
		Intent i=new Intent("com.example.materialdesign.activity.DetailActivity");
    	i.putExtra("name",name);
    	startActivity(i);
	}


	//Handles images click from home events
	public void onImageClick(View view){
        int position=0;
        switch (view.getId()) {
        case R.id.tdmenu:
        	 position=1;
             break;
        case R.id.cusrecipe:
        	 position=2;
        	 break;
        case R.id.bwcategory:
             position=3;
        	 break;
        case R.id.favitem:
     	     position=4;
        	 break;
        default:
             break;
             
      }
      displayView(position);
	}
	private void copyDataBase()
    {
		ContextWrapper cw =new ContextWrapper(getApplicationContext());
		String DB_PATH = "/data/data/com.example.materialdesign/databases/";
		String DB_NAME = "Food_DB";
		
        Log.i("Database", "New database is being copied to device!");
        byte[] buffer = new byte[1024];
        OutputStream myOutput = null;
        int length;
        // Open your local db as the input stream
        InputStream myInput = null;
        DatabaseHandler db = new DatabaseHandler(this);
        db.getAllRecipe();
        try
        {
            myInput = MainActivity.this.getAssets().open(DB_NAME);
            // transfer bytes from the inputfile to the
            // outputfile
            myOutput =new FileOutputStream(DB_PATH+ DB_NAME);
            while((length = myInput.read(buffer)) > 0)
            {
                myOutput.write(buffer, 0, length);
            }
            myOutput.close();
            myOutput.flush();
            myInput.close();
            Log.i("Database", "New database has been copied to device!");

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
	
	public boolean checkDataBase()
	{
		String DB_PATH = "/data/data/com.example.materialdesign/databases/";
		String DB_NAME = "Food_DB";
	    File dbFile = new File(DB_PATH + DB_NAME);
	    Log.d("Database",dbFile.exists()+"");
	    return dbFile.exists();
	}
}