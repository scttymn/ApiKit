package com.ovenbits.request;


import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class GsonJsonRequest<T> extends JsonRequest<T> {
    private final Gson mGson = new Gson();
    private final Class<T> mClazz;
    private final Listener<T> mListener;
    private final Map<String,String> mHeaders;
    private Map<String,String> mResponseHeaders;
    private final int mCachedTime;

    public GsonJsonRequest(RequestBuilder builder) {
        super(builder.getMethod(), builder.getUrl(), builder.getBody(), builder.getListener(), builder.getErrorListener());

        mClazz      = builder.getClazz();
        mListener   = builder.getListener();
        mHeaders    = builder.getHeaders();
        mCachedTime = builder.getCachedTime();

        setShouldCache(mCachedTime > 0);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders.isEmpty() ? super.getHeaders() : mHeaders;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            mResponseHeaders = response.headers;
            String json = new String(
                    response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(
                    mGson.fromJson(json, mClazz), parseIgnoreCacheHeaders(response, mCachedTime));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    /**
     * Extracts a {@link Cache.Entry} from a {@link NetworkResponse}.
     * Cache-control headers are ignored. SoftTtl == 3 mins, ttl == 24 hours.
     * @param response The network response to parse headers from
     * @return a cache entry for the given response, or null if the response is not cacheable.
     */
    public static Cache.Entry parseIgnoreCacheHeaders(NetworkResponse response, int cacheTime) {
        long now = System.currentTimeMillis();
        final int ONE_MINUTE = 60 * 1000;
        final int ONE_HOUR = 60 * ONE_MINUTE;

        Map<String, String> headers = response.headers;
        long serverDate = 0;
        String serverEtag = null;
        String headerValue;

        headerValue = headers.get("Date");
        if (headerValue != null) {
            serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
        }

        serverEtag = headers.get("ETag");

        final long cacheHitButRefreshed = cacheTime * ONE_MINUTE; // in cacheTime minutes cache will be hit, but also refreshed on background
        final long cacheExpired = 24 * ONE_HOUR; // in 24 hours this cache entry expires completely
        final long softExpire = now + cacheHitButRefreshed;
        final long ttl = now + cacheExpired;

        Cache.Entry entry = new Cache.Entry();
        entry.data = response.data;
        entry.etag = serverEtag;
        entry.softTtl = softExpire;
        entry.ttl = ttl;
        entry.serverDate = serverDate;
        entry.responseHeaders = headers;

        return entry;
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
        mListener.onResponse(response, mResponseHeaders);
    }
}
