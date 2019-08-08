package com.komsi.user_interface.AlarmTask;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.komsi.user_interface.Activities.MainActivity;
import com.komsi.user_interface.Activities.Menu.KonfirmasiJadwal;
import com.komsi.user_interface.Api.RetrofitClient;
import com.komsi.user_interface.R;
import com.komsi.user_interface.Storage.SharedPrefManager;
import com.komsi.user_interface.models.Details;
import com.komsi.user_interface.models.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.NOTIFICATION_SERVICE;

public class TimerJadwalTask extends BroadcastReceiver {
    NotificationCompat.Builder notification;
    public static final int uniqeID = 45614;


    @Override
    public void onReceive(final Context context, Intent intent) {
        User user = SharedPrefManager.getInstance(context).getUser();
        final String token = "Bearer " + user.getToken();
        final int idJadwal = intent.getIntExtra("idJadwal", -1);
        final int psikologId = intent.getIntExtra("PsikologId", -1);
        final int klienId = intent.getIntExtra("KlienId", -1);

        Log.d("alarm manager ", "onBindViewHolder: alarm fired" + idJadwal + psikologId);
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().updateJadwal12(token, idJadwal, psikologId,klienId, 12);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("alarm manager ", "onBindViewHolder: alarm on respon");
                if (response.isSuccessful()) {
                    Log.d("alarm manager ", "onBindViewHolder: alarm succes");
                    Intent intent = new Intent(context, KonfirmasiJadwal.class);// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);
                    notification = new NotificationCompat.Builder(context);
                    notification.setAutoCancel(true);


                    notification.setSmallIcon(R.drawable.nav_icon_orders);
                    notification.setTicker("ticker");
                    notification.setWhen(System.currentTimeMillis());
                    notification.setContentTitle("Jadwal Dibatalkan");
                    // notification.setLargeIcon(R.drawable.nav_icon_orders);
                    notification.setContentText("Pembatalan Jadwal Oleh Klien Berhasil" );

                    Intent notif = new Intent(context, KonfirmasiJadwal.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(context, idJadwal, notif, PendingIntent.FLAG_UPDATE_CURRENT);
                    notification.setContentIntent(pendingIntent);

                    NotificationManager nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
                    nm.notify(uniqeID, notification.build());

                } else {
                    Log.d("alarm manager ", "onBindViewHolder: alarm gagal");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("alarm manager ", "onBindViewHolder: alarm failure 2");
            }
        });
    }
    public void notifikasi() {

    }
}

