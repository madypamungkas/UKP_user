package com.komsi.user_interface.Activities.Menu;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.komsi.user_interface.Activities.MainActivity;
import com.komsi.user_interface.Api.RetrofitClient;
import com.komsi.user_interface.R;
import com.komsi.user_interface.Storage.SharedPrefManager;
import com.komsi.user_interface.Adapter.LayananAdapter;
import com.komsi.user_interface.models.Model_Layanan;
import com.komsi.user_interface.models.Response_Layanan;
import com.komsi.user_interface.models.User;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class Layanan extends AppCompatActivity {
    private Dialog myDialog;
    private RecyclerView recyclerViewLayanan;
    private LayananAdapter adapter;
    private List<Model_Layanan> modelLayananList;
    private String token;
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layanan);
        loading = ProgressDialog.show(Layanan.this, null, "Sedang Memuat...", true, false);

        TextView back = (TextView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Layanan.this, MainActivity.class);
                startActivity(intent);
            }
        });
        myDialog = new Dialog(this);
        recyclerViewLayanan = findViewById(R.id.recyclerViewLayanan);
        recyclerViewLayanan.setLayoutManager(new LinearLayoutManager(this));

        /*Button detail1 = (Button) findViewById(R.id.bt_detail1);
        Button detail2 = (Button) findViewById(R.id.bt_detail2);
        Button detail3 = (Button) findViewById(R.id.bt_detail3);
        Button detail4 = (Button) findViewById(R.id.bt_detail4);
        Button detail5 = (Button) findViewById(R.id.bt_detail5);

        detail1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.setContentView(R.layout.pop_up_isi_biodata);
                Button batal = (Button) myDialog.findViewById(R.id.batal);
                batal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDialog.dismiss();
                    }
                });
                Button keluar = (Button) myDialog.findViewById(R.id.keluar);
                keluar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent keluar = new Intent(Layanan.this, DaftarKonsul.class);
                        myDialog.dismiss();
                        startActivity(keluar);
                    }
                });
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
        });
        detail2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.setContentView(R.layout.pop_up_isi_biodata);
                Button batal = (Button) myDialog.findViewById(R.id.batal);
                batal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDialog.dismiss();
                    }
                });
                Button keluar = (Button) myDialog.findViewById(R.id.keluar);
                keluar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent keluar = new Intent(Layanan.this, DaftarKonsul.class);
                        myDialog.dismiss();
                        startActivity(keluar);
                    }
                });
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
        });
        detail3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.setContentView(R.layout.pop_up_isi_biodata);
                Button batal = (Button) myDialog.findViewById(R.id.batal);
                batal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDialog.dismiss();
                    }
                });
                Button keluar = (Button) myDialog.findViewById(R.id.keluar);
                keluar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent keluar = new Intent(Layanan.this, DaftarKonsul.class);
                        myDialog.dismiss();
                        startActivity(keluar);
                    }
                });
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
        });
        detail4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.setContentView(R.layout.pop_up_isi_biodata);
                Button batal = (Button) myDialog.findViewById(R.id.batal);
                batal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDialog.dismiss();
                    }
                });
                Button keluar = (Button) myDialog.findViewById(R.id.keluar);
                keluar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent keluar = new Intent(Layanan.this, DaftarKonsul.class);
                        myDialog.dismiss();
                        startActivity(keluar);
                    }
                });
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
        });
        detail5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.setContentView(R.layout.pop_up_isi_biodata);
                Button batal = (Button) myDialog.findViewById(R.id.batal);
                batal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDialog.dismiss();
                    }
                });
                Button keluar = (Button) myDialog.findViewById(R.id.keluar);
                keluar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent keluar = new Intent(Layanan.this, DaftarKonsul.class);
                        myDialog.dismiss();
                        startActivity(keluar);
                    }
                });
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
        });
    }
    */
    }

    @Override
    protected void onStart() {
        super.onStart();
        User user  = SharedPrefManager.getInstance(this).getUser();
        token = "Bearer "+ user.getToken();
        retrofit2.Call<Response_Layanan> call = RetrofitClient.getInstance().getApi().getLayanan(token);
        call.enqueue(new Callback<Response_Layanan>() {
            @Override
            public void onResponse(retrofit2.Call<Response_Layanan> call, Response<Response_Layanan> response) {
                loading.dismiss();

                modelLayananList = response.body().getLayanan();
                adapter = new LayananAdapter(Layanan.this, modelLayananList);
                recyclerViewLayanan.setAdapter(adapter);

            }

            @Override
            public void onFailure(retrofit2.Call<Response_Layanan> call, Throwable t) {
                loading.dismiss();

            }
        });

    }
}
