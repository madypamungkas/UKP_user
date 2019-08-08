package com.komsi.user_interface.AlarmTask;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.komsi.user_interface.Activities.MainActivity;
import com.komsi.user_interface.Activities.Menu.JadwalKonsul;
import com.komsi.user_interface.R;
import com.komsi.user_interface.Storage.SharedPrefManager;
import com.komsi.user_interface.models.Details;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmTask extends BroadcastReceiver {

    NotificationCompat.Builder notification;
    public static final int uniqeID = 45614;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Alarmnotif", "Alarm Fired");
        notification = new NotificationCompat.Builder(context);
        notification.setAutoCancel(true);
        Details details = SharedPrefManager.getInstance(context).getDetails();


        notification.setSmallIcon(R.drawable.nav_icon_orders);
        notification.setTicker("ticker");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("Konsultasi Anda Akan Dilaksanakan Hari Ini");
        // notification.setLargeIcon(R.drawable.nav_icon_orders);
        notification.setContentText("Konsultasi Anda Akan Dilaksanakan Hari Ini");

        Intent i = new Intent(context, JadwalKonsul.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        NotificationManager nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        nm.notify(uniqeID, notification.build());



        try
        {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(context, notification);
            r.play();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        notifikasi();
        playBeep();
    }
    public void playBeep()
    {

    }
    public void notifikasi() {

    }
}
