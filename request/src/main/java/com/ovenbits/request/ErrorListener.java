package com.ovenbits.request;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;

public class ErrorListener<T> implements Response.ErrorListener {
    private Class<T> mErrorClass;

    public ErrorListener() {
        mErrorClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        try {
            NetworkResponse response = error.networkResponse;
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            onErrorResponse(new Gson().fromJson(json, mErrorClass));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onErrorResponse(T errorObject) {

    }
}
