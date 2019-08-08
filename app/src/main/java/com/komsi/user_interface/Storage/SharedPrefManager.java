package com.komsi.user_interface.Storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.komsi.user_interface.models.Details;
import com.komsi.user_interface.models.Model_GantiPassword;
import com.komsi.user_interface.models.Model_Jadwal;
import com.komsi.user_interface.models.Klien;
import com.komsi.user_interface.models.User;

public class SharedPrefManager {

    public static final String SHARED_PREF_NAME = "user_shared_pref";
    private static SharedPrefManager mInstance;
    private Context mCtx;

    private SharedPrefManager(Context mCtx){
        this.mCtx= mCtx;

    }

    public static synchronized SharedPrefManager getInstance(Context mCtx){
        if(mInstance == null){
            mInstance = new SharedPrefManager(mCtx);
        }
        return mInstance;

    }
    public void saveUser(User user){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("id", user.getId());
        editor.putString("name", user.getName());
        editor.putString("username_id", user.getUsername_id());
        editor.putString("email", user.getEmail());
        editor.putString("token", user.getToken());
        editor.apply();
    }
    public void saveDetails(Details details){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("id", details.getId());
        editor.putString("name", details.getNama());
        editor.putString("email", details.getEmail());
        editor.putString("email_verified_at", details.getEmail_verified_at());
        //editor.putString("password", details.getPassword());
        //editor.putString("remember_token", details.getRemember_token());
        editor.putString("level", details.getLevel());
        editor.putString("username", details.getUsername());
        editor.putString("nik", details.getNik());
        editor.putString("tanggal_lahir", details.getTanggal_lahir());
        editor.putString("jenis_kelamin", details.getJenis_kelamin());
        editor.putString("alamat", details.getAlamat());
        editor.putString("no_telepon", details.getNo_telepon());
        editor.putString("foto", details.getFoto());
        editor.putString("isActive", details.getIsActive());
        editor.putString("fcm_token", details.getFcm_token());
        editor.putString("created_at", details.getCreated_at());
        editor.putString("updated_at", details.getUpdated_at());
        editor.putString("deleted_at", details.getDeleted_at());
        editor.apply();

    }
    public void saveStatusGantiPassword(Model_GantiPassword modelGantiPassword){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("status", modelGantiPassword.getStatus());
        editor.putString("sukses", modelGantiPassword.getStatus());

        editor.apply();

    }
    public Model_GantiPassword getGantiPass(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Model_GantiPassword(
                sharedPreferences.getString("status", null),
                sharedPreferences.getString("sukses", null)

        );
    }


    public void saveKlien(Klien klien){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("id", klien.getKlienId());
        editor.putString("pendidikan_terakhir", klien.getPendidikan_terakhir());
        editor.putInt("anak_ke", klien.getAnak_ke());
        editor.putInt("jumlah_saudara", klien.getJumlah_saudara());
        editor.putInt("user_id", klien.getUser_id());
        editor.putString("created_at", klien.getCreated_at());
        editor.putString("updated_at", klien.getUpdated_at());
        editor.putString("deleted_at", klien.getDeleted_at());
        editor.putInt("kategori_id", klien.getKategori_id());
        editor.putInt("parent_id", klien.getParent_id());
        editor.putString("hub_pendaftar", klien.getHub_pendaftar());

        editor.apply();

    }
    public void saveRiwayat(Model_Jadwal modelJadwal){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("id", modelJadwal.getId());
        editor.putString("tanggal", modelJadwal.getTanggal());
        editor.putInt("sesi_id", modelJadwal.getSesi_id());
        editor.putInt("klien_id", modelJadwal.getKlien_id());
        editor.putString("keluhan", modelJadwal.getKeluhan());
        editor.putInt("layanan_id", modelJadwal.getLayanan_id());
        editor.putInt("tes_id", modelJadwal.getTes_id());
        editor.putInt("ruangan_id", modelJadwal.getRuangan_id());
        editor.putInt("psikolog_id", modelJadwal.getPsikolog_id());
        editor.putInt("status_id", modelJadwal.getStatus_id());
        editor.putString("created_at", modelJadwal.getCreated_at());
        editor.putString("updated_at", modelJadwal.getUpdated_at());
        editor.putString("deleted_at", modelJadwal.getDeleted_at());
        editor.apply();
    }
    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
       if(sharedPreferences.getString("username_id",null) !=null)
           return true;
       return false;
    }
    public User getUser(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("name", null),
                sharedPreferences.getString("username_id", null),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("token", null)

        );
    }
    public Details getDetails(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Details(
                sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("name", null),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("email_verified_at", null),
                //sharedPreferences.getString("password", null),
                //sharedPreferences.getString("remember_token", null),
                sharedPreferences.getString("level", null),
                sharedPreferences.getString("username", null),
                sharedPreferences.getString("nik", null),
                sharedPreferences.getString("tanggal_lahir", null),
                sharedPreferences.getString("jenis_kelamin", null),
                sharedPreferences.getString("alamat", null),
                sharedPreferences.getString("no_telepon", null),
                sharedPreferences.getString("foto", null),
                sharedPreferences.getString("isActive", null),
                sharedPreferences.getString("fcm_token", null),
                sharedPreferences.getString("created_at", null),
                sharedPreferences.getString("updated_at", null),
                sharedPreferences.getString("deleted_at", null)

        );
    }


    public Klien getKlienModel(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Klien(
            sharedPreferences.getInt("id", -1),
            sharedPreferences.getString("pendidikan_terakhir", null),
            sharedPreferences.getInt("anak_ke", -1),
            sharedPreferences.getInt("jumlah_saudara", -1),
            sharedPreferences.getInt("user_id", -1),
            sharedPreferences.getString("created_at", null),
            sharedPreferences.getString("updated_at", null),
            sharedPreferences.getString("deleted_at", null),
            sharedPreferences.getInt("kategori_id", -1),
            sharedPreferences.getInt("parent_id", -1),
            sharedPreferences.getString("hub_pendaftar", null)
        );
    }

    public void setToken(String token){
        SharedPreferences sharedPref = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("fcm_token", token);
        editor.apply();
    }
    public void setLink(String link){
        SharedPreferences sharedPref = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("linkfoto", link);
        editor.apply();
    }
    public void getLink(){
        SharedPreferences sharedPref = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        sharedPref.getString("linkfoto", null);

    }

    public void getToken(String token){
        SharedPreferences sharedPref = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        sharedPref.getString("fcm_token", null);

    }

    public void clear(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
