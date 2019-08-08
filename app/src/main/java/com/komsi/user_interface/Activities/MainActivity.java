package com.komsi.user_interface.Activities;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.komsi.user_interface.Activities.EditActivity.EditBiodataUser;
import com.komsi.user_interface.Activities.VerifEmail.SendEmailVerif;
import com.komsi.user_interface.Api.RetrofitClient;
import com.komsi.user_interface.Fragments.AccountFragment;
import com.komsi.user_interface.Fragments.HomeFragment;
import com.komsi.user_interface.Fragments.OrderFragment;
import com.komsi.user_interface.R;
import com.komsi.user_interface.Storage.SharedPrefManager;
import com.komsi.user_interface.models.Details;
import com.komsi.user_interface.models.Response_DetailKlien;
import com.komsi.user_interface.models.Response_Klien;
import com.komsi.user_interface.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    private HomeFragment homeFragment;
    private OrderFragment orderFragment;
    private AccountFragment accountFragment;
    Dialog myDialog;
    User user = SharedPrefManager.getInstance(this).getUser();
    private String token = "Bearer " + user.getToken();
    NotificationCompat.Builder notification;
    public static final int uniqeID = 45612;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.main_nav);

        homeFragment = new HomeFragment();
        orderFragment = new OrderFragment();
        accountFragment = new AccountFragment();
        setFragment(homeFragment);
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        setFragment(homeFragment);
                        return true;
                    case R.id.nav_order:
                        setFragment(orderFragment);
                        return true;
                    case R.id.nav_account:
                        setFragment(accountFragment);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();

    }


    @Override
    protected void onStart() {
        super.onStart();
        Details details = SharedPrefManager.getInstance(this).getDetails();

        if (details.getNik() == null) {
            myDialog = new Dialog(this);
            myDialog.setContentView(R.layout.pop_up_isi_biodata);
            Button isiBiodata = (Button) myDialog.findViewById(R.id.isiBiodata);
            isiBiodata.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent isiBiodata = new Intent(MainActivity.this, EditBiodataUser.class);
                    myDialog.dismiss();
                    startActivity(isiBiodata);
                }
            });
            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            myDialog.show();
        }

    }

    public void notifikasi() {
        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);
        Details details = SharedPrefManager.getInstance(this).getDetails();

        notification.setSmallIcon(R.drawable.nav_icon_orders);
        notification.setTicker("ticker");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("Log In Sukses");
        notification.setContentText("Selamat Datang " + details.getNama());

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(uniqeID, notification.build());

    }


}
