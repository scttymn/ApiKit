package com.ovenbits.apikit.api.response;


import com.google.gson.annotations.SerializedName;

public class ErrorResponse {
    @SerializedName("message")
    private String mMessage;

    public String getMessage() {
        return mMessage;
    }
}
