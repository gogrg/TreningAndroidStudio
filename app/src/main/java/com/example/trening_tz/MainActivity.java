package com.example.trening_tz;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Date;

import com.example.trening_tz.Requests.ResponseCallback;
import com.example.trening_tz.Requests.UniversalRequest;
import com.example.trening_tz.Requests.requestsSettings.RequestEntryOptions;
import com.example.trening_tz.dto.DataForRequestEntry;
import com.example.trening_tz.dto.User;
import com.example.trening_tz.servise.GsonClass;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.trening_tz.servise.KeysFileEntry;
import com.example.trening_tz.servise.NamesFilesSetting;
import com.example.trening_tz.servise.StaticSharedPreferences;

public class MainActivity extends AppCompatActivity {
    private User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //получение значений для всякого разного

        //объявление и присваивание виджетов
        EditText editLogin = findViewById(R.id.editTextLogin);
        EditText editPassword = findViewById(R.id.editTextPassword);
        ImageButton buttonClearText = findViewById(R.id.buttonClearTextLogin);
        ImageButton buttonVisiblePassword = findViewById(R.id.buttonVisiblePassword);
        Button buttonEntry = findViewById(R.id.buttonEntry);

        //присвоение значений логина с паролем из памяти
        editLogin.setText(StaticSharedPreferences.getString(NamesFilesSetting.FILE_ENTRY.getValue(),
                KeysFileEntry.LOGIN.getValue(),
                "",
                MainActivity.this));

        editPassword.setText(StaticSharedPreferences.getString(NamesFilesSetting.FILE_ENTRY.getValue(),
                KeysFileEntry.PASSWORD.getValue(),
                "",
                MainActivity.this));


        //если есть - достаём данные о токене, айдишники и прочее
            user = StaticSharedPreferences.<User>getObject(NamesFilesSetting.FILE_ENTRY.getValue(),
                            KeysFileEntry.USER_JSON.getValue(),
                            "",
                            User.class,
                            User::new,
                            MainActivity.this);


        //для пароля отображалка кнопки видимости пароля
        if (editPassword.getText().toString().isEmpty()) {
            buttonVisiblePassword.setVisibility(View.INVISIBLE);
        } else {
            buttonVisiblePassword.setVisibility(View.VISIBLE);
        }
        //для логина кнопка стирания
        if (editLogin.getText().toString().isEmpty()) {
            buttonClearText.setVisibility(View.INVISIBLE);
        } else {
            buttonClearText.setVisibility(View.VISIBLE);
        }

        editLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    buttonClearText.setVisibility(View.INVISIBLE);
                } else {
                    buttonClearText.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    buttonVisiblePassword.setVisibility(View.INVISIBLE);
                } else {
                    buttonVisiblePassword.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        buttonClearText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editLogin.setText("");
            }
        });

        buttonVisiblePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editPassword.getInputType() == InputType.TYPE_CLASS_TEXT) {
                    editPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    buttonVisiblePassword.setImageResource(R.drawable.eye_furned_out_icon);
                } else {
                    editPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    buttonVisiblePassword.setImageResource(R.drawable.eye_icon);
                }
            }
        });

        buttonEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!user.getToken().isEmpty()) {
                    DecodedJWT decodedJWT = JWT.decode(user.getToken());

                    if (decodedJWT.getExpiresAt().before(new Date())) {
                        DataForRequestEntry dataForRequestEntry = new DataForRequestEntry(editLogin.getText().toString(), editPassword.getText().toString());

                        UniversalRequest.connect(new RequestEntryOptions(dataForRequestEntry, MainActivity.this), new ResponseCallback() {
                            @Override
                            public <User> void onResponse(int code, User gettingUser) {
                                if (code == 200) {
                                    user = (com.example.trening_tz.dto.User) gettingUser;
                                    StaticSharedPreferences.putString(NamesFilesSetting.FILE_ENTRY.getValue(),
                                            KeysFileEntry.USER_JSON.getValue(),
                                            GsonClass.toJson(user),
                                            MainActivity.this);

                                    goToSchedule(user);
                                }
                            }
                        });
                    } else {
                        goToSchedule(user);
                    }
                } else {

                    DataForRequestEntry dataForRequestEntry = new DataForRequestEntry(editLogin.getText().toString(), editPassword.getText().toString());


                    UniversalRequest.connect(new RequestEntryOptions(dataForRequestEntry, MainActivity.this), new ResponseCallback() {
                        @Override
                        public <User> void onResponse(int code, User gettingUser) {
                            if (code == 200) {
                                user = (com.example.trening_tz.dto.User) gettingUser;
                                StaticSharedPreferences.putString(NamesFilesSetting.FILE_ENTRY.getValue(),
                                        KeysFileEntry.USER_JSON.getValue(),
                                        GsonClass.toJson(user),
                                        MainActivity.this);

                                StaticSharedPreferences.putString(NamesFilesSetting.FILE_ENTRY.getValue(),
                                        KeysFileEntry.LOGIN.getValue(),
                                        editLogin.getText().toString(),
                                        MainActivity.this);

                                StaticSharedPreferences.putString(NamesFilesSetting.FILE_ENTRY.getValue(),
                                        KeysFileEntry.PASSWORD.getValue(),
                                        editLogin.getText().toString(),
                                        MainActivity.this);

                                goToSchedule(user);
                            }
                        }
                    });
                }

            }
        });

        if (!editLogin.getText().toString().isEmpty() && !editPassword.getText().toString().isEmpty()) {
            Thread threadEntry = new Thread(() -> {
                try {
                    Thread.sleep(100);
                    if (!editLogin.getText().toString().isEmpty() && !editPassword.getText().toString().isEmpty()) {
                        //buttonEntry.setText("AAAAA");
                        buttonEntry.performClick();
                    }
                } catch (InterruptedException e) {
                    Log.d("TAG", "Thread for check data for entry is interrupted. " + e);
                }
            });
            threadEntry.run();
        }
    }

    public void goToSchedule(User user) {
        Intent intent = new Intent(MainActivity.this, ScheduleActivity.class);
        intent.putExtra("userData", user);
        startActivity(intent);
        finish();
    }
}