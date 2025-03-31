package com.example.trening_tz.buildUI;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.trening_tz.R;
import com.example.trening_tz.dto.schedule.Schedule;
import com.example.trening_tz.dto.schedule.structureLesson.Lesson;

import java.util.List;

public class BuildSchedule {
        private final static int numberDaysInWeek = 6;

    public static void build(Schedule schedule, int numberCurrentWeek, ConstraintLayout constraintLayout, AppCompatActivity activity) {
        constraintLayout.removeAllViews();


        ConstraintLayout weekConstraintLayouy[] = new ConstraintLayout[6];

        for (int i = 0; i < numberDaysInWeek; i++) {
            try {
                weekConstraintLayouy[i] = createDay(getNameDay(i),schedule.getDay(i).getSomeVariantDay(numberCurrentWeek), activity);
            } catch (Exception e) {
                Log.d("TAG", e.toString());
            }

            ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams
                    (ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

            layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;
            if (i == 0) {
                layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
            } else {
                layoutParams.topToBottom = weekConstraintLayouy[i - 1].getId();
            }
            layoutParams.setMargins(20, 20, 20, 20);


            weekConstraintLayouy[i].setLayoutParams(layoutParams);

            constraintLayout.addView(weekConstraintLayouy[i]);
        }
    }

    private static ConstraintLayout createDay(String nameDay, List<Lesson> listLessons, AppCompatActivity activity) {
        ConstraintLayout layout = new ConstraintLayout(activity);
        layout.setId(View.generateViewId());
        layout.setClipToOutline(true);
        //создание и применение шаблона по которому строится фон
        GradientDrawable background = new GradientDrawable();
        background.setShape(GradientDrawable.RECTANGLE);
        background.setCornerRadius(60);
        background.setColor(0xFFFFFFFF);
        layout.setBackground(background);

        TextView textViewNameDay = createTextView(nameDay,
                15,0xFF828180, activity,
                0, true,
                layout.getId(), layout.getId(), new int[] {20, 10, 0, 0},
                ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
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
                        (ConstraintLayout.LayoutParams.MATCH_PARENT, 400);

                paramsInputLayout.leftToLeft = layout.getId();
                paramsInputLayout.topToBottom = currentPreviousId;
                paramsInputLayout.setMargins(10, 20, 0, 30);

                inputLayout.setLayoutParams(paramsInputLayout);
                layout.addView(inputLayout);
                currentPreviousId = inputLayout.getId();
            }
        } else {
            TextView textViewInfoNull = createTextView("нет пар",
                    12, 0xFFC7C5C4, activity,
                    0, false,
                    layout.getId(),
                    textViewNameDay.getId(),
                    new int[]{30, 10, 0, 30},
                    ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
            layout.addView(textViewInfoNull);
        }


        return layout;
    }

    private static ConstraintLayout createLesson(Lesson lesson, AppCompatActivity activity) {
        ConstraintLayout layout = new ConstraintLayout(activity);
        layout.setBackgroundColor(0xFFFFFFFF);
        layout.setId(View.generateViewId());

        LayoutInflater inflater = LayoutInflater.from(activity);

        inflater.inflate(R.layout.lesson, layout, true);

        layout.post(() -> {
            try {
                //номер пары
                TextView universalView = layout.findViewById(R.id.textViewNumberLesson);
                universalView.setText(String.valueOf(lesson.getLessonInfo().getNumber()));

                //время пары
                universalView = layout.findViewById(R.id.textViewTimeLesson);
                universalView.setText(lesson.getLessonInfo().getTime());

                //тип пары
                universalView = layout.findViewById(R.id.textViewTypeLesson);
                universalView.setText(lesson.getLessonInfo().getType());

                //кабинет
                universalView = layout.findViewById(R.id.textViewRoom);
                universalView.setText(lesson.getRoom().getName());

                //название пары
                universalView = layout.findViewById(R.id.textViewNameLesson);
                universalView.setText(lesson.getSubject());

                //фио препода
                universalView = layout.findViewById(R.id.textViewFullNameTeacher);
                universalView.setText(lesson.getFirstTeacher().getFullname());

                //фотка препода
                ImageView imageViewTeacher = layout.findViewById(R.id.imageTeacher);
                lesson.getFirstTeacher().getPhoto().setSmallImage(imageViewTeacher, R.drawable.icon_person, activity);
            } catch (Exception e) {
                Log.e("Build Lesson", e.toString());
            }
        });

        return layout;
    }

    //СОЗДАНИЕ ТЕКСТОВОГО ПОЛЯ ПО ТЕКСТУ, РАЗМЕРУ ТЕКСТА, ЦВЕТУ ТЕКСТА, ПЕРЕДАВАЕМОЙ АКТИВНОСТИ, ID к чему привязывться сбоку и сверху, типу привязки и отступам
    //leftToLeft = 0 - привязка leftToLeft; 1 - leftToRight; 2 - rightToRight
    private static TextView createTextView(String text, int textSize, int textColor, AppCompatActivity activity, int leftToLeft, boolean topToTop,
                                           int leftId, int topId, int[] margins, int width, int height) {
        TextView textView = new TextView(activity);
        textView.setId(View.generateViewId());
        textView.setText(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
        textView.setTextColor(textColor);
        textView.setTypeface(null, Typeface.BOLD);

        ConstraintLayout.LayoutParams paramsTextView = new ConstraintLayout.LayoutParams
                (width, height);

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
