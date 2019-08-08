package com.komsi.user_interface.Activities.RegisterUser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.komsi.user_interface.Activities.LoginKlien.LoginActivity;
import com.komsi.user_interface.R;

public class DaftarActivity_konfirm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_konfirm);
        Button login = (Button)findViewById(R.id.bt_home);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(DaftarActivity_konfirm.this, LoginActivity.class);
                startActivity(login);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(Account account) {
                EditText editUserId,editPhone,editEmail;
              //  editUserId=(EditText)findViewById(R.id.editUserID);
               // editUserId.setText(String.format("User Id anda "+account.getId()));
                editPhone=(EditText)findViewById(R.id.phone);
                editPhone.setText(String.format("Nomor HP anda "+account.getPhoneNumber() == null ? "":account.getPhoneNumber().toString()));
                //editEmail=(EditText)findViewById(R.id.editEmail);
               // editEmail.setText(String.format("Alamat Email anda "+account.getEmail()));
            }

            @Override
            public void onError(AccountKitError accountKitError) {

            }
        });


    }
}
