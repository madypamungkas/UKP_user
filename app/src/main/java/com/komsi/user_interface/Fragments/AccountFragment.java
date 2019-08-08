package com.komsi.user_interface.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.komsi.user_interface.Activities.Dashboard.Dashboard;
import com.komsi.user_interface.Activities.EditActivity.EditAkun;
import com.komsi.user_interface.Activities.LoadData.Loading;
import com.komsi.user_interface.Activities.MainActivity;
import com.komsi.user_interface.Activities.VerifEmail.SendEmailVerif;
import com.komsi.user_interface.Activities.bin.TentangAplikasi;
import com.komsi.user_interface.Activities.EditActivity.UploadFoto;
import com.komsi.user_interface.Activities.password.GantiPassword;
import com.komsi.user_interface.Api.RetrofitClient;
import com.komsi.user_interface.R;
import com.komsi.user_interface.Storage.SharedPrefManager;
import com.komsi.user_interface.models.Details;
import com.squareup.picasso.Picasso;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.CONNECTIVITY_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment implements View.OnClickListener {


    public AccountFragment() {
        // Required empty public constructor
    }

    private ConnectivityManager connectivityManager;
    SwipeRefreshLayout swiperefresh;
    private int refresh_count = 0;

    ImageView avatar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_account, container, false);
        connectivityManager = (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
        Details details = SharedPrefManager.getInstance(getActivity()).getDetails();
        LinearLayout ganti = (LinearLayout) fragmentView.findViewById(R.id.gantipass);
        LinearLayout about = (LinearLayout) fragmentView.findViewById(R.id.about);
        RelativeLayout logout = (RelativeLayout) fragmentView.findViewById(R.id.logout);
        TextView username = (TextView) fragmentView.findViewById(R.id.accUsername);
        TextView nohp = (TextView) fragmentView.findViewById(R.id.accNohp);
        TextView email = (TextView) fragmentView.findViewById(R.id.accEmail);
        LinearLayout editakun = (LinearLayout) fragmentView.findViewById(R.id.edit_akun);
        TextView ubah_foto = (TextView) fragmentView.findViewById(R.id.ubah_foto);
        SharedPreferences sharedPrefFCM = getActivity()
                .getSharedPreferences("linkFoto", Context.MODE_PRIVATE);
        String link = sharedPrefFCM.getString("link", null);
        final String url = link + details.getFoto();
        avatar = (ImageView) fragmentView.findViewById(R.id.avatarAcc);
        Picasso.with(getActivity()).load(url).error(R.drawable.menu_icon_user).into(avatar);
        username.setText(" " + details.getUsername());
        nohp.setText(" " + details.getNo_telepon());
        email.setText(" " + details.getEmail());
        logout.setOnClickListener((View.OnClickListener) this);
        ganti.setOnClickListener((View.OnClickListener) this);
        about.setOnClickListener((View.OnClickListener) this);
        editakun.setOnClickListener((View.OnClickListener) this);
        ubah_foto.setOnClickListener((View.OnClickListener) this);
        return fragmentView;

    }

    private static String token;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.gantipass:
                Intent ganti = new Intent(getActivity(), GantiPassword.class);
                startActivity(ganti);
                break;
            case R.id.about:
                Intent about = new Intent(getActivity(), SendEmailVerif.class);
                startActivity(about);
                break;
            case R.id.edit_akun:
                Intent edit = new Intent(getActivity(), EditAkun.class);
                startActivity(edit);
                break;
            case R.id.ubah_foto:
                Intent uploadFoto = new Intent(getActivity(), UploadFoto.class);
                startActivity(uploadFoto);
                break;
            case R.id.logout:
                Details details = SharedPrefManager.getInstance(getActivity()).getDetails();
                final int idUser = details.getId();

                SharedPreferences sharedPref = getActivity().getSharedPreferences
                        ("DaftarKonsul", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.clear();
                editor.apply();



                Call<ResponseBody> putoken = RetrofitClient.getInstance().getApi().updateFCM(token, idUser, "0");
                putoken.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Intent intent = new Intent(getActivity(), Dashboard.class);
                        SharedPrefManager.getInstance(getActivity()).clear();
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getActivity(), "Error, Periksa Kembali Jaringan Anda", Toast.LENGTH_LONG).show();
                    }
                });

                break;


        }
    }
    @Override
    public void onStart() {
        super.onStart();
        cekConnectivity();
    }

    private void cekConnectivity() {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Snackbar.make(getActivity().findViewById(R.id.accountFragment), "Anda Terhubung", Snackbar.LENGTH_LONG);
        } else {
            Snackbar.make(getActivity().findViewById(R.id.accountFragment), "Koneksi Terputus, Anda Tidak Terhubung Dengan Layanan Internet", Snackbar.LENGTH_LONG).show();

        }
    }


}
