package com.example.trening_tz.buildUI;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.trening_tz.dto.schedule.Schedule;
import com.example.trening_tz.dto.schedule.structureLesson.Lesson;

import java.util.List;

public class BuildSchedule {

    public static void build(Schedule schedule, int numberCurrentWeek, ConstraintLayout constraintLayout, AppCompatActivity activity) {
        constraintLayout.removeAllViews();

        ConstraintLayout weekConstraintLayouy[] = new ConstraintLayout[6];

        for (int i = 0; i < 6; i++) {
            try {
                weekConstraintLayouy[i] = createDay(getNameDay(i),schedule.getDay(i).getSomeVariantDay(numberCurrentWeek), activity);
            } catch (Exception e) {
                Log.d("TAG", e.toString());
            }
            weekConstraintLayouy[i].setId(View.generateViewId());

            ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams
                    (ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

            layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
            if (i == 0) {
                layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
            } else {
                layoutParams.topToBottom = weekConstraintLayouy[i - 1].getId();
            }
            layoutParams.setMargins(10, 20, 0, 0);


            weekConstraintLayouy[i].setLayoutParams(layoutParams);
            weekConstraintLayouy[i].setBackgroundColor(0xFFF0F0F0);

            constraintLayout.addView(weekConstraintLayouy[i]);
        }
    }

    private static ConstraintLayout createDay(String nameDay, List<Lesson> listLessons, AppCompatActivity activity) {
        ConstraintLayout layout = new ConstraintLayout(activity);

        TextView textViewNameDay = createTextView(nameDay, 15,0xFF828180, activity, 0, true, layout.getId(), layout.getId(), new int[] {20, 10, 0, 0});
        layout.addView(textViewNameDay);

        if (listLessons != null) {
            //первая пара
            ConstraintLayout inputLayout = createLesson(listLessons.get(0), activity);
            ConstraintLayout.LayoutParams paramsInputLayout = new ConstraintLayout.LayoutParams
                    (ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

            paramsInputLayout.leftToLeft = layout.getId();
            paramsInputLayout.topToBottom = textViewNameDay.getId();
            paramsInputLayout.setMargins(10, 20, 0, 0);

            inputLayout.setLayoutParams(paramsInputLayout);
            layout.addView(inputLayout);

            //остальные пары
            int currentPreviousId = inputLayout.getId();

            for (int i = 1; i < listLessons.size(); i++) {
                inputLayout = createLesson(listLessons.get(i), activity);

                paramsInputLayout = new ConstraintLayout.LayoutParams
                        (ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

                paramsInputLayout.leftToLeft = layout.getId();
                paramsInputLayout.topToBottom = currentPreviousId;
                paramsInputLayout.setMargins(10, 20, 0, 0);

                inputLayout.setLayoutParams(paramsInputLayout);
                layout.addView(inputLayout);
                currentPreviousId = inputLayout.getId();
            }
        } else {
            TextView textViewInfoNull = createTextView("нет пар",
                    12, 0xFFC7C5C4, activity,
                    0, true,
                    layout.getId(),
                    layout.getId(),
                    new int[]{10, 10, 0, 0});
            layout.addView(textViewInfoNull);
        }


        return layout;
    }

    private static ConstraintLayout createLesson(Lesson lesson, AppCompatActivity activity) {
        ConstraintLayout layout = new ConstraintLayout(activity);
        layout.setId(View.generateViewId());

        //номер пары
        TextView textViewNumberLesson = createTextView(String.valueOf(lesson.getLessonInfo().getNumber()),
                12, 0xFFC14424, activity,
                0, true,
                layout.getId(), layout.getId(),
                new int[]{10, 10, 0, 0});
        layout.addView(textViewNumberLesson);

        //время пары
        TextView textViewLessonTime = createTextView(lesson.getLessonInfo().getTime(),
                12, 0xFF828180, activity,
                1, true,
                textViewNumberLesson.getId(), layout.getId(),
                new int[]{10, 10, 0, 0});
        layout.addView(textViewLessonTime);

        //тип пары
        TextView textViewTypeLesson = createTextView(lesson.getLessonInfo().getType(),
                12, 0xFF828180, activity,
                1, true,
                textViewLessonTime.getId(), layout.getId(),
                new int[]{10, 10, 0, 0});
        layout.addView(textViewTypeLesson);

        //кабинет
        TextView textViewRoom = createTextView(lesson.getRoom().getName(),
                14, 0xFF292827, activity,
                2, true,
                layout.getId(), layout.getId(),
                new int[] {0, 10, 40, 0});
        layout.addView(textViewRoom);

        //название пары
        TextView textViewNameLesson = createTextView(lesson.getSubject(),
                14, 0xFF292827, activity,
                0, false,
                layout.getId(), textViewNumberLesson.getId(),
                new int[] {10, 20, 0, 0});
        layout.addView(textViewNameLesson);

        return layout;
    }

    private static TextView createTextView(String text, int textSize, int textColor, AppCompatActivity activity, int leftToLeft, boolean topToTop,
                                           int leftId, int topId, int[] margins) {
        TextView textView = new TextView(activity);
        textView.setId(View.generateViewId());
        textView.setText(text);
        textView.setTextSize(textSize);
        textView.setTextColor(textColor);

        ConstraintLayout.LayoutParams paramsTextView = new ConstraintLayout.LayoutParams
                (ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

        switch (leftToLeft) {
            case 0:
                paramsTextView.leftToLeft = leftId;
                break;
            case 1:
                paramsTextView.leftToRight = leftId;
                break;
            case 2:
                paramsTextView.rightToRight = leftId;
                break;
        }

        if (topToTop) {
            paramsTextView.topToTop = topId;
        } else {
            paramsTextView.topToBottom = topId;
        }

        paramsTextView.setMargins(margins[0], margins[1], margins[2], margins[3]);
        textView.setPadding(5, 5, 5, 10);
        //добавление в контейнер
        textView.setLayoutParams(paramsTextView);

        return textView;
    }

    private static String getNameDay(int num) throws Exception {
        switch (num) {
            case 0:
                return "Понедельник";
            case 1:
                return "Вторник";
            case 2:
                return "Среда";
            case 3:
                return "Четверг";
            case 4:
                return "Пятница";
            case 5:
                return "Суббота";
            default:
                throw new Exception("Неверный номер дня недели");
        }
    }
}
