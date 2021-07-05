package com.shegi.idpendaki.API;

import com.shegi.idpendaki.Model.Getmountain;
import com.shegi.idpendaki.Model.Login.Login;
import com.shegi.idpendaki.Model.Value;
import com.shegi.idpendaki.Modelprov.Modelpro;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * created by shegi-developer on 14/09/20
 */
public interface Apiinterface {

    @GET("getdatamountain.php")
    Call<List<Getmountain>> getMountain(@Query("key") String key);

    @FormUrlEncoded
    @POST("userregis.php")
    Call<Value> Save(
            @Field("nama") String nama,
            @Field("email") String email,
            @Field("pass") String pass
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<Login> Login(
            @Field("email") String email,
            @Field("pass") String pass
    );

    @FormUrlEncoded
    @POST("getprovince.php")
    Call<List<Modelpro>> getProv(@Field("prov_gunung") String prov_gunung);

    @FormUrlEncoded
    @POST("getmdpl.php")
    Call<List<Modelpro>> getMdpl(@Field("mdpl") String mdpl);

}
