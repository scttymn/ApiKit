package com.ovenbits.request;


import com.android.volley.Response;

import java.util.Map;

public class Listener<T> implements Response.Listener<T> {

    @Override
    public void onResponse(T response) {
    }

    public void onResponse(T response, Map<String, String> headers) {
    }
}