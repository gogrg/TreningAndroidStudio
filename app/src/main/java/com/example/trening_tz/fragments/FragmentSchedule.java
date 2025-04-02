package com.example.trening_tz.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.trening_tz.R;
import com.example.trening_tz.Requests.RequestSchedule;
import com.example.trening_tz.Requests.ResponseCallback;
import com.example.trening_tz.buildUI.BuildSchedule;
import com.example.trening_tz.dto.User;


import java.time.LocalDate;

public class FragmentSchedule extends Fragment {

    User user;
    public FragmentSchedule(User user) {
        super(R.layout.fragment_schedule);
        this.user = user;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppCompatActivity activity = (AppCompatActivity) requireActivity();

        ConstraintLayout blockSchedule = view.findViewById(R.id.containerSchedule);

        RequestSchedule.getSchedule(activity, user, new ResponseCallback() {
            @Override
            public <T> void onResponse(int codeResponse, T gettingData) {
                com.example.trening_tz.dto.schedule.Schedule schedule = (com.example.trening_tz.dto.schedule.Schedule) gettingData;

                BuildSchedule.build(schedule, schedule.getCurrentWeekDay()-1, blockSchedule, activity);
            }
        });
    }
}
