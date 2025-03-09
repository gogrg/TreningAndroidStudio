package com.example.trening_tz.Requests;

public interface ResponseCallback {
    public <T> void onResponse(int codeResponce, T getData);
}
