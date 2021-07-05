package com.shegi.idpendaki.Model;

import com.google.gson.annotations.SerializedName;

/**
 * created by shegi-developer on 18/09/20
 */

public class Value {
    @SerializedName("success") String success;
    @SerializedName("message") String message;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
