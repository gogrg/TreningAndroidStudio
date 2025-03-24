package com.example.trening_tz.Requests.requestsSettings;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trening_tz.dto.DataForRequestEntry;
import com.example.trening_tz.dto.User;

public class RequestEntryOptions extends RequestOptions<User>{

    public RequestEntryOptions(DataForRequestEntry dataForRequestEntry, AppCompatActivity activity) {
        super(dataForRequestEntry, activity);
        url += "auth/login";
        this.tClass = User.class;
    }
}
