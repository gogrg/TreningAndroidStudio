package com.example.trening_tz.buildUI;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

    public static void build(Schedule schedule, int numberCurrentWeek, LinearLayout linearWeek, AppCompatActivity activity) {
        linearWeek.removeAllViews();


        RelativeLayout daysInWeekLayout[] = new RelativeLayout[numberDaysInWeek];

        for (int i = 0; i < numberDaysInWeek; i++) {
            try {
                daysInWeekLayout[i] = createDay(getNameDay(i),schedule.getDay(i).getSomeVariantDay(numberCurrentWeek), activity);
            } catch (Exception e) {
                Log.d("TAG", e.toString());
            }

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams
                    (RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

            layoutParams.addRule(RelativeLayout.ALIGN_LEFT, linearWeek.getId());
            layoutParams.addRule(RelativeLayout.ALIGN_RIGHT, linearWeek.getId());
            if (i == 0) {
                layoutParams.addRule(RelativeLayout.BELOW, linearWeek.getId());
            } else {
                layoutParams.addRule(RelativeLayout.BELOW, daysInWeekLayout[i - 1].getId());
            }
            layoutParams.setMargins(20, 20, 20, 20);


            daysInWeekLayout[i].setLayoutParams(layoutParams);

            linearWeek.addView(daysInWeekLayout[i]);
        }
    }

    private static RelativeLayout createDay(String nameDay, List<Lesson> listLessons, AppCompatActivity activity) {
        RelativeLayout layoutDay = new RelativeLayout(activity);

        layoutDay.setClipToOutline(true);
        //создание и применение шаблона по которому строится фон
        GradientDrawable background = new GradientDrawable();
        background.setShape(GradientDrawable.RECTANGLE);
        background.setCornerRadius(60);
        background.setColor(0xFFFFFFFF);
        layoutDay.setBackground(background);

        //создание текстового поля с названием дня
        TextView textViewNameDay = createTextView(nameDay,
                15,0xFF828180, activity,
                0, true,
                layoutDay.getId(), layoutDay.getId(), new int[] {40, 30, 0, 0},
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textViewNameDay.setId(View.generateViewId());
        layoutDay.addView(textViewNameDay);



        if (listLessons != null) {
            LinearLayout linearLessons = new LinearLayout(new ContextThemeWrapper(activity, R.style.SeparatorLessonsInDay),
                    null, 0);

            linearLessons.setId(View.generateViewId());
            linearLessons.setOrientation(LinearLayout.VERTICAL);

            RelativeLayout.LayoutParams linearParamsLessons = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearParamsLessons.addRule(RelativeLayout.BELOW, textViewNameDay.getId());
            linearParamsLessons.setMargins(20, 10, 20, 0);
            linearLessons.setLayoutParams(linearParamsLessons);

            // Добавляем все пары
            for (Lesson lesson : listLessons) {
                RelativeLayout inputLayout = createLesson(lesson, activity);

                // Используем LinearLayout.LayoutParams для LinearLayout
                LinearLayout.LayoutParams paramsInputLayout = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                paramsInputLayout.setMargins(10, 20, 10, 0);

                inputLayout.setLayoutParams(paramsInputLayout);
                linearLessons.addView(inputLayout);
            }

            layoutDay.addView(linearLessons);

        } else {
            TextView textViewInfoNull = createTextView("нет пар",
                    12, 0xFFC7C5C4, activity,
                    0, false,
                    layoutDay.getId(),
                    textViewNameDay.getId(),
                    new int[]{30, 10, 0, 30},
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutDay.addView(textViewInfoNull);
        }


        return layoutDay;
    }

    private static RelativeLayout createLesson(Lesson lesson, AppCompatActivity activity) {
        RelativeLayout layoutLesson = new RelativeLayout(activity);
        layoutLesson.setBackgroundColor(0xFFFFFFFF);
        layoutLesson.setId(View.generateViewId());

        LayoutInflater inflater = LayoutInflater.from(activity);

        inflater.inflate(R.layout.lesson, layoutLesson, true);

        layoutLesson.post(() -> {
            try {
                //номер пары
                TextView universalView = layoutLesson.findViewById(R.id.textViewNumberLesson);
                universalView.setText(String.valueOf(lesson.getLessonInfo().getNumber()));

                //время пары
                universalView = layoutLesson.findViewById(R.id.textViewTimeLesson);
                universalView.setText(lesson.getLessonInfo().getTime());

                //тип пары
                universalView = layoutLesson.findViewById(R.id.textViewTypeLesson);
                universalView.setText(lesson.getLessonInfo().getType());

                //кабинет
                universalView = layoutLesson.findViewById(R.id.textViewRoom);
                universalView.setText(lesson.getRoom().getName());

                //название пары
                universalView = layoutLesson.findViewById(R.id.textViewNameLesson);
                universalView.setText(lesson.getSubject());

                //фио препода
                universalView = layoutLesson.findViewById(R.id.textViewFullNameTeacher);
                universalView.setText(lesson.getFirstTeacher().getFullname());

                //фотка препода
                ImageView imageViewTeacher = layoutLesson.findViewById(R.id.imageTeacher);
                if (lesson.getFirstTeacher().getPhoto().getSmall() == null){
                    imageViewTeacher.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    imageViewTeacher.setPadding(7, 7, 7, 15);
                } else {
                    lesson.getFirstTeacher().getPhoto().setSmallImage(imageViewTeacher, R.drawable.icon_person, activity);
                }

            } catch (Exception e) {
                Log.e("Build Lesson", e.toString());
            }
        });

        return layoutLesson;
    }

    //СОЗДАНИЕ ТЕКСТОВОГО ПОЛЯ ПО ТЕКСТУ, РАЗМЕРУ ТЕКСТА, ЦВЕТУ ТЕКСТА, ПЕРЕДАВАЕМОЙ АКТИВНОСТИ, ID к чему привязывться сбоку и сверху, типу привязки и отступам
    //leftToLeft = 0 - привязка leftToLeft; 1 - leftToRight; 2 - rightToRight
    //TODO пересмотреть нахер работу метода с учётом RelativeLayout
    private static TextView createTextView(String text, int textSize, int textColor, AppCompatActivity activity, int leftToLeft, boolean topToTop,
                                           int leftId, int topId, int[] margins, int width, int height) {
        TextView textView = new TextView(activity);
        textView.setId(View.generateViewId());
        textView.setText(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
        textView.setTextColor(textColor);
        textView.setTypeface(null, Typeface.BOLD);

        RelativeLayout.LayoutParams paramsTextView = new RelativeLayout.LayoutParams(width, height);

        switch (leftToLeft) {
            case 0:
                paramsTextView.addRule(RelativeLayout.ALIGN_LEFT, leftId);
                break;
            case 1:
                paramsTextView.addRule(RelativeLayout.ALIGN_RIGHT, leftId);
                break;
            case 2:
                paramsTextView.addRule(RelativeLayout.ALIGN_RIGHT, leftId);
                break;
        }

        if (topToTop) {
            paramsTextView.addRule(RelativeLayout.BELOW, topId);
        } else {
            paramsTextView.addRule(RelativeLayout.BELOW, topId);
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
