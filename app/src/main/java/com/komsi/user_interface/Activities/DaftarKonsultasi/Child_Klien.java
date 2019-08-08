package com.komsi.user_interface.Activities.DaftarKonsultasi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.komsi.user_interface.Adapter.ChildAdapter;
import com.komsi.user_interface.Api.RetrofitClient;
import com.komsi.user_interface.R;
import com.komsi.user_interface.Storage.SharedPrefManager;
import com.komsi.user_interface.models.Model_Child;
import com.komsi.user_interface.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Child_Klien extends AppCompatActivity {
    private String token;
    private RecyclerView recyclerViewChild;
    private ChildAdapter adapter;
    private List<Model_Child> childModelslist;
    ProgressDialog loading;
    ScrollView recyclerView;
    LinearLayout noData;
    SwipeRefreshLayout swiperefresh;
    private int refresh_count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_klien);
        TextView back = (TextView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Child_Klien.super.onBackPressed();
            }
        });
        recyclerViewChild = findViewById(R.id.recyclerViewChild);
        recyclerViewChild.setLayoutManager(new LinearLayoutManager(this));
        loading = ProgressDialog.show(Child_Klien.this, null,
                "Sedang Memuat...", true, false);
        recyclerView =findViewById(R.id.recyclerView);
        noData = findViewById(R.id.noData);
        swiperefresh = findViewById(R.id.swiperefresh);
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataChild();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        getDataChild();

    }
    public void getDataChild(){
        User user  = SharedPrefManager.getInstance(this).getUser();
        token = "Bearer "+ user.getToken();
        SharedPreferences sharedPref = getSharedPreferences("DaftarKonsul", Context.MODE_PRIVATE);
        int id = sharedPref.getInt("idKlien", 0);
        Call<List<Model_Child>> call = RetrofitClient.getInstance().getApi().getChild(token, id);
        call.enqueue(new Callback<List<Model_Child>>() {
            @Override
            public void onResponse(Call<List<Model_Child>> call, Response<List<Model_Child>> response) {
                loading.dismiss();
                refresh_count++;
                swiperefresh.setRefreshing(false);
                if (refresh_count > 3){
                    refresh_count = 0;
                    swiperefresh.setRefreshing(false);
                }
                if(response.isSuccessful()){
                    childModelslist = response.body();
                    adapter = new ChildAdapter(Child_Klien.this, childModelslist);
                    recyclerViewChild.setAdapter(adapter);
                    if (childModelslist.size() > 0){
                        recyclerView.setVisibility(View.VISIBLE);
                        noData.setVisibility(View.GONE);
                    }
                    else{
                        LinearLayout background= findViewById(R.id.background);
                        background.setBackground(Drawable.createFromPath("#FFF"));
                        recyclerView.setVisibility(View.GONE);
                        noData.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Model_Child>> call, Throwable t) {
                loading.dismiss();
                refresh_count++;
                swiperefresh.setRefreshing(false);
                if (refresh_count > 3){
                    refresh_count = 0;
                    swiperefresh.setRefreshing(false);
                }
                Toast.makeText(Child_Klien.this, "Periksa Koneksi Internet Anda" ,Toast.LENGTH_LONG).show();
            }
        });
    }

    public void tambahChild(View view) {
        Intent intent= new Intent(Child_Klien.this, DaftarKonsul_Lain.class);
        startActivity(intent);
    }
}
