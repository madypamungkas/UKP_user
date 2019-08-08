package com.komsi.user_interface.Activities.LoadData;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.komsi.user_interface.Activities.MainActivity;
import com.komsi.user_interface.Activities.Dashboard.SplashActivity;
import com.komsi.user_interface.Activities.VerifEmail.SendEmailVerif;
import com.komsi.user_interface.Api.RetrofitClient;
import com.komsi.user_interface.R;
import com.komsi.user_interface.Storage.SharedPrefManager;
import com.komsi.user_interface.models.Details;
import com.komsi.user_interface.models.Response_DetailKlien;
import com.komsi.user_interface.models.Response_Klien;
import com.komsi.user_interface.models.SendEmailVerif_Model;
import com.komsi.user_interface.models.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.komsi.user_interface.Storage.SharedPrefManager.SHARED_PREF_NAME;

public class Loading extends AppCompatActivity {
    NotificationCompat.Builder notification;
    public static final int uniqeID = 45614;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

    }

    String token;
    User user = SharedPrefManager.getInstance(this).getUser();

    @Override
    protected void onStart() {
        super.onStart();

        token = "Bearer " + user.getToken();
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        Details details = SharedPrefManager.getInstance(this).getDetails();
        if(details.getUsername() == null ){
            loadDetails();
        }
        else{
            LinearLayout loading = (LinearLayout)findViewById(R.id.loadingBar);
            LinearLayout refresh = (LinearLayout)findViewById(R.id.refreshLoading);
            loading.setVisibility(View.GONE);
            refresh.setVisibility(View.VISIBLE);
        }

    }

    public void loadDetails(){
        retrofit2.Call<Response_DetailKlien> call = RetrofitClient
                .getInstance()
                .getApi()
                .getDetails(token);
        call.enqueue(new retrofit2.Callback<Response_DetailKlien>() {
            @Override
            public void onResponse(retrofit2.Call<Response_DetailKlien> call, retrofit2.Response<Response_DetailKlien> response) {
                if (response.isSuccessful()) {
                    SharedPrefManager.getInstance(Loading.this).saveDetails(response.body().getDetails());
                    SharedPreferences sharedPref = getSharedPreferences("DaftarKonsul", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();

                    editor.putInt("userId", response.body().getDetails().getId());

                    loadKlien();

                } else {
                    Toast.makeText(Loading.this, "Error, Periksa Kembali Jaringan Anda", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<Response_DetailKlien> call, Throwable t) {
                Toast.makeText(Loading.this, "Error, Periksa Kembali Jaringan Anda", Toast.LENGTH_LONG).show();
            }
        });
    }
    public void loadKlien(){
        final Details details = SharedPrefManager.getInstance(Loading.this).getDetails();
        final int idUser = details.getId();

        Call<Response_Klien> callKlien = RetrofitClient.getInstance().getApi().getKlien( token, idUser);
        callKlien.enqueue(new Callback<Response_Klien>() {
            @Override
            public void onResponse(Call<Response_Klien> call, Response<Response_Klien> response) {
                SharedPrefManager.getInstance(Loading.this).saveKlien(response.body().getKlien());
                SharedPreferences sharedPref = getSharedPreferences("DaftarKonsul", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("idKlien", response.body().getKlien().getKlienId());
                editor.putInt("idUser", response.body().getKlien().getUser_id());
                // editor.putString("keahlian", psikologModel.get());
                editor.apply();

                SharedPreferences sharedPrefFCM = getSharedPreferences("TokenFCM", Context.MODE_PRIVATE);
                String TokenFCM = sharedPrefFCM.getString("token", null);

                Call<ResponseBody> putoken = RetrofitClient.getInstance().getApi().updateFCM(token, idUser, TokenFCM );
                putoken.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (details.getEmail_verified_at() == null) {
                            Intent intent = new Intent(Loading.this, SendEmailVerif.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            //  Toast.makeText(Loading.this, details.getEmail_verified_at(), Toast.LENGTH_LONG).show();
                            startActivity(intent);
                        } else {
                            notifikasi();
                            linkFoto();
                            Intent intent = new Intent(Loading.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(Loading.this, "Error, Periksa Kembali Jaringan Anda", Toast.LENGTH_LONG).show();
                    }
                });
                //end of put token

            }

            @Override
            public void onFailure(Call<Response_Klien> call, Throwable t) {
                Toast.makeText(Loading.this, "Error, Periksa Kembali Jaringan Anda", Toast.LENGTH_LONG).show();

            }
        });
    }
   /* public void checkVerif() {
        retrofit2.Call<SendEmailVerif_Model> call = RetrofitClient.getInstance().getApi().getStatusVerif(token);
        call.enqueue(new Callback<SendEmailVerif_Model>() {
            @Override
            public void onResponse(retrofit2.Call<SendEmailVerif_Model> call, Response<SendEmailVerif_Model> response) {
                if (response.body().getMessage() == "Not verified") {

                    Intent intent = new Intent(Loading.this, SendEmailVerif.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    notifikasi();
                    linkFoto();
                    Intent intent = new Intent(Loading.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<SendEmailVerif_Model> call, Throwable t) {
                Log.d("Cek Verif", "onFailure: gagal");
            }
        });

    }
*/
    public void notifikasi() {
        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);
        Details details = SharedPrefManager.getInstance(this).getDetails();


        notification.setSmallIcon(R.drawable.nav_icon_orders);
        notification.setTicker("ticker");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("Log In Sukses");
        // notification.setLargeIcon(R.drawable.nav_icon_orders);
        notification.setContentText("Selamat Datang " + details.getNama());

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(uniqeID, notification.build());
    }

    public void linkFoto(){
        //String link = "http://psikologi.ridwan.info/psikolog-api/public/public/images/";
        String link = "http://psikologi.ridwan.info/images/";
        //String link = "http://10.0.2.2/psikolog-api/public/images/";
        SharedPreferences sharedPref = getSharedPreferences("linkFoto", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("link", link);
        editor.apply();

    }
}
