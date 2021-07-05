package com.shegi.idpendaki.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * created by shegi-developer on 14/09/20
 */
public class Apiclient {
    public  static final String BASE_URL = "https://shegidev.com/indonesiamountain/";
    public static Retrofit retrofit;

    public static Retrofit getApiclient(){

        if (retrofit == null){

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }

        return retrofit;
    }
}
