package com.komsi.user_interface.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.komsi.user_interface.R;
import com.komsi.user_interface.models.Model_Layanan;

import java.util.List;

public class LayananPsikologAdapter extends RecyclerView.Adapter<LayananPsikologAdapter.LayananPsikologViewHolder> {
    private Context mCtx;
    private List<Model_Layanan> layananList;

    public LayananPsikologAdapter(Context mCtx, List<Model_Layanan> layananList) {
        this.mCtx = mCtx;
        this.layananList = layananList;
    }

    @Override
    public LayananPsikologViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.layanan_psikolog_list_layout, parent, false);
        return new LayananPsikologAdapter.LayananPsikologViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LayananPsikologViewHolder holder, int position) {
        final Model_Layanan layananModel = layananList.get(position);
        holder.namaLayanan.setText("> "+layananModel.getNama());
    }

    @Override
    public int getItemCount() {
        return layananList.size();
    }

    class  LayananPsikologViewHolder extends RecyclerView.ViewHolder{
        CheckBox checkLayanan;
        TextView namaLayanan;
        public LayananPsikologViewHolder(View itemView) {
            super(itemView);
            checkLayanan = itemView.findViewById(R.id.checkLayanan);
            namaLayanan = itemView.findViewById(R.id.namaLayanan);

        }
    }
}

