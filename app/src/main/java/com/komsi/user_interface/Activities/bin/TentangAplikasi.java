package com.komsi.user_interface.Activities.bin;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.komsi.user_interface.R;

import java.util.ArrayList;
public class TentangAplikasi extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenteng_aplikasi);
        ImageView about = (ImageView)findViewById(R.id.about_img);
        String url="https://i.imgur.com/ZcLLrkY.jpg";
        //Picasso.with(this).load(url).into(about);
        about.setImageURI(Uri.parse(url));
    }

}

