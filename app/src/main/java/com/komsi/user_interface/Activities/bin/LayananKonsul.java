package com.komsi.user_interface.Activities.bin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.komsi.user_interface.R;

public class LayananKonsul extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layanan_konsul);

        final CardView rekomendasiSekolah = (CardView)findViewById(R.id.rekomendasiSekolah);
        final CardView desRekomendasi = (CardView)findViewById(R.id.des_rekomendasiSekolah);
        //final ImageView check1 =(ImageView)findViewById(R.id.check1);

        final CardView minatBakatSekolah=(CardView)findViewById(R.id.minatBakatSekolah);
        final CardView desMinatBakatSekolah=(CardView)findViewById(R.id.des_minatBakatSekolah);
        //final ImageView check2 =(ImageView)findViewById(R.id.check2);

        final CardView minatBakatKerja=(CardView)findViewById(R.id.minatBakatKerja);
        final CardView desMinatBakatKerja=(CardView)findViewById(R.id.des_minatBakatKerja);
        //final ImageView check3 =(ImageView)findViewById(R.id.check3);

        final CardView konsulPribadi=(CardView)findViewById(R.id.konsulPibadi);
        final LinearLayout desKonsulPribadi=(LinearLayout)findViewById(R.id.des_konsulPribadi);
        //final ImageView check4 =(ImageView)findViewById(R.id.check4);

        final CardView konsulAnak=(CardView)findViewById(R.id.konsulAnak);
        final CardView desKonsulAnak=(CardView)findViewById(R.id.des_konsulAnak);
        //final ImageView check5 =(ImageView)findViewById(R.id.check5);

        final CardView konsulDewasa=(CardView)findViewById(R.id.konsulDewasa);
        final CardView desKonsulDewasa=(CardView)findViewById(R.id.des_konsulDewasa);
        //final ImageView check6 =(ImageView)findViewById(R.id.check6);
        rekomendasiSekolah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                desRekomendasi.setVisibility(view.VISIBLE);
          //      check1.setVisibility(view.VISIBLE);
                desMinatBakatSekolah.setVisibility(View.GONE);
                desMinatBakatKerja.setVisibility(View.GONE);
                desKonsulPribadi.setVisibility(View.GONE);
                desKonsulAnak.setVisibility(View.GONE);
                desKonsulDewasa.setVisibility(View.GONE);
            }
        });
        minatBakatSekolah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                desRekomendasi.setVisibility(view.GONE);
            //    check1.setVisibility(view.GONE);
                desMinatBakatSekolah.setVisibility(View.VISIBLE);
              //  check2.setVisibility(view.VISIBLE);
                desMinatBakatKerja.setVisibility(View.GONE);
                //check3.setVisibility(view.GONE);
                desKonsulPribadi.setVisibility(View.GONE);
                //check4.setVisibility(view.GONE);
                desKonsulAnak.setVisibility(View.GONE);
                //check5.setVisibility(view.GONE);
                desKonsulDewasa.setVisibility(View.GONE);
                //check6.setVisibility(view.GONE);
            }
        });
        minatBakatKerja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                desRekomendasi.setVisibility(view.GONE);
                //check1.setVisibility(view.GONE);
                desMinatBakatSekolah.setVisibility(View.GONE);
                //check2.setVisibility(view.GONE);
                desMinatBakatKerja.setVisibility(View.VISIBLE);
                //check3.setVisibility(view.VISIBLE);
                desKonsulPribadi.setVisibility(View.GONE);
                //check4.setVisibility(view.GONE);
                desKonsulAnak.setVisibility(View.GONE);
                //check5.setVisibility(view.GONE);
                desKonsulDewasa.setVisibility(View.GONE);
                //check6.setVisibility(view.GONE);
            }
        });
        konsulPribadi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                desRekomendasi.setVisibility(view.GONE);
                //check1.setVisibility(view.GONE);
                desMinatBakatSekolah.setVisibility(View.GONE);
               // check2.setVisibility(view.GONE);
                desMinatBakatKerja.setVisibility(View.GONE);
                //check3.setVisibility(view.GONE);
                desKonsulPribadi.setVisibility(View.VISIBLE);
                //check4.setVisibility(view.VISIBLE);
                desKonsulAnak.setVisibility(View.GONE);
                //check5.setVisibility(view.GONE);
                desKonsulDewasa.setVisibility(View.GONE);
                //check6.setVisibility(view.GONE);
            }
        });
        konsulAnak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                desRekomendasi.setVisibility(view.GONE);
                //check1.setVisibility(view.GONE);
                desMinatBakatSekolah.setVisibility(View.GONE);
                //check2.setVisibility(view.GONE);
                desMinatBakatKerja.setVisibility(View.GONE);
                //check3.setVisibility(view.GONE);
                desKonsulPribadi.setVisibility(View.VISIBLE);
                //check4.setVisibility(view.VISIBLE);
                desKonsulAnak.setVisibility(View.VISIBLE);
                //check5.setVisibility(view.VISIBLE);
                desKonsulDewasa.setVisibility(View.GONE);
                //check6.setVisibility(view.GONE);
            }
        });
        konsulDewasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                desRekomendasi.setVisibility(view.GONE);
                //check1.setVisibility(view.GONE);
                desMinatBakatSekolah.setVisibility(View.GONE);
                //check2.setVisibility(view.GONE);
                desMinatBakatKerja.setVisibility(View.GONE);
                //check3.setVisibility(view.GONE);
                desKonsulPribadi.setVisibility(View.VISIBLE);
                //check4.setVisibility(view.VISIBLE);
                desKonsulAnak.setVisibility(View.GONE);
                //check5.setVisibility(view.GONE);
                desKonsulDewasa.setVisibility(View.VISIBLE);
                //check6.setVisibility(view.VISIBLE);
            }
        });
    }
}
