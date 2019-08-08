package com.komsi.user_interface.FCM;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.komsi.user_interface.Storage.SharedPrefManager;

import static android.support.constraint.Constraints.TAG;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("MeetMyPsi", "Refreshed token: " + refreshedToken);

        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
        SharedPrefManager.getInstance(this).setToken(refreshedToken);
        SharedPreferences sharedPref = getSharedPreferences("TokenFCM", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("token", refreshedToken);
        editor.apply();

    }
}
