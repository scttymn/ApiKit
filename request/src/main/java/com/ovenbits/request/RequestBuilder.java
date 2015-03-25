package com.ovenbits.request;


import com.android.volley.Request;
import com.android.volley.Response;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class RequestBuilder<T> {
    private final static int CACHE = 3;
    private final static int NO_CACHE = 0;

    private final int mMethod;
    private final String mUrl;
    private final Class<T> mClazz;
    private final Listener<T> mListener;
    private final Response.ErrorListener mErrorListener;
    private Gson mGson = new Gson();

    // Optional parameters
    private String mBody;
    private Map<String,Object> mParams = new HashMap<String, Object>();
    private Map<String,String> mHeaders = new HashMap<String, String>();
    private int mCachedTime = NO_CACHE;
    private ContentType mContentType = ContentType.FORM_URL_ENCODED;

    public enum ContentType {
        JSON,
        FORM_URL_ENCODED
    }

    /**
     * RequestBuilder constructor
     * @param method the Request.Method (POST, GET, DELETE, PATCH, etc...)
     * @param url the com.ovenbits.apikit.example.api endpoint of the request
     * @param clazz the response object class
     * @param listener the volley Response.Listener class
     * @param errorListener the volley Response.ErrorListener class
     */
    public RequestBuilder(int method, String url, Class<T> clazz,
                          Listener<T> listener,
                          Response.ErrorListener errorListener) {

        mMethod        = method;
        mUrl           = url;
        mClazz         = clazz;
        mListener      = listener;
        mErrorListener = errorListener;
    }

    /**
     * Add a single parameter to the request
     * @param key String parameter key
     * @param value Object parameter value
     * @return RequestBuilder object with the specified parameter.
     */
    public RequestBuilder putParam(String key, Object value) {
        mParams.put(key, value);
        return this;
    }

    /**
     * @param params a <String, Object> map of parameters of the request
     * @return RequestBuilder object with the specified parameters.
     */
    public RequestBuilder putParams(Map<String, Object> params) {
        mParams.putAll(params);
        return this;
    }

    /**
     * Add a single header to the request
     * @param key String header key
     * @param value String header value
     * @return RequestBuilder object with the specified header.
     */
    public RequestBuilder putHeader(String key, String value) {
        mHeaders.put(key, value);
        return this;
    }

    /**
     * @param headers a <String,String> Map of request headers
     * @return RequestBuilder object with the specified headers.
     */
    public RequestBuilder putHeaders(Map<String,String> headers) {
        mHeaders = headers;
        return this;
    }

    public RequestBuilder setBody(String body) {
        mBody = body;
        return this;
    }

    /**
     * @param hasCache forces caching of the request if cache headers are not present
     * @return RequestBuilder object with the specified cache time.
     */
    public RequestBuilder isCached(boolean hasCache) {
        if (hasCache) {
            mCachedTime = CACHE;
        } else {
            mCachedTime = NO_CACHE;
        }
        return this;
    }

    /**
     * @param contentType encoding ContentType of the request.
     * @return RequestBuilder object with the specified ContentType.
     */
    public RequestBuilder encoding(ContentType contentType) {
        mContentType = contentType;
        return this;
    }

    public Request<T> build() {
        if (mContentType == ContentType.JSON) {
            return new GsonJsonRequest<T>(this);
        } else {
            return new GsonRequest<T>(this);
        }
    }

    public int getMethod() {
        return mMethod;
    }

    public String getUrl() {
        return mUrl;
    }

    public Class<T> getClazz() {
        return mClazz;
    }

    public Listener<T> getListener() {
        return mListener;
    }

    public Response.ErrorListener getErrorListener() {
        return mErrorListener;
    }

    public Map<String,Object> getParams() {
        return mParams;
    }

    public String getBody() {
        return (mBody != null) ? mBody : mGson.toJson(mParams);
    }

    public Map<String,String> getHeaders() {
        return mHeaders;
    }

    public int getCachedTime() {
        return mCachedTime;
    }
}
