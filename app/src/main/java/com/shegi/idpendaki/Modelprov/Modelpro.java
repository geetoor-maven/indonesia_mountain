package com.shegi.idpendaki.Modelprov;

/**
 * created by shegi-developer on 19/11/20
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Modelpro {

    @SerializedName("nama_gunung")
    @Expose
    private String namaGunung;
    @SerializedName("gambar")
    @Expose
    private String gambar;
    @SerializedName("kab_gunung")
    @Expose
    private String kabGunung;
    @SerializedName("prov_gunung")
    @Expose
    private String provGunung;
    @SerializedName("mdpl")
    @Expose
    private String mdpl;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longtitude")
    @Expose
    private String longtitude;

    public String getNamaGunung() {
        return namaGunung;
    }

    public String getGambar() {
        return gambar;
    }

    public String getKabGunung() {
        return kabGunung;
    }

    public String getProvGunung() {
        return provGunung;
    }

    public String getMdpl() {
        return mdpl;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }


}

