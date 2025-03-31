package com.example.trening_tz.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.trening_tz.R;
import com.example.trening_tz.Requests.ResponseCallback;
import com.example.trening_tz.Requests.UniversalRequest;
import com.example.trening_tz.Requests.requestsSettings.RequestCurrentWeekOptions;
import com.example.trening_tz.Requests.requestsSettings.RequestScheduleOptions;
import com.example.trening_tz.ScheduleActivity;
import com.example.trening_tz.buildUI.BuildSchedule;
import com.example.trening_tz.dto.DataForRequestSchedule;
import com.example.trening_tz.dto.User;
import com.example.trening_tz.servise.KeysFileEntry;
import com.example.trening_tz.servise.NamesFilesSetting;
import com.example.trening_tz.servise.StaticSharedPreferences;

import java.time.LocalDate;

public class FragmentSchedule extends Fragment {

    public FragmentSchedule() {
        super(R.layout.fragment_schedule);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppCompatActivity activity = (AppCompatActivity) requireActivity();

        ConstraintLayout blockSchedule = view.findViewById(R.id.blockSchedule);

        LocalDate currentDate = LocalDate.now();

        DataForRequestSchedule dataForRequestSchedule = new DataForRequestSchedule(currentDate.getYear(), currentDate.getMonthValue(), currentDate.getDayOfMonth(),
                StaticSharedPreferences.<User>getObject(NamesFilesSetting.FILE_ENTRY.getValue(), KeysFileEntry.USER_JSON.getValue(), "", User.class, User::new, activity).getUserId(),
                StaticSharedPreferences.<User>getObject(NamesFilesSetting.FILE_ENTRY.getValue(), KeysFileEntry.USER_JSON.getValue(), "", User.class, User::new,activity).getIdGroup(), "");

        UniversalRequest.connect(new RequestScheduleOptions(dataForRequestSchedule, activity, StaticSharedPreferences.<User>getObject(NamesFilesSetting.FILE_ENTRY.getValue(), KeysFileEntry.USER_JSON.getValue(), "", User.class, User::new, activity)), new ResponseCallback() {
            @Override
            public <Schedule> void onResponse(int code, Schedule gettingSchedule) {
                if (code == 200) {
                    com.example.trening_tz.dto.schedule.Schedule schedule = (com.example.trening_tz.dto.schedule.Schedule) gettingSchedule;
//TODO Подумать, откуда вызывать создание расписание. Возможно подумать над переименованием класса BuildSchedule в просто Build и создание методов для разных случаев


                    //получение номера текущей недели, её типа и дня
                    UniversalRequest.connect(new RequestCurrentWeekOptions(currentDate, activity, StaticSharedPreferences.<User>getObject(NamesFilesSetting.FILE_ENTRY.getValue(), KeysFileEntry.USER_JSON.getValue(), "", User.class, User::new, activity)), new ResponseCallback() {
                        @Override
                        public <CurrentWeek> void onResponse(int code, CurrentWeek gettingCurrentWeek) {
                            if (code == 200) {
                                com.example.trening_tz.dto.CurrentWeek currentWeek = (com.example.trening_tz.dto.CurrentWeek) gettingCurrentWeek;
                                BuildSchedule.build(schedule, currentWeek.getWeekNum()-1, blockSchedule, activity);
                            }
                        }
                    });

                }
            }
        });
    }
}
