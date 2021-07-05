package com.shegi.idpendaki.Model;

import com.google.gson.annotations.SerializedName;

/**
 * created by shegi-developer on 14/09/20
 */
public class Getmountain {
    @SerializedName("id") private int id;
    @SerializedName("nama_gunung") private String nama_gunung;
    @SerializedName("gambar") private String gambar;
    @SerializedName("alamat_gunung") private String alamat_gunung;
    @SerializedName("kab_gunung") private String kab_gunung;
    @SerializedName("prov_gunung") private String prov_gunung;
    @SerializedName("mdpl") private String mdpl;
    @SerializedName("latitude") private String latitude;
    @SerializedName("longtitude") private String longtitude;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama_gunung() {
        return nama_gunung;
    }

    public void setNama_gunung(String nama_gunung) {
        this.nama_gunung = nama_gunung;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getAlamat_gunung() {
        return alamat_gunung;
    }

    public void setAlamat_gunung(String alamat_gunung) {
        this.alamat_gunung = alamat_gunung;
    }

    public String getKab_gunung() {
        return kab_gunung;
    }

    public void setKab_gunung(String kab_gunung) {
        this.kab_gunung = kab_gunung;
    }

    public String getProv_gunung() {
        return prov_gunung;
    }

    public void setProv_gunung(String prov_gunung) {
        this.prov_gunung = prov_gunung;
    }

    public String getMdpl() {
        return mdpl;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }


}
