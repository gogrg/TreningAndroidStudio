package com.example.trening_tz.Requests.requestsSettings;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trening_tz.servise.GsonClass;

import java.util.HashMap;
import java.util.Map;

public abstract class RequestOptions<T> {
    protected String request;
    protected String url = "https://app2.spbgasu.ru/";
    protected Class<T> tClass;
    protected AppCompatActivity activity;
    protected Map<String, String> headers = new HashMap<String, String>();

    public <T> RequestOptions (T dataForRequest, AppCompatActivity activity) {
        request = GsonClass.toJson(dataForRequest);
        this.activity = activity;
        //this.headers.put("x-route-type", "mobile");
        this.headers.put("X-Poweres-By", "Express");
    }

    public Class<T> gettClass() {
        return tClass;
    }

    public void settClass(Class<T> tClass) {
        this.tClass = tClass;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public AppCompatActivity getActivity() {
        return activity;
    }

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}
