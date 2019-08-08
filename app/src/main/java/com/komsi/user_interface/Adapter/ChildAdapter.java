package com.komsi.user_interface.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.komsi.user_interface.Activities.DaftarKonsultasi.DaftarKonsul_pilihLayanan2;
import com.komsi.user_interface.Activities.DetailActivity.DetailChild;
import com.komsi.user_interface.R;
import com.komsi.user_interface.models.Model_Child;

import java.util.List;

public class  ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ChildViewHolder>{
    private Context mCtx;
    private List<Model_Child> childList;
//    User user = SharedPrefManager.getInstance(mCtx).getUser();

    public ChildAdapter(Context mCtx, List<Model_Child> childList) {
        this.mCtx = mCtx;
        this.childList = childList;

}


    @Override
    public ChildViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.child_list_layout, parent, false);
        return new ChildViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ChildViewHolder holder, int position) {
        final Model_Child childModels = childList.get(position);
        holder.namaChild.setText(childModels.getUser().getNama());
        holder.jkChild.setText(childModels.getUser().getJenis_kelamin());
        holder.hubunganChild.setText(childModels.getHub_pendaftar());

        holder.pilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mCtx, psikologModelList.get(position).getName() + "\n" +
//                        psikologModelList.get(position).getJenis_kelamin() + "\n" + "Keahlian Psikolog" + "\n" +
//                        "Keahlian 1" , Toast.LENGTH_SHORT).show();
                //myDialog.setContentView(R.layout.pop_up_detail_layanan);
                Intent intent = new Intent(mCtx, DetailChild.class);
                intent.putExtra("namaChild",childModels.getUser().getNama());
                intent.putExtra("hubungan",childModels.getHub_pendaftar());
                intent.putExtra("jenisKelaminChild",childModels.getUser().getJenis_kelamin());
                intent.putExtra("anakke",Integer.toString(childModels.getAnak_ke()));
                intent.putExtra("dari",  Integer.toString(childModels.getJumlah_saudara()));
                intent.putExtra("pendidikan",childModels.getPendidikan_terakhir());
                intent.putExtra("ttlChild",childModels.getUser().getTanggal_lahir());
                intent.putExtra("idKlien", childModels.getId());
                mCtx.startActivity(intent);


            }
        });
        holder.bt_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.bt_batal.setVisibility(View.VISIBLE);
                holder.bt_daftar.setVisibility(View.GONE);
                holder.bt_lanjutDaftar.setVisibility(View.VISIBLE);


            }
        });
        holder.bt_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.bt_batal.setVisibility(View.GONE);
                holder.bt_daftar.setVisibility(View.VISIBLE);
                holder.bt_lanjutDaftar.setVisibility(View.GONE);
            }
        });
        holder.bt_lanjutDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPrefDaftarLain = mCtx.getSharedPreferences("DaftarKonsul_lain", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPrefDaftarLain.edit();
                editor.putString("namaKlien", childModels.getUser().getNama());
                editor.putInt("idKlien", childModels.getId());
                editor.putString("tanggalLahir", childModels.getUser().getTanggal_lahir());
                editor.putString("anakke",  Integer.toString(childModels.getAnak_ke()));
                editor.putString("jumlahSaudara",  Integer.toString(childModels.getJumlah_saudara()));
                editor.putString("hubungan", childModels.getHub_pendaftar());
                editor.putString("pendidikan", childModels.getPendidikan_terakhir());
                editor.putString("jenisKelamin", childModels.getUser().getJenis_kelamin());
                editor.apply();

                if(sharedPrefDaftarLain.getInt("idKlien", 0) != 0){
                    Intent lanjut = new Intent(view.getContext(), DaftarKonsul_pilihLayanan2.class);
                    mCtx.startActivity(lanjut);
                }
                else{
                    Toast.makeText(mCtx, "Gagal Menambahkan Klien", Toast.LENGTH_LONG).show();

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return childList.size();
    }

    public class ChildViewHolder extends RecyclerView.ViewHolder{
        TextView namaChild, jkChild, hubunganChild;
        String layanan ;
        int idLayanan;
        Button pilih, bt_daftar, bt_batal, bt_lanjutDaftar;;
        public ChildViewHolder(View itemView) {
            super(itemView);
            namaChild = itemView.findViewById(R.id.namaChild);
            jkChild = itemView.findViewById(R.id.jkChild);
            hubunganChild = itemView.findViewById(R.id.hubunganChild);
            pilih = itemView.findViewById(R.id.pilih);
            bt_daftar = itemView.findViewById(R.id.bt_daftar);
            bt_batal = itemView.findViewById(R.id.bt_batal);
            bt_lanjutDaftar = itemView.findViewById(R.id.bt_lanjutDaftar);
        }
    }
}
