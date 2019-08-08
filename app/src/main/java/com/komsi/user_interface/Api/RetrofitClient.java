package com.komsi.user_interface.Api;


import com.komsi.user_interface.Storage.SharedPrefManager;
import com.komsi.user_interface.models.Details;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
 //private static final String BASE_URL = "http://10.0.2.2:8000/api/";
    //private static final String BASE_URL = "http://psikologi.ridwan.info/psikolog-api/public/api/";
    private static final String BASE_URL = "http://psikologi.ridwan.info/api/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;
   
    private RetrofitClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }
    public Api getApi(){
        return retrofit.create(Api.class);
    }
}