package com.komsi.user_interface.Fragments;


import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.komsi.user_interface.Activities.MainActivity;
import com.komsi.user_interface.Activities.Menu.BiodataUser;
import com.komsi.user_interface.Activities.Menu.DaftarKonsul;
import com.komsi.user_interface.Activities.Menu.JadwalKonsul;
import com.komsi.user_interface.Activities.Menu.KonfirmasiJadwal;
import com.komsi.user_interface.Activities.Menu.Layanan;
import com.komsi.user_interface.Activities.Notifikasi;
import com.komsi.user_interface.Api.RetrofitClient;
import com.komsi.user_interface.R;
import com.komsi.user_interface.models.Response_DetailKlien;
import com.komsi.user_interface.Adapter.RecyclerViewAdapterHome;
import com.komsi.user_interface.Storage.SharedPrefManager;
import com.komsi.user_interface.models.Details;
import com.komsi.user_interface.models.Klien;
import com.komsi.user_interface.models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.CONNECTIVITY_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    NotificationCompat.Builder notification;
    public static final int uniqeID = 45612;
    private ConnectivityManager connectivityManager;
    SwipeRefreshLayout swiperefresh;
    private int refresh_count = 0;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //listInfo();
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPrefManager.getInstance(getActivity()).getDetails();
        getDetails();
        // notifikasi
        notification = new NotificationCompat.Builder(getActivity());
        notification.setAutoCancel(true);

        notification.setSmallIcon(R.drawable.nav_icon_orders);
        notification.setTicker("ticker");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("Selamat Datang");
        notification.setContentText("Selamat Datang");

        Intent intent = new Intent(getActivity(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        // NotificationManager nm = (NotificationManager)getSystemServices

    }

    ImageView avatar;
    TextView name, nohp;
    Details details = SharedPrefManager.getInstance(getActivity()).getDetails();
    Klien klien = SharedPrefManager.getInstance(getActivity()).getKlienModel();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_home, container, false);
        connectivityManager = (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
        swiperefresh = fragmentView.findViewById(R.id.swiperefresh);
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDetails();
            }
        });
        SharedPreferences sharedPrefFCM = getActivity().getSharedPreferences("linkFoto", Context.MODE_PRIVATE);
        String link = sharedPrefFCM.getString("link", null);
        final String url = link + details.getFoto();
        avatar = (ImageView) fragmentView.findViewById(R.id.avatar);
        name = (TextView) fragmentView.findViewById(R.id.username);
        nohp = (TextView) fragmentView.findViewById(R.id.noHp);
        name.setText(details.getNama());
        nohp.setText("" + details.getNo_telepon());
        Picasso.with(getActivity()).load(url).error(R.drawable.menu_icon_user).into(avatar);
        LinearLayout daftar = (LinearLayout) fragmentView.findViewById(R.id.daftarKonsul);
        LinearLayout jadwal = (LinearLayout) fragmentView.findViewById(R.id.jadwalKonsul);
        LinearLayout layanan = (LinearLayout) fragmentView.findViewById(R.id.layananKonsul);
        LinearLayout biodata = (LinearLayout) fragmentView.findViewById(R.id.biodataUser);
        LinearLayout konfirmasi = (LinearLayout) fragmentView.findViewById(R.id.menuBaru);
        TextView notif2 = (TextView) fragmentView.findViewById(R.id.notif2);
        ImageView notif1 = (ImageView) fragmentView.findViewById(R.id.notif1);
        daftar.setOnClickListener(this);
        jadwal.setOnClickListener(this);
        layanan.setOnClickListener(this);
        biodata.setOnClickListener(this);
        notif2.setOnClickListener(this);
        notif1.setOnClickListener(this);
        konfirmasi.setOnClickListener(this);
        return fragmentView;

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.daftarKonsul:
                Intent daftar = new Intent(getActivity(), DaftarKonsul.class);
                startActivity(daftar);
                break;
            case R.id.jadwalKonsul:
                Intent jadwal = new Intent(getActivity(), JadwalKonsul.class);
                startActivity(jadwal);
                break;
            case R.id.menuBaru:
                Intent konfirmasi = new Intent(getActivity(), KonfirmasiJadwal.class);
                startActivity(konfirmasi);
                break;
            case R.id.layananKonsul:
                Intent layanan = new Intent(getActivity(), Layanan.class);
                startActivity(layanan);
                break;
            case R.id.biodataUser:
                Intent biodata = new Intent(getActivity(), BiodataUser.class);
                startActivity(biodata);
                break;
            case R.id.notif2:
                Intent notif = new Intent(getActivity(), Notifikasi.class);
                startActivity(notif);
                break;
            case R.id.notif1:
                Intent notif1 = new Intent(getActivity(), Notifikasi.class);
                startActivity(notif1);
                break;
        }
    }

    private void cekConnectivity() {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Snackbar.make(getActivity().findViewById(R.id.homeFragment), "Anda Terhubung", Snackbar.LENGTH_LONG);
        } else {
            Snackbar.make(getActivity().findViewById(R.id.homeFragment), "Koneksi Terputus, Anda Tidak Terhubung Dengan Layanan Internet", Snackbar.LENGTH_LONG).show();

        }
    }

    public void getDetails() {
        User user = SharedPrefManager.getInstance(getActivity()).getUser();
        String token;
        cekConnectivity();
        token = "Bearer " + user.getToken();
        retrofit2.Call<Response_DetailKlien> call = RetrofitClient.getInstance().getApi().getDetails(token);
        call.enqueue(new Callback<Response_DetailKlien>() {
            @Override
            public void onResponse(retrofit2.Call<Response_DetailKlien> call, Response<Response_DetailKlien> response) {
                SharedPrefManager.getInstance(getActivity()).getDetails();
                SharedPrefManager.getInstance(getActivity()).saveDetails(response.body().getDetails());
                refresh_count++;
                swiperefresh.setRefreshing(false);
                if (refresh_count > 3) {
                    refresh_count = 0;
                    swiperefresh.setRefreshing(false);
                }

            }
            @Override
            public void onFailure(retrofit2.Call<Response_DetailKlien> call, Throwable t) {
                refresh_count++;
                swiperefresh.setRefreshing(false);
                if (refresh_count > 3) {
                    refresh_count = 0;
                    swiperefresh.setRefreshing(false);
                }
            }
        });
    }


    public void listInfo() {
        final ArrayList<String> mImageUrls = new ArrayList<>();
        final ArrayList<String> mNames = new ArrayList<>();
        mImageUrls.add("10.0.2.2/psikolog-api-master/storage/app/images/330813306475681.jpg");
        mNames.add("Havasu Falls");

        mImageUrls.add("10.0.2.2/psikolog-api-master/storage/app/images/330813306475681.jpg");
        mNames.add("Trondheim");

        mImageUrls.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        mNames.add("Portugal");

        mImageUrls.add("https://i.redd.it/j6myfqglup501.jpg");
        mNames.add("Rocky Mountain National Park");


        mImageUrls.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mNames.add("Mahahual");

        mImageUrls.add("https://i.redd.it/k98uzl68eh501.jpg");
        mNames.add("Frozen Lake");


        mImageUrls.add("https://i.redd.it/glin0nwndo501.jpg");
        mNames.add("White Sands Desert");

        mImageUrls.add("https://i.redd.it/obx4zydshg601.jpg");
        mNames.add("Austrailia");

        mImageUrls.add("https://i.imgur.com/ZcLLrkY.jpg");
        mNames.add("Washington");

        mImageUrls.add("https://www.google.co.id/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwiV0MSRvffdAhXLq48KHUPRC-4QjRx6BAgBEAU&url=https%3A%2F%2Fmbtskoudsalg.com%2Fexplore%2Fadd-button-png%2F&psig=AOvVaw0JKrzRUwhKGFYnRzNCpd5H&ust=1539109843131073");
        mNames.add("Layanan Lain");




        final RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerView);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        final RecyclerViewAdapterHome adapter = new RecyclerViewAdapterHome(getContext(), mNames, mImageUrls);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);


    }
}