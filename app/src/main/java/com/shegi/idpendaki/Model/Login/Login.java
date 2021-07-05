package com.shegi.idpendaki.Model.Login;

import com.google.gson.annotations.SerializedName;

public class Login{

	@SerializedName("data")
	private Data data;

	@SerializedName("success")
	private String success;

	@SerializedName("message")
	private String message;

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}

	public void setSuccess(String success){
		this.success = success;
	}

	public String getSuccess(){
		return success;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}
}