package com.ovenbits.apikit.api;

import android.content.Context;
import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.ovenbits.apikit.R;
import com.ovenbits.apikit.api.response.UserResponse;
import com.ovenbits.request.Listener;
import com.ovenbits.request.RequestBuilder;

public class ApiManager {
    private final Context mContext;
    private final RequestQueue mRequestQueue;
    private final Uri mBaseUri;

    public ApiManager(Context context, RequestQueue queue) {
        mContext = context;
        mRequestQueue = queue;
        mBaseUri = Uri.parse(context.getString(R.string.api_base_url));
    }

    public Request<?> signIn(String email, String password,
                             Listener<UserResponse> listener,
                             Response.ErrorListener errorListener) {

        Uri uri = mBaseUri.buildUpon()
                .appendPath(mContext.getString(R.string.sign_in_endpoint))
                .build();

        RequestBuilder builder = new RequestBuilder(Request.Method.POST, uri.toString(), UserResponse.class, listener, errorListener)
                .putParam("email", email)
                .putParam("password", password)
                .isCached(false);

        return mRequestQueue.add(builder.build());
    }
}
