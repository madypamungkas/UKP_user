package com.komsi.user_interface.Activities.Dashboard;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.komsi.user_interface.Activities.LoginKlien.LoginActivity;
import com.komsi.user_interface.Activities.MainActivity;
import com.komsi.user_interface.Activities.RegisterUser.DaftarActivity;
import com.komsi.user_interface.Activities.bin.TentangAplikasi;
import com.komsi.user_interface.R;
import com.komsi.user_interface.Storage.SharedPrefManager;

public class Dashboard extends AppCompatActivity {
    Button bt_login;
    Button bt_daftar;

    private final static int REQUEST_CODE = 999;
    private ConnectivityManager connectivityManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);


        Button bt_login = (Button) findViewById(R.id.bt_login);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(Dashboard.this, LoginActivity.class);
                startActivity(login);

            }
        });

        Button bt_daftar = (Button) findViewById(R.id.bt_daftar);
        bt_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLoginPage(LoginType.PHONE);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        cekConnectivity();
    }
    private void startLoginPage(LoginType phone) {
        Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(LoginType.PHONE,
                        AccountKitActivity.ResponseType.TOKEN);
        intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,configurationBuilder.build());
        startActivityForResult(intent, REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE){
            AccountKitLoginResult result = data.getParcelableExtra(AccountKitLoginResult
                    .RESULT_KEY);
            if(result.getError() !=null)
            {
                Toast.makeText(this, ""+result.getError().getErrorType()
                        .getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
            else if (result.wasCancelled()){
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
                return;
            }
            else {
                if(result.getAccessToken() != null)
                    Toast.makeText(this, "Sukses !! "//+result.getAccessToken().getAccountId()
                            , Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "Sukses !!"+result.getAuthorizationCode()
                            .substring(0,10), Toast.LENGTH_SHORT).show();

                startActivity(new Intent(this, DaftarActivity.class));
            }
        }
    }

    private void cekConnectivity() {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Snackbar.make(findViewById(R.id.dashboardLayout),
                    "Anda Terhubung Dengan Layanan Internet",
                    Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(findViewById(R.id.dashboardLayout),
                    "Koneksi Terputus, Anda Tidak Terhubung Dengan Layanan Internet"
                    , Snackbar.LENGTH_LONG).show();

        }
    }

    public void about(View view) {
        Intent intent = new Intent(Dashboard.this, TentangAplikasi.class);
        startActivity(intent);
    }
}
