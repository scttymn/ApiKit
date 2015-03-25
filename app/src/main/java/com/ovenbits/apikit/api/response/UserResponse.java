package com.ovenbits.apikit.api.response;


import com.google.gson.annotations.SerializedName;
import com.ovenbits.apikit.api.models.User;

public class UserResponse {
    @SerializedName("message")
    private String mMessage;
    @SerializedName("user")
    private User mUser;

    public String getMessage() {
        return mMessage;
    }

    public User getUser() {
        return mUser;
    }
}
