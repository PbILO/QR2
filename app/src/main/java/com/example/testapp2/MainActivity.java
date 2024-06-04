package com.example.testapp2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.FormBody;
import okhttp3.RequestBody;


public class MainActivity extends AppCompatActivity {
    //public static String session_key;
    //OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //client = new OkHttpClient();
    }

    public void onClickStart(View view) {
        EditText login = (EditText) findViewById(R.id.login);
        EditText password = (EditText) findViewById(R.id.passwd);
        /*RequestBody formBody = new FormBody.Builder()
                .add("login", login.getText().toString())
                .add("password", password.getText().toString())
                .build();
        Request request = new Request.Builder()
                .method("POST", formBody)
                .url("http://45.143.92.223:8080/login")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    //response.code(); Непр пароль - 401
                    throw new IOException("Unexpected code " + response); //вместо исключения, пишем что неверный пароль
                } else {
                    // читаем данные в отдельном потоке
                    final String responseData = response.body().string();
                    // выполняем операции по обновлению UI
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            session_key = responseData;
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });*/


        Intent intent = new Intent(MainActivity.this, StartActivity.class);
        startActivity(intent);
    }
}