package com.komsi.user_interface.Adapter;


import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.komsi.user_interface.Activities.DetailActivity.DetailKonsultasi;
import com.komsi.user_interface.Activities.LoadData.Loading;
import com.komsi.user_interface.Activities.Menu.JadwalKonsul;
import com.komsi.user_interface.Activities.Menu.KonfirmasiJadwal;
import com.komsi.user_interface.AlarmTask.AlarmTask;
import com.komsi.user_interface.AlarmTask.TimerJadwalTask;
import com.komsi.user_interface.Api.RetrofitClient;
import com.komsi.user_interface.R;
import com.komsi.user_interface.Storage.SharedPrefManager;
import com.komsi.user_interface.models.Model_Jadwal;
import com.komsi.user_interface.models.User;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JadwalAdapter extends RecyclerView.Adapter<JadwalAdapter.JadwalViewHolder> {
    private Context mCtx;
    private List<Model_Jadwal> jadwalList;
    User user = SharedPrefManager.getInstance(mCtx).getUser();


    public JadwalAdapter(Context mCtx, List<Model_Jadwal> jadwalList) {
        this.mCtx = mCtx;
        this.jadwalList = jadwalList;


    }


    @Override
    public JadwalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.riwayat_konsul_list_layout, parent, false);
        return new JadwalViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final JadwalViewHolder holder, final int position) {
        final Model_Jadwal jadwal = jadwalList.get(position);
        holder.jenisLayanan.setText(jadwal.getLayanan().getNama());
        holder.namaKlien.setText("Nama Klien : " + jadwal.getKlien().getUser().getNama());
        holder.tanggal.setText(jadwal.getTanggal());
        holder.namaPsikolog.setText("Psikolog : " + jadwal.getPsikolog().getUser().getNama() + " " + jadwal.getPsikolog().getGelar());
        holder.status.setText("Status : " + jadwal.getStatus().getNama());

        SharedPreferences sharedPrefFCM = mCtx.getSharedPreferences("linkFoto", Context.MODE_PRIVATE);
        String link = sharedPrefFCM.getString("link", null);
        final String url = link + jadwal.getPsikolog().getUser().getFoto();
        Picasso.with(mCtx).load(url).error(R.drawable.menu_icon_user).into(holder.avatar);

        holder.bt_detail1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detail = new Intent(mCtx, DetailKonsultasi.class);
                detail.putExtra("namaKlien", " " + jadwal.getKlien().getUser().getNama());
                detail.putExtra("layanan", jadwal.getLayanan().getNama());
                detail.putExtra("psikolog", jadwal.getPsikolog().getUser().getNama());
                detail.putExtra("tanggal", jadwal.getTanggal());
                detail.putExtra("status", jadwal.getStatus().getNama());
                detail.putExtra("sesi", jadwal.getSesi().getNama());
                detail.putExtra("jam", jadwal.getSesi().getJam());
                detail.putExtra("keluhan", jadwal.getKeluhan());
                String ruangan = String.valueOf(jadwal.getRuangan_id()) + " a";
                if (jadwal.getRuangan_id() == 0) {
                    detail.putExtra("ruangan", "Menunggu Konfirmasi Admin");
                } else {
                    detail.putExtra("ruangan", jadwal.getRuangan().getNama());
                }
                mCtx.startActivity(detail);
            }
        });

        String[] waktu = jadwal.getUpdated_at().split(" ");
        String[] separateJam = waktu[1].split(":");
        final int jam = Integer.parseInt(separateJam[0]);
        final int menit = Integer.parseInt(separateJam[1]);
        final int detik = Integer.parseInt(separateJam[2]);

        Calendar cal = Calendar.getInstance();
        cal.getTime();
        cal.set(Calendar.HOUR_OF_DAY, jam);
        cal.set(Calendar.MINUTE, menit);
        cal.set(Calendar.SECOND, detik);

        cal.add(Calendar.MINUTE, 10);
        Date datelimit = cal.getTime();
        long dateAlarm = cal.getTimeInMillis();

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date newDate = datelimit;
        final String dateTime = dateFormat.format(datelimit);
        // holder.timeLimit.setText(dateTime);

        Calendar calnow = Calendar.getInstance();
        calnow.getTime();
        Date dateskrg = calnow.getTime();
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");
        Date newDate2 = dateskrg;
        long datenow = calnow.getTimeInMillis();
        holder.timeLimit.setVisibility(View.VISIBLE);


        // alarm Manager

        AlarmManager am = (AlarmManager) mCtx.getSystemService(mCtx.ALARM_SERVICE);
        Intent intent = new Intent(mCtx, TimerJadwalTask.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // coba di set flag

        // put extra
        intent.putExtra("idJadwal", jadwal.getId());
        SharedPreferences sharedPref = mCtx.getSharedPreferences("DataPsikolog", Context.MODE_PRIVATE);
        intent.putExtra("PsikologId", jadwal.getPsikolog_id());
        intent.putExtra("KlienId", jadwal.getKlien_id());
        PendingIntent pi = PendingIntent.getBroadcast(mCtx, jadwal.getId(), intent, 0);
        if (jadwal.getStatus_id() == 6) {
            am.set(AlarmManager.RTC, dateAlarm, pi);
        } else {
            // am.set(AlarmManager.RTC, , pi);
            //am.set(AlarmManager.RTC, 0, pi);
            am.cancel(pi);
            holder.timeLimit.setVisibility(View.GONE);
        }

//
        String tesTanggal2 = jadwal.getTanggal();
        final String[] separated1 = tesTanggal2.split(" ");
        final String[] separated12 = separated1[1].split("");
        int monthw = Integer.parseInt(separated1[1]);
        int monthw2 = 0;

        if (monthw==1){
        holder.warna.setBackgroundResource(R.color.yellow);
        }
        if (monthw==2){
            holder.warna.setBackgroundResource(R.color.yellow2);
        }
        if (monthw==3){
            holder.warna.setBackgroundResource(R.color.green);
        }
        if (monthw==4){
            holder.warna.setBackgroundResource(R.color.green2);
        }
        if (monthw==5){
            holder.warna.setBackgroundResource(R.color.purple);
        }
        if (monthw==6){
            holder.warna.setBackgroundResource(R.color.purple2);
        }
        if (monthw==7){
            holder.warna.setBackgroundResource(R.color.pink);
        }
        if (monthw==8){
            holder.warna.setBackgroundResource(R.color.pink2);
        }
        if (monthw==9){
            holder.warna.setBackgroundResource(R.color.gradStop);
        }
        if (monthw==10){
            holder.warna.setBackgroundResource(R.color.black);
        }
        if (monthw==11){
            holder.warna.setBackgroundResource(R.color.gradStart2);
        }
        if (monthw==12){
            holder.warna.setBackgroundResource(R.color.gradStart);
        }

        if (dateAlarm > datenow) {
            CountDownTimer cd = new CountDownTimer(dateAlarm - datenow, 1000) {
                @Override
                public void onTick(long l) {
                    holder.timer.setText(dateTime);
                    int hours = (int) (((l / 1000) / 3600) % 24);
                    int minutes = (int) ((l / 1000) / 60);
                    int seconds = (int) ((l / 1000) % 60);
                    String limitFormat = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
                    holder.timer.setText("00:" + limitFormat);
                }

                @Override
                public void onFinish() {
                    holder.timer.setText("Kedaluarsa");
                  /*  if (jadwal.getStatus_id() == 6) {
                        final String token = "Bearer " + user.getToken();
                        final int idJadwal = jadwal.getId();
                        final int psikologId = jadwal.getPsikolog_id();
                        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().updateJadwal12(token, idJadwal, psikologId, jadwal.getKlien_id(), 12);
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(mCtx, "Sukses Mengalihkan Psikolog", Toast.LENGTH_LONG).show();
//                                    Intent sukses = new Intent(mCtx, KonfirmasiJadwal.class);
//                                    mCtx.startActivity(sukses);
                                } else {
                                    Toast.makeText(mCtx, "Gagal Mengalihkan Psikolog", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(mCtx, "Terjadi Kesalahan Data", Toast.LENGTH_LONG).show();

                            }
                        });
                    } else {
                        holder.timer.setText("Kedaluarsa");
                    }*/
                }

            }.start();

        } else {
            holder.timer.setText("Kedaluarsa");
            final String token = "Bearer " + user.getToken();
            final int idJadwal = jadwal.getId();
            final int psikologId = jadwal.getPsikolog_id();
//                Call<ResponseBody> call = RetrofitClient.getInstance().getApi().updateJadwal12(token, idJadwal, psikologId, jadwal.getKlien_id(), 12);
//                call.enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        if (response.isSuccessful()) {
//                            //   Toast.makeText(mCtx, "Sukses Mengalihkan Psikolog", Toast.LENGTH_LONG).show();
////                            Intent sukses = new Intent(mCtx, KonfirmasiJadwal.class);
////                            mCtx.startActivity(sukses);
//                        } else {
//                            Toast.makeText(mCtx, "Gagal Mengalihkan Psikolog", Toast.LENGTH_LONG).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        Toast.makeText(mCtx, "Terjadi Kesalahan Data", Toast.LENGTH_LONG).show();
//
//                    }
//                });
        }




        if (jadwal.getStatus().getId() == 12) {
            am.cancel(pi);
            holder.layout_pengalihan.setVisibility(View.VISIBLE);
            holder.bt_tolak.setVisibility(View.VISIBLE);
            // holder.layout_pengalihan.setBackground(Drawable.createFromPath("#66B2FF"));
            final String token = "Bearer " + user.getToken();
            final int idJadwal = jadwal.getId();
            final int psikologId = jadwal.getPsikolog_id();
            holder.bt_alihkan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Call<ResponseBody> call = RetrofitClient.getInstance().getApi().updateJadwal5(token, idJadwal, psikologId, 5);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(mCtx, "Sukses Mengalihkan Psikolog", Toast.LENGTH_LONG).show();
                                Intent sukses = new Intent(mCtx, KonfirmasiJadwal.class);
                                mCtx.startActivity(sukses);
                            } else {
                                Toast.makeText(mCtx, "Gagal Mengalihkan Psikolog", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(mCtx, "Terjadi Kesalahan Data", Toast.LENGTH_LONG).show();

                        }
                    });
                }
            });

            holder.bt_tolak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Call<ResponseBody> call = RetrofitClient.getInstance().getApi().updateJadwal13(token, idJadwal, psikologId, jadwal.getKlien_id(), 13);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(mCtx, "Sukses Menghapus Jadwal", Toast.LENGTH_LONG).show();
                                Intent sukses = new Intent(mCtx, KonfirmasiJadwal.class);
                                mCtx.startActivity(sukses);
                            } else {
                                Toast.makeText(mCtx, "Gagal Menghapus Jadwal", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(mCtx, "Terjadi Kesalahan Data", Toast.LENGTH_LONG).show();

                        }
                    });

                }
            });


        } else {
            holder.layout_pengalihan.setVisibility(View.GONE);
        }

        if (jadwal.getStatus().getId() == 7) {
            am.cancel(pi);
            holder.bt_tolak.setVisibility(View.VISIBLE);
            String tesTanggal = jadwal.getTanggal();
            final String[] separated = tesTanggal.split(" ");
            final String[] separated2 = separated[1].split("");
            final int year = Integer.parseInt(separated[2]);
            final int day = Integer.parseInt(separated[0]);
            int month1 = Integer.parseInt(separated[1]);
            int month = 0;
            if (month1 < 10) {
                String m = String.valueOf(month1);
                String[] months = m.split("");
                month = Integer.parseInt(months[1]);
            } else {
                month = month1;
            }
            String dateKonsul = year + " " + day + " ";
            // set Alarm
            Calendar calendar2 = Calendar.getInstance();
            calendar2.set(year, month - 1, day, 6, 15, 0);
            Calendar calnow2 = Calendar.getInstance();
            calnow2.getTime();
            Date dateskrg2 = calnow2.getTime();
            int ynow2 = calnow2.get(Calendar.YEAR);
            int dnow2 = calnow2.get(Calendar.DAY_OF_MONTH);
            //int mnow = calnow.get(Calendar.MONTH);
            String dateAlarm2 = ynow2 + " " + dnow2 + " ";
            long datenow2 = calnow2.getTimeInMillis();
            AlarmManager am2 = (AlarmManager) mCtx.getSystemService(mCtx.ALARM_SERVICE);
            Intent intent2 = new Intent(mCtx, AlarmTask.class);
            PendingIntent pi2 = PendingIntent.getBroadcast(mCtx, jadwal.getId(), intent2, 0);
           // am2.set(AlarmManager.RTC, calendar2.getTimeInMillis(), pi2);
            Calendar dayLimit2 = Calendar.getInstance();
            dayLimit2.set(year, (month - 1), (day - 1), 6, 15, 0);
            final String token = "Bearer " + user.getToken();
            final int idJadwal = jadwal.getId();
            final int psikologId = jadwal.getPsikolog_id();
            holder.bt_tolak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Call<ResponseBody> call = RetrofitClient.getInstance().getApi().updateJadwal13(token, idJadwal, psikologId, jadwal.getKlien_id(), 13);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(mCtx, "Sukses Menghapus Jadwal", Toast.LENGTH_LONG).show();
                                Intent sukses = new Intent(mCtx, JadwalKonsul.class);
                                mCtx.startActivity(sukses);
                            } else {
                                Toast.makeText(mCtx, "Gagal Menghapus Jadwal", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(mCtx, "Terjadi Kesalahan Data", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });

            if (calnow2.getTime().after(dayLimit2.getTime())) {
                holder.bt_tolak.setVisibility(View.GONE);

            } else {
                holder.bt_tolak.setVisibility(View.VISIBLE);
            }
            if (dateAlarm2.equals(dateKonsul)) {
//                AlarmManager am = (AlarmManager) mCtx.getSystemService(mCtx.ALARM_SERVICE);
//                Intent intent = new Intent(mCtx, AlarmTask.class);
//                PendingIntent pi = PendingIntent.getBroadcast(mCtx, jadwal.getId(), intent, 0);
//                am.set(AlarmManager.RTC, calendar.getTimeInMillis(), pi);
            }

            holder.status.setText("Status : " + jadwal.getStatus().getNama());
            holder.bt_detail1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent detail = new Intent(mCtx, DetailKonsultasi.class);
                    detail.putExtra("namaKlien", " " + jadwal.getKlien().getUser().getNama());
                    detail.putExtra("layanan", jadwal.getLayanan().getNama());
                    detail.putExtra("psikolog", jadwal.getPsikolog().getUser().getNama());
                    detail.putExtra("tanggal", jadwal.getTanggal());
                    detail.putExtra("status", jadwal.getStatus().getNama());
                    detail.putExtra("sesi", jadwal.getSesi().getNama());
                    detail.putExtra("jam", jadwal.getSesi().getJam());
                    detail.putExtra("keluhan", jadwal.getKeluhan());
                    detail.putExtra("ruangan", "Menunggu Konfirmasi Admin");
                    mCtx.startActivity(detail);
                    //     Toast.makeText(mCtx, " " + day + year + month, Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return jadwalList.size();
    }

    class JadwalViewHolder extends RecyclerView.ViewHolder {
        TextView jenisLayanan, namaKlien, tanggal, status, namaPsikolog, timer;
        Button bt_detail1, bt_tolak, bt_alihkan;
        CircleImageView avatar;
        LinearLayout layout_pengalihan, timeLimit;
        android.support.v7.widget.CardView warna;

        public JadwalViewHolder(View itemView) {
            super(itemView);
            jenisLayanan = itemView.findViewById(R.id.namakonsul);
            namaKlien = itemView.findViewById(R.id.namaklien);
            namaPsikolog = itemView.findViewById(R.id.namaPsikolog);
            status = itemView.findViewById(R.id.status);
            tanggal = itemView.findViewById(R.id.tanggalKonsul);
            bt_detail1 = itemView.findViewById(R.id.bt_detail1);
            avatar = itemView.findViewById(R.id.avatar);
            layout_pengalihan = itemView.findViewById(R.id.layout_pengalihan);
            bt_tolak = itemView.findViewById(R.id.bt_tolak);
            bt_alihkan = itemView.findViewById(R.id.bt_alihkan);
            timeLimit = itemView.findViewById(R.id.timeLimit);
            timer = itemView.findViewById(R.id.timer);
            warna = itemView.findViewById(R.id.warna);


        }
    }

}
