package com.example.materialdesign.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.example.materialdesign.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class Utils {

	public static NotificationManager mManager;

	@SuppressWarnings("static-access")
	public static void generateNotification(Context context){ 
		String notimsg="";
		String timeStamp = new SimpleDateFormat("HH").format(new Date());
		long time = Integer.parseInt(timeStamp);
		if (time < 10)
		{
			notimsg = "Your breakfast is ready!";
		}
		else if (time < 13)
		{
			notimsg = "Your lunch is ready!";
		}
		else 
		{
			notimsg = "Your dinner is ready!";
		}
		mManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
		Intent intent1 = new Intent(context,MainActivity.class);
		Notification notification = new Notification(R.drawable.master_icon,notimsg, System.currentTimeMillis());
		intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);
		PendingIntent pendingNotificationIntent = PendingIntent.getActivity(context,0, intent1,PendingIntent.FLAG_UPDATE_CURRENT);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.setLatestEventInfo(context, "Chef Master", notimsg, pendingNotificationIntent);
		mManager.notify(0, notification);
	}
}
