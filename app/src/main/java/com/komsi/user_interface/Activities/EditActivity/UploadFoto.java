package com.komsi.user_interface.Activities.EditActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.komsi.user_interface.Activities.LoadData.Loading;
import com.komsi.user_interface.Activities.MainActivity;
import com.komsi.user_interface.Api.RetrofitClient;
import com.komsi.user_interface.Api.UploadApi;
import com.komsi.user_interface.R;
import com.komsi.user_interface.Storage.SharedPrefManager;
import com.komsi.user_interface.models.Details;
import com.komsi.user_interface.models.Klien;
import com.komsi.user_interface.models.Response_DetailKlien;
import com.komsi.user_interface.models.User;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadFoto extends AppCompatActivity implements View.OnClickListener {
    Button bt_gallery, bt_uploadFoto;
    ImageView uploadFoto;
    private static final int IMG_REQUEST = 777;
    private Bitmap bitmap;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_foto);
        bt_gallery = (Button) findViewById(R.id.bt_gallery);
        bt_uploadFoto = (Button) findViewById(R.id.bt_uploadFoto);
        uploadFoto = (ImageView) findViewById(R.id.uploadFoto);

        bt_gallery.setOnClickListener(this);
        bt_uploadFoto.setOnClickListener(this);

        SharedPreferences sharedPrefFCM = getSharedPreferences("linkFoto", Context.MODE_PRIVATE);
        String link = sharedPrefFCM.getString("link", null);
        final String url = link + details.getFoto();
        uploadFoto = (ImageView) findViewById(R.id.uploadFoto);
        Picasso.with(this).load(url).error(R.drawable.menu_icon_user).into(uploadFoto);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_gallery:
                selectImage();
                break;
            case R.id.bt_uploadFoto:
                uploadImage();
                loading = ProgressDialog.show(this, null, "Sedang Memproses...", true, false);

                break;

        }

    }

    User user = SharedPrefManager.getInstance(this).getUser();
    final String token = "Bearer " + user.getToken();
    Details details = SharedPrefManager.getInstance(UploadFoto.this).getDetails();
    final int idUser = details.getId();


    private void uploadImage() {
        String image = imgToString();

        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().uploadImg(token, idUser, image);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(UploadFoto.this, "Berhasil Mengubah Foto", Toast.LENGTH_LONG).show();
                loading.dismiss();
                loadDetails(); }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(UploadFoto.this, "Gagal", Toast.LENGTH_LONG).show();
                loading.dismiss(); }
        }); }
    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST); }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            uploadFoto.setVisibility(View.VISIBLE);
            bt_uploadFoto.setEnabled(true);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                uploadFoto.setImageBitmap(bitmap);
                } catch (IOException e) {
                e.printStackTrace(); } }
    }

    private String imgToString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        bt_uploadFoto.setEnabled(true);
        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }

    public void loadDetails() {
        retrofit2.Call<Response_DetailKlien> call = RetrofitClient
                .getInstance()
                .getApi()
                .getDetails(token);
        call.enqueue(new retrofit2.Callback<Response_DetailKlien>() {
            @Override
            public void onResponse(retrofit2.Call<Response_DetailKlien> call, retrofit2.Response<Response_DetailKlien> response) {
                if (response.isSuccessful()) {
                    SharedPrefManager.getInstance(UploadFoto.this).saveDetails(response.body().getDetails());
                    Intent intent = new Intent(UploadFoto.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                } else {
                    Toast.makeText(UploadFoto.this, "Error, Periksa Kembali Jaringan Anda", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<Response_DetailKlien> call, Throwable t) {
                Toast.makeText(UploadFoto.this, "Error, Periksa Kembali Jaringan Anda", Toast.LENGTH_LONG).show();
            }
        });
    }
}
