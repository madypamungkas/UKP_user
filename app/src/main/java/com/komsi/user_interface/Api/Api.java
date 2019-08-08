package com.komsi.user_interface.Api;


import com.komsi.user_interface.models.DefaultModel;
import com.komsi.user_interface.models.JsonDefault;
import com.komsi.user_interface.models.Model_Child;
import com.komsi.user_interface.models.Response_DetailKlien;
import com.komsi.user_interface.models.Response_GantiPassword;
import com.komsi.user_interface.models.Response_Jadwal;
import com.komsi.user_interface.models.Response_Klien;
import com.komsi.user_interface.models.Response_Layanan;
import com.komsi.user_interface.models.Response_LayananPsikolog;
import com.komsi.user_interface.models.Response_Login;
import com.komsi.user_interface.models.Response_Psikolog;
import com.komsi.user_interface.models.Response_DaftarOrangLain;
import com.komsi.user_interface.models.Response_ForgotPassword;
import com.komsi.user_interface.models.Response_Sesi;
import com.komsi.user_interface.models.Response_BiodataUpdate;
import com.komsi.user_interface.models.SendEmailVerif_Model;

import java.util.List;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    @FormUrlEncoded
    @POST("register/klien")
    Call<JsonDefault> klien(@Header("Accept") String accept,
                            @Field("name") String name,
                            @Field("email") String email,
                            @Field("password") String password,
                            @Field("username") String username,
                            @Field("no_telepon") String no_telepon,
                            @Field("tanggal_lahir") String ttl,
                            @Field("kategori_id") int kategori,
                            @Field("level") String level
    );

    @FormUrlEncoded
    @POST("login/klien")
    Call<Response_Login> login(@Field("username") String username,
                               @Field("password") String password,
                               @Field("level") String level
    );

    @FormUrlEncoded
    @POST("pendaftar_lain")
    Call<Response_DaftarOrangLain> daftarLain(@Header("Authorization") String token,
                                              @Field("name") String name,
                                              @Field("password") String password,
                                              @Field("nik") String nik,
                                              @Field("level") String level,
                                              @Field("tanggal_lahir") String tanggal_lahir,
                                              @Field("jenis_kelamin") String jenis_kelamin,
                                              @Field("anak_ke") String anak_ke,
                                              @Field("jumlah_saudara") String jumlah_saudara,
                                              @Field("pendidikan_terakhir") String pendidikan_terakhir,
                                              @Field("parent_id") int parent_id,
                                              @Field("hub_pendaftar") String hub_pendaftar,
                                              @Field("fcm_token") String fcm_token,
                                              @Field("kategori_id") int kategori_id

                                              );

    @GET("details")
    Call<Response_DetailKlien> getDetails(@Header("Authorization") String token);

    @GET("klien/show/{id}")
    Call<Response_Klien> getKlien(@Header("Authorization") String token,
                                  @Path("id") int id);

    @GET("klien/jadwal")
    Call<Response_Jadwal> getJadwalResponse(@Header("Authorization") String token,
                                            @Query("klien_id") int klienId);

    @GET("klien/jadwalChild")
    Call<Response_Jadwal> getJadwalChild(@Header("Authorization") String token,
                                         @Query("parent_id") int parent_id);

    @GET("klien/riwayat")
    Call<Response_Jadwal> getRiwayat(@Header("Authorization") String token,
                                     @Query("klien_id") int klienId);

    @GET("klien/riwayatChild")
    Call<Response_Jadwal> getRiwayatChild(@Header("Authorization") String token,
                                          @Query("parent_id") int parent_id);

    @GET("klien/konfirmasi_konsultasiChild")
    Call<Response_Jadwal> getkonfirmasiChild(@Header("Authorization") String token,
                                             @Query("parent_id") int parent_id);

    @GET("klien/konfirmasi_konsultasi")
    Call<Response_Jadwal> getkonfirmasi(@Header("Authorization") String token,
                                        @Query("klien_id") int klienId);

    @GET("layanan")
    Call<Response_Layanan> getLayanan(@Header("Authorization") String token);

    @GET("layananPsikolog")
    Call<Response_Psikolog> getPsikolog(@Header("Authorization") String token,
                                        @Query("layanan_id") int layanan_id);

    @GET("sesi")
    Call<Response_Sesi> getSesi(@Header("Authorization") String token);


    @FormUrlEncoded
    @PUT("klien/update/{id}")
    Call<Response_BiodataUpdate> update(
            @Header("Authorization") String token,
            @Path("id") int id,
            @Field("name") String name,
            @Field("nik") String nik,
            @Field("tanggal_lahir") String tanggal_lahir,
            @Field("jenis_kelamin") String jenis_kelamin,
            @Field("alamat") String alamat,
            @Field("no_telepon") String no_telepon,
            @Field("anak_ke") String anak_ke,
            @Field("jumlah_saudara") String jumlah_saudara,
            @Field("pendidikan_terakhir") String pendidikan_terakhir,
            @Field("kategori_id") int kategori_id


    );

    @FormUrlEncoded
    @POST("jadwal/store")
    Call<ResponseBody> jadwalStore(@Header("Authorization") String token,
                                   @Field("tanggal") String tanggalJadwal,
                                   @Field("keluhan") String keluhanKlien,
                                   @Field("sesi_id") int sesi_id,
                                   @Field("layanan_id") int layanan_id,
                                   @Field("tes_id") int tes_id,
                                   @Field("ruangan_id") int ruangan_id,
                                   @Field("psikolog_id") int psikolog_id,
                                   @Field("klien_id") int klien_id

    );

    @GET("klien/show/{id}")
    Call<Response_DetailKlien> getDetailKlien(
            @Header("Authorization") String token,
            @Path("id") int id);

    @GET("klien/child")
    Call<List<Model_Child>> getChild(
            @Header("Authorization") String token,
            @Query("id") int id
    );

    @FormUrlEncoded
    @PUT("psikolog/update_user/{id}")
    Call<JsonDefault> updateUser(
            @Header("Accept") String accept,
            @Header("Authorization") String token,
            @Path("id") int id,
            @Field("username") String username,
            @Field("email") String email,
            @Field("email_verified_at") String email_verified_at,
            @Field("no_telepon") String phone
    );

    @FormUrlEncoded
    @PUT("ganti_password/{id}")
    Call<Response_GantiPassword> updatePass(
            @Header("Authorization") String token,
            @Path("id") int id,
            @Field("password_lama") String password_lama,
            @Field("password_baru") String password_baru,
            @Field("konfirmasi") String konfirmasi

    );

    @FormUrlEncoded
    @POST("forgot/password")
    Call<Response_ForgotPassword> forgotPassword(
            @Field("email") String email
    );

    @FormUrlEncoded
    @PUT("update_token/{id}")
    Call<ResponseBody> updateFCM(
            @Header("Authorization") String token,
            @Path("id") int id,
            @Field("fcm_token") String fcm_token
    );

    @FormUrlEncoded
    @PUT("psikolog/update/{id}")
    Call<ResponseBody> uploadImg(
            @Header("Authorization") String token,
            @Path("id") int id,
            @Field("foto") String foto
    );

    @GET("email/resend")
    Call<SendEmailVerif_Model> getSendVerif(@Header("Authorization") String token);

    @GET("email/verify")
    Call<SendEmailVerif_Model> getStatusVerif(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("email/verify")
    Call<SendEmailVerif_Model> postCodeVerif(@Header("Authorization") String token,
                                             @Field("verification_code") String code
    );

    @FormUrlEncoded
    @POST("jadwal/getSesi")
    Call<DefaultModel> validasiJadwal(@Header("Authorization") String token,
                                      @Field("tanggal") String tanggalJadwal,
                                      @Field("keluhan") String keluhanKlien,
                                      @Field("sesi_id") int sesi_id,
                                      @Field("layanan_id") int layanan_id,
                                      @Field("psikolog_id") int psikolog_id,
                                      @Field("klien_id") int klien_id,
                                      @Field("status_id") int status_id

    );

    @FormUrlEncoded
    @PUT("jadwal/updatePsikolog/{id}")
    Call<ResponseBody> updateJadwal(
            @Header("Authorization") String token,
            @Path("id") int idJadwal,
            @Field("psikolog_id") int psikolog_id,
            @Field("status_id") int status_id
    );

    @FormUrlEncoded
    @PUT("jadwal/updatePsikolog5/{id}")
    Call<ResponseBody> updateJadwal5(
            @Header("Authorization") String token,
            @Path("id") int idJadwal,
            @Field("psikolog_id") int psikolog_id,
            @Field("status_id") int status_id
    );

    @FormUrlEncoded
    @PUT("jadwal/updatePsikolog7/{id}")
    Call<ResponseBody> updateJadwal7(
            @Header("Authorization") String token,
            @Path("id") int idJadwal,
            @Field("psikolog_id") int psikolog_id,
            @Field("klien_id") int klien_id,
            @Field("status_id") int status_id
    );

    @FormUrlEncoded
    @PUT("jadwal/updatePsikolog10/{id}")
    Call<ResponseBody> updateJadwal10(
            @Header("Authorization") String token,
            @Path("id") int idJadwal,
            @Field("psikolog_id") int psikolog_id,
            @Field("klien_id") int klien_id,
            @Field("status_id") int status_id
    );

    @FormUrlEncoded
    @PUT("jadwal/updatePsikolog12/{id}")
    Call<ResponseBody> updateJadwal12(
            @Header("Authorization") String token,
            @Path("id") int idJadwal,
            @Field("psikolog_id") int psikolog_id,
            @Field("klien_id") int klien_id,
            @Field("status_id") int status_id
    );
    @FormUrlEncoded
    @PUT("jadwal/updatePsikolog13/{id}")
    Call<ResponseBody> updateJadwal13(
            @Header("Authorization") String token,
            @Path("id") int idJadwal,
            @Field("psikolog_id") int psikolog_id,
            @Field("klien_id") int klien_id,
            @Field("status_id") int status_id
    );

    @GET("detail_psikolog/{id}")
    Call<Response_LayananPsikolog> getLayananPsikolog(@Header("Authorization") String token,
                                                      @Path("id") int idPsikolog);

}

