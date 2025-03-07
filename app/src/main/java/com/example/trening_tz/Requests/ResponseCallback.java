package com.example.trening_tz.Requests;

import com.example.trening_tz.dto.User;

public interface ResponseCallback {
    public <T> void onResponse(int codeResponce, T getData);
}
