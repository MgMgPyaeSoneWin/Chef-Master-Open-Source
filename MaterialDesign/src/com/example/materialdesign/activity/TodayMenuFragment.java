package com.example.materialdesign.activity;

import java.util.Timer; 
import java.util.TimerTask;

import com.example.materialdesign.R;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class TodayMenuFragment extends Fragment {

	View rootView;
	
	Timer timer;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final Handler mHandler = new Handler();
		random();

		// Create runnable for posting
		final Runnable mUpdateResults = new Runnable() {
			public void run() {

				AnimateandSlideShow();

			}
		};

		int delay = 1000; // delay for 1 sec.

		int period = 3000; // repeat every 1 sec.

		timer = new Timer();

		timer.scheduleAtFixedRate(new TimerTask() {

			public void run() {

				mHandler.post(mUpdateResults);

			}

		}, delay, period);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_todaymenu, container,
				false);

		return rootView;
	}

	public int currentimageindex = 0;
	TimerTask task;
	ImageView slidingimage;
	private int[] anim_index = new int[6];
	private int[] style = { R.anim.fade_in, R.anim.fade, R.anim.slide_left,
			R.anim.slide_right, R.anim.zoom_exit, R.anim.hold };
	private int[] IMAGE_IDS = { R.drawable.breakfast,
			R.drawable.meal, R.drawable.salad,
			R.drawable.soup, R.drawable.dessert,
			R.drawable.vegetarian };

	public void random() {
		int rand, count;
		boolean pass;
		for (int i = 0; i < 6; i++) {
			anim_index[i] = -1;
		}
		do {
			rand = (int) (Math.random() * 6);
			count = 0;
			pass = false;
			do {
				if (anim_index[count] == rand)
					pass = true;
				else if (anim_index[count] == -1) {
					anim_index[count] = rand;
					pass = true;
				} else
					count++;
			} while (!pass);

		} while (count != 5);
	}

	// android.os.Process.killProcess(android.os.Process.myPid());

	/**
	 * Helper method to start the animation on the splash screen
	 */
	private void AnimateandSlideShow() {

		slidingimage = (ImageView) rootView.findViewById(R.id.imgSlider);
		
		if(getActivity() == null){
			Log.i("Fragment Status : ", "Replace");
			timer.cancel();
			timer.purge();
			return;
		}
		slidingimage.setImageResource(IMAGE_IDS[currentimageindex
				% IMAGE_IDS.length]);

		currentimageindex++;
		Animation rotateimage = AnimationUtils.loadAnimation(getActivity(),
				style[anim_index[currentimageindex % IMAGE_IDS.length]]);

		slidingimage.startAnimation(rotateimage);

	}
}
