package com.shegi.idpendaki.Model.Login;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("nama")
	private String nama;

	@SerializedName("pass")
	private String pass;

	@SerializedName("id")
	private String id;

	@SerializedName("email")
	private String email;

	@SerializedName("totalgunung")
	private String totalgunung;

	public String getTotalgunung() {
		return totalgunung;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setPass(String pass){
		this.pass = pass;
	}

	public String getPass(){
		return pass;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}
}