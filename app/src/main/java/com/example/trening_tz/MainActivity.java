package com.example.trening_tz;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.trening_tz.Requests.Connecting;
import com.example.trening_tz.service.MessageBox;

import java.io.IOException;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import com.example.trening_tz.GsonClass.User;
import com.google.gson.Gson;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

public class MainActivity extends AppCompatActivity {
    private static SharedPreferences settingEntry;
    private static final String PREF_FILE_SETTING = "FILE_SETTING_ENTRY";
    private static final String PREF_LOGIN = "login";
    private static final String PREF_PASSWORD = "password";
    private static final String PREF_USER_JSON = "user_json";

    private User user = new User();
    private String jsonUser;
    private Gson gson = new Gson();

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
        settingEntry = getSharedPreferences(PREF_FILE_SETTING, MODE_PRIVATE);

        //объявление и присваивание виджетов
        EditText editLogin = findViewById(R.id.editTextLogin);
        EditText editPassword = findViewById(R.id.editTextPassword);
        ImageButton buttonClearText = findViewById(R.id.buttonClearTextLogin);
        ImageButton buttonVisiblePassword = findViewById(R.id.buttonVisiblePassword);
        Button buttonEntry = findViewById(R.id.buttonEntry);

        //присвоение значений логина с паролем из памяти
        editLogin.setText(settingEntry.getString(PREF_LOGIN, ""));
        editPassword.setText(settingEntry.getString(PREF_PASSWORD, ""));
        //если есть - достаём данные о токене, айдишники и прочее
        jsonUser = settingEntry.getString(PREF_USER_JSON, "");
        if (!jsonUser.isEmpty()) {
            user = gson.fromJson(jsonUser, User.class);
        }

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
                SharedPreferences.Editor sharedSetting = settingEntry.edit();
                int codeResponse[] = new int[1];
                if (!user.getToken().isEmpty()) {
                    DecodedJWT decodedJWT = JWT.decode(user.getToken());

                    if (decodedJWT.getExpiresAt().before(new Date())) {
                        Connecting.connect(editLogin.getText().toString(), editPassword.getText().toString(), MainActivity.this, new Connecting.ResponseCallback() {
                            @Override
                            public void onResponse(int code, User gettingUser) {
                                codeResponse[0] = code;
                                if (code == 200) {
                                    user = gettingUser;
                                    jsonUser = gson.toJson(user);
                                    sharedSetting.putString(PREF_USER_JSON, jsonUser);
                                    sharedSetting.apply();

                                    goToSchedule(user);
                                }
                            }
                        });
                    } else {
                        goToSchedule(user);
                    }
                } else {
                    Connecting.connect(editLogin.getText().toString(), editPassword.getText().toString(), MainActivity.this, new Connecting.ResponseCallback() {
                        @Override
                        public void onResponse(int code, User gettingUser) {
                            codeResponse[0] = code;
                            if (code == 200) {
                                user = gettingUser;
                                jsonUser = gson.toJson(user);
                                sharedSetting.putString(PREF_USER_JSON, jsonUser);
                                sharedSetting.putString(PREF_LOGIN, editLogin.getText().toString());
                                sharedSetting.putString(PREF_PASSWORD, editPassword.getText().toString());
                                sharedSetting.apply();

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
        Intent intent = new Intent(MainActivity.this, Schedule.class);
        intent.putExtra("userData", user);
        startActivity(intent);
        finish();
    }
}