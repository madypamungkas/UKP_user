package com.komsi.user_interface.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.komsi.user_interface.Activities.DetailActivity.DetailLayanan;
import com.komsi.user_interface.R;
import com.komsi.user_interface.models.Model_Layanan;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LayananAdapter  extends RecyclerView.Adapter<LayananAdapter.LayananViewHolder>{
    private Context mCtx;
    private List<Model_Layanan> layananList;

    public LayananAdapter(Context mCtx, List<Model_Layanan> layananList) {
        this.mCtx = mCtx;
        this.layananList = layananList;
    }

    @Override
    public LayananViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.layanan_listlayout, parent, false);
        return new LayananViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LayananViewHolder holder, final int position) {
        final Model_Layanan layanan = layananList.get(position);

        holder.namaLayanan.setText(layanan.getNama());
        holder.deskripsiLayanan.setText(layanan.getDeskripsi());
        holder.hargaLayanan.setText("Rp "+layanan.getHarga()+",-");
        holder.bt_detail1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mCtx, DetailLayanan.class);

                intent.putExtra("namaLayanan", layanan.getNama());
                intent.putExtra("foto", layanan.getFoto());
                intent.putExtra("deskripsi", layanan.getDeskripsi());
                intent.putExtra("harga", layanan.getHarga());
                mCtx.startActivity(intent);

    }
});
        SharedPreferences sharedPref = mCtx.getSharedPreferences("linkFoto", Context.MODE_PRIVATE);
        String link = sharedPref.getString("link", null);
        Picasso.with(mCtx).load(link+layanan.getFoto()).error(R.drawable.konsul)
                .into(holder.check);

    }

    @Override
    public int getItemCount() {
        return layananList.size();
    }

    class LayananViewHolder extends RecyclerView.ViewHolder{

        TextView namaLayanan, deskripsiLayanan, hargaLayanan;
        Button bt_detail1;
        ImageView check;

        public LayananViewHolder(View itemView) {
            super(itemView);
            namaLayanan = itemView.findViewById(R.id.namaLayanan);
            deskripsiLayanan = itemView.findViewById(R.id.deskripsiLayanan);
            hargaLayanan = itemView.findViewById(R.id.hargaLayanan);
            bt_detail1 = itemView.findViewById(R.id.bt_detail1);
            check = itemView.findViewById(R.id.check);
        }
    }


}

