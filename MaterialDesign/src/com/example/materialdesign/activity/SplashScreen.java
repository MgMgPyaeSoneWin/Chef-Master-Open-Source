package com.example.materialdesign.activity;

import com.example.materialdesign.R;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

public class SplashScreen extends AppCompatActivity{

	ProgressBar prgLoading;
	int progress = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);
		
		prgLoading = (ProgressBar) findViewById(R.id.prgLoading);
		prgLoading.setProgress(progress);
		
			new Loading().execute();
	}
	public class Loading extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			while(progress < 100){
				try {
					Thread.sleep(35);
					progress += 1;
					prgLoading.setProgress(progress);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Intent i = new Intent(SplashScreen.this, MainActivity.class);
			startActivity(i);
		}
	}
}
