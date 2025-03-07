package com.example.trening_tz.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.annotation.NonNull;


public class MessageBox extends DialogFragment {
    private String title;
    private String message;

    public MessageBox(String title, String message) {
        this.title = title;
        this.message = message;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder.setTitle(title).setMessage(message).create();
    }
}
